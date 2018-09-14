package com.lx.demo.zjjg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GDPushIndexTotal {

    /**
     * http://tesb.gdczt.gov.cn:7800/olsupervise/services/IndexPushWebService?wsdl
     * @param unitCode
     * @param unitName
     * @param startTime
     * @param endTime
     * @param vfCode
     * @return
     */
    public String PushIndexTotal(String unitCode,String unitName,String startTime,String endTime, String vfCode){
        String json = "{\n" +
                "\n" +
                "    \"code\": 0,\n" +
                "    \"message\": \"成功\",\n" +
                "    \"totalRow\": \"10\",\n" +
                "    \"totalPage\": \"1\"\n" +
                "    }";
        System.out.println(unitCode);
        System.out.println(json);
        return json;
    }

    public static void main(String[] args) {
        new GDPushIndexTotal().PushIndexTotal("1", "", "", "", "");
    }
}
