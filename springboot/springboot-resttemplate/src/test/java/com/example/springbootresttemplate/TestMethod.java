package com.example.springbootresttemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootresttemplate
 * @Description: TODO
 * @date 2021/7/5 下午7:52
 */
public class TestMethod {

    public static void main(String[] args) {
        String url = "http://127.0.0.1/pay/directzj/audit.page?mouldid=B9394089B1C3340CE0530501A8C0275D&vchtypeid=5F43CF521987101164163EFE399EC763&mainmenu=pay0200&submenu=B93A4D5E07587E86E0530501A8C01137&appid=pay&tokenid=2ABB1E119E395E6EEC48EFA9D6ABB0CDaTcvyUtV&mainmenu=pay0200&submenu=B93A4D5E07587E86E0530501A8C01137&menuId=B93A4D5E07587E86E0530501A8C01137&param5=V_PAY_VOUCHERMAIN&param3=5F43CF521987101164163EFE399EC763&param4=&param1=0&param2=B9394089B1C3340CE0530501A8C0275D&";

        final Map<String, String> urlparamsMap = getUrlparamsMap(url);
        System.out.println(urlparamsMap);
    }

    private static Map<String, String> getUrlparamsMap(String url) {
        Map<String, String> urlparams = new HashMap();
        char[] chars = url.toCharArray();
        int start = 0;
        int i = 0;
        for (; i < chars.length; i++) {
            if (chars[i] == '&') {
                putKV(url.substring(start, i), urlparams);
                start = i + 1;
            }
        }
        return urlparams;
    }

    private static void putKV(String stringToParse, Map<String, String> map) {
        String key;
        String value;
        // note that '=' can be a valid part of the value
        int firstEq = stringToParse.indexOf('=');
        if (firstEq == -1) {
            key = stringToParse;
            value = null;
        } else {
            key = stringToParse.substring(0, firstEq);
            value = stringToParse.substring(firstEq + 1);
        }
        if (!"mainmenu".equals(key)) {
            map.put(key, value);
        }
    }


}
