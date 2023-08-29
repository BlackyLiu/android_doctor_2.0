package com.base.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.bean.Chat;
import com.base.util.Config;
import com.base.util.Reqs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatDao  extends  Dao<Chat> {

    static  ChatDao clz = null;

    public static  ChatDao getInstance(){
        if(clz == null){
            synchronized (ChatDao.class){
                if(clz == null){
                    clz = new ChatDao();
                }
            }
        }
        return  clz;
    }


    public  List<Chat>  queryTxYh(Long id){
        String methodName = "queryTxYh";
        String result = Reqs.get(Config.URL + moduleName + "/" + methodName,id);
        JSONObject jsonObject = JSON.parseObject(result);
        Integer code = jsonObject.getInteger("code");
        if (code == 200) {
            result = jsonObject.getString("data");
            List<Chat> data = JSON.parseArray(result, Chat.class);
            return data;
        }
        return null;
    }


    public  List<Chat>  queryYxQb(Long myId,Long hisId){
        String methodName = "queryYxQb";
        Map<String,String> p = new HashMap<>();
        p.put("myId",myId.toString());
        p.put("hisId",hisId.toString());
        String result = Reqs.get(Config.URL + moduleName + "/" + methodName,p);
        JSONObject jsonObject = JSON.parseObject(result);
        Integer code = jsonObject.getInteger("code");
        if (code == 200) {
            result = jsonObject.getString("data");
            List<Chat> data = JSON.parseArray(result, Chat.class);
            return data;
        }
        return null;
    }


}

































