//package com.example.filter;
//
//import com.thetransactioncompany.cors.CORSConfiguration;
//import com.thetransactioncompany.cors.CORSConfigurationException;
//import com.thetransactioncompany.cors.CORSFilter;
//
//import javax.servlet.*;
//import java.io.IOException;
//import java.util.Properties;
//
//@javax.servlet.annotation.WebFilter(urlPatterns={"/v2/api-docs"},filterName="swaggerCORSFilter")
//public class SwaggerCORSFilter implements Filter {
//
//
//
//	private static CORSFilter filter;
//	@Override
//	public void destroy() {
//
//
//	}
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		if(filter!=null) {
//			filter.doFilter(request, response, chain);
//		}else {
//			chain.doFilter(request, response);
//		}
//
//	}
//
//	@Override
//	public void init(FilterConfig arg0) throws ServletException {
//		Properties p = new Properties();
//		p.setProperty("cors.allowOrigin", "*");
//		p.setProperty("cors.supportedMethods", "GET");
//		p.setProperty("cors.supportedHeaders", "Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
//		p.setProperty("cors.exposedHeaders", "Set-Cookie");
////		p.setProperty("cors.supportsCredentials", "true");
//
//		try {
//			CORSConfiguration config = new CORSConfiguration(p);
//			filter = new CORSFilter();
//			filter.setConfiguration(config);
//		} catch (CORSConfigurationException e) {
//			e.printStackTrace();
//		}
//	}
//
//}
