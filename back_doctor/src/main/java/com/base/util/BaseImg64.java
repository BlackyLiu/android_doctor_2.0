package com.base.util;

import sun.misc.BASE64Encoder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

public class BaseImg64 {
    /**
     * 将一张本地图片转化成Base64字符串
     * @param imgPath 本地图片地址
     * @return 图片转化base64后再UrlEncode结果
     */
    public static String getImageStrFromPath(String imgPath) throws Exception{
           return getImageStrFromPath(new File(imgPath));
    }


    public  static  String getImageStrFromPath(File file) throws Exception{
        BufferedInputStream in;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过再URLEncode的字节数组字符串
        return URLEncoder.encode(encoder.encode(data),"UTF-8");

    }

}
