package com.lx.demo.simplex;

import java.io.*;
import java.net.Socket;

/**
 * 为甚么要用println {@link https://www.cnblogs.com/wxgblogs/p/5347996.html}
 *
 * println == write + flush
 */
public class Client {

    public static void main(String[] args) {

        try {

            //建立连接
            Socket socket = new Socket("127.0.0.1", 8888);

            PrintWriter bufferedWriter = new PrintWriter(socket.getOutputStream(), true);
//            BufferedReader sysBuff = new BufferedReader(new InputStreamReader(System.in));
            // 这里不用println就死翘翘了
//            bufferedWriter.println(sysBuff.readLine());

//            bufferedWriter.println("laji ");
            // 如果用write 显示加入换行
            bufferedWriter.write("lll222l\r\n");
            bufferedWriter.flush();
            System.out.println("写出完成..");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String s = bufferedReader.readLine();
            System.out.println("服务端传入消息" + s);

//            bufferedReader.close();
//            bufferedWriter.close();
//            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
