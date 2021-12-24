package com.example.nacosexample.rule;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.example.nacosexample.util.SecurityUtils;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: CustomRouteLoadBalancer
 * @Package com/example/nacosexample/config/CustomRouteLoadBalancer.java
 * @Description: 自定义负载实现, 根据配置的meta信息控制负载范围
 * @author zhaozhiwei
 * @date 2021/12/24 下午3:34
 * @version V1.0
 */
public class CustomRouteLoadBalancer implements ILoadBalancer {

    private static Logger logger = LoggerFactory.getLogger(CustomRouteLoadBalancer.class);

    /**
     * 分隔符
     */
    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final ILoadBalancer loadBalancer;

    public CustomRouteLoadBalancer(ILoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    /**
     * Initial list of servers. This API also serves to add additional ones at a
     * later time The same logical server (host:port) could essentially be added
     * multiple times (helpful in cases where you want to give more "weightage"
     * perhaps ..)
     *
     * @param newServers new servers to add
     */
    @Override
    public void addServers(List<Server> newServers) {
        loadBalancer.addServers(newServers);
    }

    /**
     * Choose a server from load balancer.
     *
     * @param key An object that the load balancer may use to determine which server
     *            to return. null if the load balancer does not use this parameter.
     * @return server chosen
     */
    @Override
    public Server chooseServer(Object key) {
        return loadBalancer.chooseServer(key);
    }

    /**
     * To be called by the clients of the load balancer to notify that a Server is
     * down else, the LB will think its still Alive until the next Ping cycle -
     * potentially (assuming that the LB Impl does a ping)
     *
     * @param server Server to mark as down
     */
    @Override
    public void markServerDown(Server server) {
        loadBalancer.markServerDown(server);
    }

    /**
     * @param availableOnly if true, only live and available servers should be
     *                      returned
     * @deprecated 2016-01-20 This method is deprecated in favor of the cleaner
     * {@link #getReachableServers} (equivalent to availableOnly=true)
     * and {@link #getAllServers} API (equivalent to
     * availableOnly=false).
     * <p>
     * Get the current list of servers.
     */
    @Override
    public List<Server> getServerList(boolean availableOnly) {
        return loadBalancer.getServerList(availableOnly);
    }

    /**
     * @return Only the servers that are up and reachable.
     */
    @Override
    public List<Server> getReachableServers() {
        return loadBalancer.getReachableServers();
    }

    /**
     * @data: 2021/12/24-下午3:35
     * @User: zhaozhiwei
     * @method: getAllServers

     * @return: java.util.List<com.netflix.loadbalancer.Server>
     * @Description: 自定义控制负载的范围, 如果控制返回一个则精确指定一个
     */
    @Override
    public List<Server> getAllServers() {
        List<Server> servers = loadBalancer.getAllServers();

        String currentPro = SecurityUtils.getProvince();
        if (StringUtils.isEmpty(currentPro)) {
            return servers;
        }
        // 按财年匹配成功的server
        List<Server> matchServers = new ArrayList<Server>();
        // 按default匹配成功的server
        List<Server> defaultServers = new ArrayList<Server>();
        // 默认提供所有服务的server
        List<Server> allServers = new ArrayList<Server>();
        for (Server server : servers) {
            String region = null;
//            if (server instanceof ServiceCombServer) {
//                // 华为的注册中心服务
//                DataCenterInfo dataCenterInfo = ((ServiceCombServer) server).getInstance().getDataCenterInfo();
//                if (dataCenterInfo != null) {
//                    region = ((ServiceCombServer) server).getInstance().getDataCenterInfo().getRegion();
//                }
//            } else if (server instanceof DiscoveryEnabledServer) {
//                region = ((DiscoveryEnabledServer) server).getInstanceInfo().getMetadata().get("serviceprovince");
//            } else
            if (server instanceof NacosServer) {
                region = ((NacosServer) server).getMetadata().get("serviceprovince");
            }
            if (region == null) {
                // 服务全部区划
                allServers.add(server);
                continue;
            }
            if ("ALL".equals(region)) {
                // 服务全部区划
                allServers.add(server);
                continue;
            }
            if (!StringUtils.isEmpty(region)) {
                if (match(region, currentPro)) {
                    logger.info("匹配服务分流：" + region + "," + currentPro);
                    // 匹配上财年
                    matchServers.add(server);
                    continue;
                }
                if (region.contains("default")) {
                    // 配置了default
                    defaultServers.add(server);
                }
            }
        }
        if (!matchServers.isEmpty()) {
            // 优先使用匹配上的服务
            return matchServers;
        } else if (!defaultServers.isEmpty()) {
            // 其次使用default的服务
            // 使用all服务
            return defaultServers;
        } else if (!allServers.isEmpty()) {
            return allServers;
        }
        return new ArrayList<Server>(0);

    }

    private boolean match(String region, String currentPro) {

        // 获取当前登陆用户的财政
        for (String province : region.split(",")) {
            // 用户年度与当前系统年度匹配
            if (antPathMatcher.match(province, currentPro) || currentPro.equalsIgnoreCase(province)
                    || currentPro.startsWith(province)) {
                return true;
            }
        }
        return false;

    }
}
