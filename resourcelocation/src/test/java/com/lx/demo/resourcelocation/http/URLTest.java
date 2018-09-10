package com.lx.demo.resourcelocation.http;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class URLTest {

    /**
     * 访问网页源码
     * @throws IOException
     */
    @Test
    public void testConnectURL() throws IOException {
        URL url = new URL("https://start.spring.io");
        InputStream inputStream = url.openStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while((line = bufferedReader.readLine()) != null){
            System.out.println(line);
        }
    }
}
