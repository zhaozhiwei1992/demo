package com.lx.demo;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class ChildProcessDemo {
    public static void main(String[] args) throws IOException {
        final OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        System.out.println(operatingSystemMXBean.getName());
//        System.out.println(operatingSystemMXBean.getArch());
        if("Linux".equals(operatingSystemMXBean.getName())){

            // exec native process
            final Process exec = Runtime.getRuntime().exec("ls");
            final InputStream inputStream = exec.getInputStream();
//            final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String data;
            while ((data = bufferedReader.readLine()) != null){
                System.out.println(data);
            }
        }
    }
}
