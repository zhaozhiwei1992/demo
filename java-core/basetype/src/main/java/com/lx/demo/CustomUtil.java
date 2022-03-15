package com.lx.demo;

import java.io.*;

public class CustomUtil {

    public static <T> T deepCopy(T src)
            throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        T dest = (T)in.readObject();
        return dest;
    }

}
