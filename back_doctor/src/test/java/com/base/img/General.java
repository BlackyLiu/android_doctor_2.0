package com.base.img;

import com.base.util.AuthTextService;
import com.base.util.BaseImg64;
import com.base.util.HttpUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.URLEncoder;

/**
 * OCR 通用识别
 */
public class General {

    public static String checkFile(String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            throw new NullPointerException("图片不存在");
        }
        String image = BaseImg64.getImageStrFromPath(path);

        return image;
    }


    public static void main(String[] args) {
        // 通用识别url
        //String otherHost = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
        String otherHost = "https://aip.baidubce.com/rest/2.0/ocr/v1/handwriting";
        // 本地图片路径
        String filePath = "D:\\res\\project\\java\\管理项目\\小学生考试系统\\1.jpg";

        try {
            String imgStr = BaseImg64.getImageStrFromPath(filePath);
            String params = URLEncoder.encode("image", "UTF-8") + "=" + imgStr;
            /**
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            String accessToken = AuthTextService.getAuth();
            String result = HttpUtil.post(otherHost, accessToken, params);
            System.out.println(result);

            //{"words_result":[{"location":{"top":888,"left":443,"width":420,"height":193},"words":"1+1=?"},{"location":{"top":1075,"left":387,"width":598,"height":225},"words":"2+2=?"}],"words_result_num":2,"log_id":1632643766179054570}
            //解析JSON串
            JSONObject data = new JSONObject(result);
            JSONArray jsonArray = data.getJSONArray("words_result");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jObject=jsonArray.getJSONObject(i);
                String str = jObject.getString("words");
                System.out.println(str);
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
