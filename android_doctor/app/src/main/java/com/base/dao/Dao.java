package com.base.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.bean.MCourse;
import com.base.util.Config;
import com.base.util.Reqs;
import com.base.util.Strings;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dao<T> {

    String moduleName;
    Class<T> clz;

    public Dao() {
        // 获取应用时 UserDao extend Dao<User> 的泛型实体类 User
        Type type = this.getClass().getGenericSuperclass();
        ParameterizedType p = (ParameterizedType) type;
        clz = (Class<T>) p.getActualTypeArguments()[0];
        moduleName = Strings.f2L(clz.getSimpleName());
    }


    public boolean add(T t) {
        String methodName = "add";
//        String content = JSON.toJSONString(t);
        String result = Reqs.post(Config.URL + moduleName + "/" + methodName, t);
        JSONObject jsonObject = JSON.parseObject(result);
        Integer code = jsonObject.getInteger("code");
        if (code == 200) {
            return true;
        }
        return false;
    }


    public boolean delete(long id) {
        String methodName = "delete";
        Map<String, String> map = new HashMap<>();
        map.put("id", id + "");
        String result = Reqs.get(Config.URL + moduleName + "/" + methodName, map);
        JSONObject jsonObject = JSON.parseObject(result);
        Integer code = jsonObject.getInteger("code");
        if (code == 200) {
            return true;
        }
        return false;
    }


    public boolean update(T t) {
        String methodName = "update";
        String result = Reqs.post(Config.URL + moduleName + "/" + methodName, t);
        JSONObject jsonObject = JSON.parseObject(result);
        Integer code = jsonObject.getInteger("code");
        if (code == 200) {
            return true;
        }
        return false;
    }


    public boolean update(Map<String,String> map) {
        String methodName = "update";
        String result = Reqs.post(Config.URL + moduleName + "/" + methodName, map);
        JSONObject jsonObject = JSON.parseObject(result);
        Integer code = jsonObject.getInteger("code");
        if (code == 200) {
            return true;
        }
        return false;
    }

    public List<T> query(T t) {
        String methodName = "queryCondition";
        String result = Reqs.get(Config.URL + moduleName + "/" + methodName,t);
        JSONObject jsonObject = JSON.parseObject(result);
        Integer code = jsonObject.getInteger("code");
        if (code == 200) {
            result = jsonObject.getString("data");
            List<T> data = JSON.parseArray(result, clz);
            return data;
        }
        return null;
    }

    public List<T> queryAllData() {
        String methodName = "queryAllData";
        String result = Reqs.get(Config.URL + moduleName + "/" + methodName);
        JSONObject jsonObject = JSON.parseObject(result);
        Integer code = jsonObject.getInteger("code");
        if (code == 200) {
            result = jsonObject.getString("data");
            List<T> data = JSON.parseArray(result, clz);
            return data;
        }
        return null;
    }



}
