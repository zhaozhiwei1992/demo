package com.example.springbootactivity.activiti5.util;

import com.alibaba.fastjson.JSONObject;

public class JsonUtil {

    private final static JSONObject jsonObject = new JSONObject();

    public static JSONObject getFailJson(String msg) {
        return result(msg, null, 0);
    }

    public static JSONObject getFailJson(String msg, Object object) {
        return result(msg, object, 0);
    }


    public static JSONObject getSuccessJson(String msg) {
        return result(msg, null, 0);
    }

    public static JSONObject getSuccessJson(String msg, Object object) {
        return result(msg, object, 0);
    }


    public static synchronized JSONObject result(String msg, Object obj, Integer code) {
        jsonObject.clear();

        jsonObject.put("msg", msg);
        jsonObject.put("data", obj);
        jsonObject.put("code", code);

        return jsonObject;
    }
}
