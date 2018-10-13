package com.lx.demo.dao;

import com.lx.demo.domain.Configuration;
import com.lx.demo.domain.common.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConfigurationDB {
    private static Map<Integer, Configuration> configurationDB = new ConcurrentHashMap<Integer, Configuration>();
    private static AtomicInteger idCounter = new AtomicInteger();

    public static Integer createConfiguration(String content, Status status){
        Configuration c = new Configuration();
        c.setId(idCounter.incrementAndGet());
        c.setContent(content);
        c.setStatus(status);
        configurationDB.put(c.getId(), c);

        return c.getId();
    }

    public static Configuration getConfiguration(Integer id){
        return configurationDB.get(id);
    }

    public static List<Configuration> getAllConfigurations(){
        return new ArrayList<Configuration>(configurationDB.values());
    }

    public static Configuration removeConfiguration(Integer id){
        return configurationDB.remove(id);
    }

    public static Configuration updateConfiguration(Integer id, Configuration c){
        return configurationDB.put(id, c);
    }
}

