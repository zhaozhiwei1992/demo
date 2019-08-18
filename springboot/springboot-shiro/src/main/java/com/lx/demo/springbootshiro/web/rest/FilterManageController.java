package com.lx.demo.springbootshiro.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/filter")
public class FilterManageController {

    //加载自定义的拦截工厂
    @Autowired
    private ShiroFilterFactoryBean myShiroFilterFactoryBean;

    @GetMapping("/all")
    public Map<String, String> findAll() {
        return myShiroFilterFactoryBean.getFilterChainDefinitionMap();
    }

    @GetMapping(value = "/add")
    public Map<String, String> addFilter() {
        Map<String, String> filterMap = new HashMap<>();
        filterMap.put("/user/login**", "anon");
        filterMap.put("/admin/**", "anon");//把 admin 设置成不需要拦截
        filterMap.put("/super/**", "authc,roles[super],perms[super:info]");
        addAndUpdatePermission(filterMap);

        return myShiroFilterFactoryBean.getFilterChainDefinitionMap();
    }

    /**
     * 动态更新新的权限, 更新原权限，增加新权限
     *
     * @param filterMap
     */
    private synchronized void addAndUpdatePermission(Map<String, String> filterMap) {

        AbstractShiroFilter shiroFilter = null;
        try {
            shiroFilter = (AbstractShiroFilter) myShiroFilterFactoryBean.getObject();

            // 获取过滤管理器
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                    .getFilterChainResolver();
            DefaultFilterChainManager filterManager =
                    (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

            //清空拦截管理器中的存储
//            filterManager.getFilterChains().clear();
            /*
            清空拦截工厂中的存储,如果不清空这里,还会把之前的带进去
            ps:如果仅仅是更新的话,可以根据这里的 map 遍历数据修改,重新整理好权限再一起添加
             */
//            myShiroFilterFactoryBean.getFilterChainDefinitionMap().clear();

            Map<String, String> chains = myShiroFilterFactoryBean.getFilterChainDefinitionMap();
            //把修改后的 map 放进去
            filterMap.entrySet().forEach(fm -> {
                chains.putIfAbsent(fm.getKey(), fm.getValue());
            });
//            chains.putAll(filterMap);

            //这个相当于是全量添加
            for (Map.Entry<String, String> entry : filterMap.entrySet()) {
                //要拦截的地址
                String url = entry.getKey().trim().replace(" ", "");
                //地址持有的权限
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                //生成拦截
                filterManager.createChain(url, chainDefinition);
            }
        } catch (Exception e) {
            log.error("updatePermission error,filterMap=" + filterMap, e);
        }
    }
}
