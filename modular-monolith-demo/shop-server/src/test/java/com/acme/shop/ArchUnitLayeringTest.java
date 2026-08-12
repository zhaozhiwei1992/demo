package com.acme.shop;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * 第 3 层守卫：分层 + 技术债规则，只放在聚合工程（shop-server）测试里。
 * 注意：模块间依赖规则归 Modulith（第 2 层），这里【不重复】写模块规则。
 * 经验值：规则控制在 5~10 条，别写成几十上百条变成维护负担。
 */
@AnalyzeClasses(packages = "com.acme.shop")
class ArchUnitLayeringTest {

    // ==================== 分层规则 ====================

    /** 1. Controller 只能待在 web 包 */
    @ArchTest
    static final ArchRule controller_in_web =
        classes().that().haveSimpleNameEndingWith("Controller")
                 .should().resideInAPackage("..web..");

    /** 2. web 层只依赖契约（api），不依赖实现类（*Impl） */
    @ArchTest
    static final ArchRule web_only_depends_on_api =
        noClasses().that().resideInAPackage("..web..")
                   .should().dependOnClassesThat().haveSimpleNameEndingWith("Impl");

    /** 3. web 层禁止直接访问 repository */
    @ArchTest
    static final ArchRule web_not_touch_repository =
        noClasses().that().resideInAPackage("..web..")
                   .should().dependOnClassesThat().resideInAPackage("..repository..");

    /** 4. service 实现不得反向依赖 web */
    @ArchTest
    static final ArchRule service_not_touch_web =
        noClasses().that().resideInAPackage("..service..")
                   .should().dependOnClassesThat().resideInAPackage("..web..");

    /** 5. api 包只允许放接口 / DTO（契约卫生） */
    @ArchTest
    static final ArchRule api_only_contracts =
        classes().that().resideInAPackage("..api..")
                 .should().beInterfaces()
                 .orShould().haveSimpleNameEndingWith("Dto")
                 .orShould().haveSimpleNameEndingWith("DTO");

    // ==================== 技术债规则 ====================

    /** 6. 禁止 System.out（用日志框架） */
    @ArchTest
    static final ArchRule no_system_out =
        noClasses().should().callMethod(System.class, "out");

    /** 7. 禁止直接 new Date()（用 java.time） */
    @ArchTest
    static final ArchRule use_java_time =
        noClasses().should().callConstructor(java.util.Date.class);
}
