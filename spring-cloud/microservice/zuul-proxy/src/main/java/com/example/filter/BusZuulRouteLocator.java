package com.example.filter;

import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Title: BusZuulRouteLocator
 * @Package com/example/filter/BusZuulRouteLocator.java
 * @Description: 自定义进行路由筛选,减少进行路由匹配的范围
 * @author zhaozhiwei
 * @date 2024/2/22 下午4:13
 * @version V1.0
 */
public class BusZuulRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

	private static final Logger logger = LoggerFactory.getLogger(BusZuulRouteLocator.class);

	private ZuulProperties properties;

	public BusZuulRouteLocator(String servletPath, ZuulProperties properties) {
		super(servletPath, properties);
		this.properties = properties;
		logger.info("servletPath:{}", servletPath);
	}

	// 父类已经提供了这个方法，这里写出来只是为了说明这一个方法很重要！！！
//    @Override
//    protected void doRefresh() {
//        super.doRefresh();
//    }

	@Override
	public void refresh() {
		doRefresh();
	}

	private Map<String, ZuulRoute> currentBusRoutes = null;

	@Override
	protected Map<String, ZuulRoute> getRoutesMap() {
		return locateRoutes();
	}

	@Override
	protected Map<String, ZuulRoute> locateRoutes() {
		LinkedHashMap<String, ZuulRoute> routesMap = new LinkedHashMap<String, ZuulRoute>();
		// 从application.properties中加载路由信息
		routesMap.putAll(super.locateRoutes());
		// 加载本地的路由信息
//		routesMap.putAll(busZuulRouteService.getBusRoutes());
		// 优化一下配置
		Map<String, ZuulRoute> values = new ConcurrentHashMap<String, ZuulRoute>();
		for (Entry<String, ZuulRoute> entry : routesMap.entrySet()) {
			values.put(entry.getValue().getId(), entry.getValue());
		}
		currentBusRoutes = values;
		return currentBusRoutes;
	}

	@Override
	public Route getMatchingRoute(final String path) {
		RequestContext ctx = RequestContext.getCurrentContext();
		Route route = super.getSimpleMatchingRoute(path);
		ctx.getRequest().setAttribute("path",path);
		return route;
	}
	
	@Override
	protected ZuulRoute getZuulRoute(String path) {
		if (!matchesIgnoredPatterns(path)) {
			// 先使用系统配置的有序的路由集合进行匹配
//			BusZuulRouteDTO bo = busZuulRouteService.getTopMatch(path);
//			if (bo != null) {
//				return getRoutesMap().get(bo.getGuid());
//			}
//
//			// 没有结果，则使用系统内置的路由进行匹配
//			for (Entry<String, ZuulRoute> entry : getRoutesMap().entrySet()) {
//				String pattern = entry.getKey();
//				logger.info("正在匹配: {}", path);
//				if (this.antPathService.match(pattern, path)) {
//					return entry.getValue();
//				}
//			}
		}
		return null;
	}

	@Override
	protected Route getRoute(ZuulRoute route, String path) {
		return null;
	}

}
