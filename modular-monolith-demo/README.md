# modular-monolith-demo

Maven 多模块单体：**三层边界守卫 + 可裁剪 + 可拆微服务** 的配套可运行示例。

配套笔记：`~/workspace/notes/编程/架构设计/单体架构/模块化单体最佳实践.org`
包名 `com.acme.shop` 只是示例，实际项目可整体替换（如 money-making-machine-plus 的 `com.z` / `z-module-*`）。

## 结构

```
modular-monolith-demo/
├── pom.xml                    # 聚合 + ${revision} 版本管理
├── shop-dependencies/         # BOM：三方依赖版本（无代码）
├── shop-framework/
│   └── shop-common/           # 无业务语义的共享层（Result 等）
├── shop-module-order/         # 核心模块（DDD 五层）：api / application / domain / infrastructure / web
├── shop-module-payment/       # 核心模块（DDD 五层，与 order 同构）
├── shop-module-user/          # 非核心模块（三层）：api / service / web（可裁剪演示）
├── shop-server/               # 聚合入口：唯一有 main 的工程，组装各业务模块
└── examples/leaky-example/    # 违规样例（故意不放 src 内）
```

## 模块内部结构（两种模式，与笔记 2.5 对应）

核心模块（业务规则复杂，如 order / payment）：

```
shop-module-order/
├── api/            # 契约：接口 + DTO（COLA client 思想）
├── application/    # 用例、事务、编排（OrderApplicationService implements OrderApi）
├── domain/         # 聚合、领域服务、Repository 接口 —— 纯 POJO，无 Spring
├── infrastructure/ # Repository 实现（依赖倒置落地；真实项目 = JPA Entity + 映射）
└── web/            # Controller，只依赖 api（注入接口，实现是 application 层）
```

非核心模块（CRUD 为主，如 user）：`api / service / web` 三层，不跑全套 DDD。

依赖方向：`web → application → domain ← infrastructure`，由 Modulith 环检测 + ArchUnit 9 条规则强制。

## 三层守卫（与笔记第三节对应）

| 层 | 工具 | 拦什么 | 在哪跑 |
|---|---|---|---|
| 1 | Maven 物理隔离 | biz 互引（artifact 级硬墙） | 编译期，全模块 |
| 2 | Spring Modulith per-biz | 本模块内部包结构 | 每个业务模块的 test |
| 2 | Spring Modulith 聚合 | 跨模块 internal 泄漏 + allowedDependencies 真实成立 | shop-server 的 test |
| 3 | ArchUnit | 分层 + 技术债（不重复模块规则） | shop-server 的 test |

## 怎么跑

```bash
mvn clean package                       # 全量构建，三层守卫全过
mvn -pl shop-server -am clean package   # 只构建入口及其依赖链（跳过无关模块）
```

## 从内存存储到真实数据库（ORM）

demo 的 order/payment 用内存 Map 存储，只为演示依赖方向。接数据库时**不需要手写
Repository 实现类**，三种姿势任选（取舍详见笔记 2.5）：

1. **务实派（推荐）**：给 `domain/Order.java` 直接加 `@Entity`/`@Id` 注解（`@Table(name="t_order")`），
   再建一个接口 `OrderJpaRepository extends JpaRepository<Order, Long>, OrderRepository`
   ——domain 接口里的 save/findById 被 Spring Data 自动实现，零转换代码。
2. **严格 DDD**：`Order` 保持纯净，另建 `OrderEntity`(@Entity) + `OrderMapper`（entity↔domain
   双向映射，字段少手写、多则 MapStruct），`OrderInfraRepository` 改为注入 JpaRepository + Mapper。
3. **ORM 关联**（Order 带 OrderItem）：务实派直接 `@OneToMany(mappedBy="order", cascade=ALL)`；
   严格派注解写在 OrderEntity 上，domain 里用普通 List，映射时带明细。

主键：现在是 `System.nanoTime()` 伪主键，接库后由 infra 换成 `@GeneratedValue(IDENTITY/SEQUENCE)`。

## 可裁剪演示（去掉 user 模块）

1. 注释掉根 `pom.xml` 里 `<module>shop-module-user</module>`
2. 注释掉 `shop-server/pom.xml` 里 `shop-module-user` 依赖
3. `mvn clean package` 依然通过（user 不被任何模块依赖，删掉无影响）

## 违规演示（守卫真的会拦）

1. 把 `examples/leaky-example/BadLeak.java` 拷到 `shop-common/src/main/java/com/acme/shop/common/`
2. `mvn clean package` → **编译失败**：`程序包com.acme.shop.order.service不存在`
   这是第 1 层 Maven 硬墙在编译期拦截（common 的类路径上没有 order，根本编译不过）
3. 删掉该文件还原

> Modulith（第 2 层）拦的是「能过 Maven 的包级违规」：模块间存在 artifact 级依赖
> （-api 契约工程）却 import 对方 internal 实现类的场景，由 shop-server 聚合 verify 拦截。

## 关键坑（实测）

- `allowedDependencies` 里用 `xxx::api` 引用别的模块的 api 包时，对方 api 包**必须有**
  `@NamedInterface("api")` 的 package-info 声明，否则聚合 verify 报
  `No named interface named 'api' found`。
- per-biz verify（`ApplicationModules.of("包名")`）需要 spring-boot 在测试类路径上
  （正常工程都有，不用额外处理）。
- 守卫依赖（spring-modulith-core / archunit-junit5）全部 `scope=test`，不进运行时。
- **叶子模块 api 子包不要写 `@NamedInterface`**，否则 per-biz verify 报
  `Module 'web' depends on non-exposed type ... within module 'api'`（见笔记 2.5）。

## 可测试性（DDD 的收益）

- `shop-module-order` 里的 `OrderApplicationServiceTest`：**纯 JUnit 单测**，
  `new OrderApplicationService(new OrderInfraRepository())` 直接测，毫秒级、不起 Spring 上下文
  ——因为 domain/application 不依赖 Spring，infrastructure 的内存实现本身就是测试替身。
- 真实项目：domain/application 纯单测；infrastructure 用 `@DataJpaTest` 切片；web 用 MockMvc。
