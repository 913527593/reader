package com.reader.books.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * 读取网络请求后的数据
 * Created by Administrator on 2017/6/5.
 */

public class StreamUtil {
    /**
     *拼接服务器返回的数据
     * @param is InputStream
     * @return String
     */
    public static String creatStream(InputStream is){
        StringBuilder builder=null;
        try {
            BufferedReader reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
            String line;
            builder=new StringBuilder();
            if ((line=reader.readLine())!=null){
                builder.append(line);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
