package com.acme.shop;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * 第 3 层守卫：分层（DDD 依赖方向）+ 技术债规则，只放在聚合工程（shop-server）测试里。
 * 模块间依赖规则归 Modulith（第 2 层），这里【不重复】写模块规则。
 * 经验值：规则控制在 5~10 条，别写成几十上百条变成维护负担。
 *
 * DDD 依赖方向（核心模块 order/payment）：
 *   web → application → domain ← infrastructure
 *   web → api（契约）；application 实现 api 契约
 * 非核心模块（user）只要求 web → api / service。
 */
@AnalyzeClasses(packages = "com.acme.shop")
class ArchUnitLayeringTest {

    // ==================== 分层规则（DDD 方向） ====================

    /** 1. Controller 只能待在 web 包 */
    @ArchTest
    static final ArchRule controller_in_web =
        classes().that().haveSimpleNameEndingWith("Controller")
                 .should().resideInAPackage("..web..");

    /** 2. web 层不得碰 domain / infrastructure（只能依赖 api / application） */
    @ArchTest
    static final ArchRule web_not_touch_domain =
        noClasses().that().resideInAPackage("..web..")
                   .should().dependOnClassesThat()
                   .resideInAnyPackage("..domain..", "..infrastructure..");

    /** 3. web 层禁止依赖实现类（*Impl），只注入接口 */
    @ArchTest
    static final ArchRule web_only_inject_interfaces =
        noClasses().that().resideInAPackage("..web..")
                   .should().dependOnClassesThat().haveSimpleNameEndingWith("Impl");

    /** 4. application / service 不得反向依赖 web */
    @ArchTest
    static final ArchRule app_not_touch_web =
        noClasses().that().resideInAnyPackage("..application..", "..service..")
                   .should().dependOnClassesThat().resideInAPackage("..web..");

    /** 5. domain 是纯领域：不得依赖 web / application / infrastructure */
    @ArchTest
    static final ArchRule domain_is_pure =
        noClasses().that().resideInAPackage("..domain..")
                   .should().dependOnClassesThat()
                   .resideInAnyPackage("..web..", "..application..", "..infrastructure..");

    /** 6. api 包只允许放接口 / DTO（契约卫生） */
    @ArchTest
    static final ArchRule api_only_contracts =
        classes().that().resideInAPackage("..api..")
                 .should().beInterfaces()
                 .orShould().haveSimpleNameEndingWith("Command")
                 .orShould().haveSimpleNameEndingWith("Dto")
                 .orShould().haveSimpleNameEndingWith("DTO");

    /** 7. infrastructure 只实现 domain 接口，不得依赖 web / application / api */
    @ArchTest
    static final ArchRule infra_not_touch_upper =
        noClasses().that().resideInAPackage("..infrastructure..")
                   .should().dependOnClassesThat()
                   .resideInAnyPackage("..web..", "..application..", "..api..");

    // ==================== 技术债规则 ====================

    /** 8. 禁止 System.out（用日志框架） */
    @ArchTest
    static final ArchRule no_system_out =
        noClasses().should().callMethod(System.class, "out");

    /** 9. 禁止直接 new Date()（用 java.time） */
    @ArchTest
    static final ArchRule use_java_time =
        noClasses().should().callConstructor(java.util.Date.class);
}
