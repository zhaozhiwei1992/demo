package com.lx.demo;

import com.lx.demo.service.ConfigurationResource;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * http://localhost:8080/network-management/configurations
 */
@ApplicationPath("/network-management")
public class NetworkApplication extends Application {

    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();

    public NetworkApplication() {
        singletons.add(new ConfigurationResource());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return empty;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}

