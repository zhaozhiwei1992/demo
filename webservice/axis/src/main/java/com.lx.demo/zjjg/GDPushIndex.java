package com.lx.demo.zjjg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GDPushIndex {

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

    /**
     * http://tesb.gdczt.gov.cn:7800/olsupervise/services/IndexPushWebService?wsdl
     * @param unitCode
     * @param unitName
     * @param startTime
     * @param endTime
     * @param pageNum
     * @param vfCode
     * @return
     */
    public String PushIndex(String unitCode,String unitName,String startTime,String endTime,int pageNum,String vfCode){
        //读取json文件 file:/home/lx7ly/workspace/webservice/out/production/webservice/pushindex.json
        String jsonStr = this.readjson(String.valueOf(ClassLoader.getSystemResource("pushindex.json")).split(":")[1]);
        //解析成listmap
        System.out.println(pageNum);
        System.out.println(jsonStr);
        return jsonStr;
    }

    /**
     * 读取json文件
     * @param path
     * @return
     */
    public String readjson(String path) {
        String laststr="";
        File file=new File(path);
        BufferedReader reader=null;
        try{
            reader=new BufferedReader(new FileReader(file));
            String tempString=null;
            //int line=1;
            while((tempString=reader.readLine())!=null){
                //System.out.println("line"+line+":"+tempString);
                laststr=laststr+tempString;
                //line++;
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(reader!=null){
                try{
                    reader.close();
                }catch(IOException el){
                }  }  }
        return laststr;
    }

    public static void main(String[] args) {
        new GDPushIndex().PushIndexTotal("1", "", "", "", "");
    }
}
