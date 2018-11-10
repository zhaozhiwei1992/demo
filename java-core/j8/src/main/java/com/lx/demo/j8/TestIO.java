package com.lx.demo.j8;

import java.io.*;

public class TestIO {

    public static void main(String[] args) {
        //old
//        String result = processFile();

        String result = processFile((BufferedReader br) -> br.readLine() + br.readLine());
        System.out.println(result);
    }

    @FunctionalInterface
    public interface BufferedReaderProcessor{
        String processFile(BufferedReader br) throws IOException;
    }

    private static String processFile(BufferedReaderProcessor bufferedReaderProcessor) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("/tmp/7001.out"));
            return bufferedReaderProcessor.processFile(bufferedReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String processFile(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/tmp/7001.out"));
            return bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
