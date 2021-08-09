package com.hoau.bigdata.utils;

import java.io.*;
import java.util.List;

/**
 * @Description: TODO
 * @Author: zhaowei
 * @Date: 2020.12.24
 * @Time: 14:32
 */
public class CopyUtils {

    /**
     * 深度拷贝
     * @param src
     * @param <>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }

}
