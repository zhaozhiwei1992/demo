package com.example.nacosexample.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: SecurityUtils
 * @Package com/example/nacosexample/util/SecurityUtils.java
 * @Description: 通过threadlocal 记录登录信息
 * @author zhaozhiwei
 * @date 2021/12/24 下午3:52
 * @version V1.0
 */
public final class SecurityUtils {

    private static final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    public static String getProvince(){
        return String.valueOf(threadLocal.get().get("province"));
    }

    public static String setProvince(String province){
        final HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("province", province);
        threadLocal.set(stringObjectHashMap);
        return province;
    }
}
