package com.lx.demo.zjjg;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class GDPushIndex {

    /**
     * http://tesb.gdczt.gov.cn:7800/olsupervise/services/IndexPushWebService?wsdl
     *
     * @param unitCode
     * @param unitName
     * @param startTime
     * @param endTime
     * @param vfCode
     * @return
     */
    public String PushIndex(String unitCode, String unitName, String startTime, String endTime, String vfCode) {
        URL resource = Thread.currentThread().getContextClassLoader().getResource("pushindex.json");
        String jsonStr = this.readjson(resource.getPath()).trim();
        System.out.println(jsonStr);
        return jsonStr;
    }

    /**
     * 读取json文件
     *
     * @param path
     * @return
     */
    public String readjson(String path) {
        StringBuilder laststr = new StringBuilder();
        File file = new File(path);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
        return laststr.toString();
    }

    public static void main(String[] args) {
        new GDPushIndex().PushIndex("1", "", "", "", "");
    }
}
