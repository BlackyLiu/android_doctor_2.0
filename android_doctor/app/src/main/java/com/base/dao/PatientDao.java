package com.base.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.bean.Doctor;
import com.base.bean.Patient;
import com.base.util.Config;
import com.base.util.Reqs;

public class PatientDao extends Dao<Patient> {

    static PatientDao clz = null;

    public static PatientDao getInstance() {
        if (clz == null) {
            synchronized (PatientDao.class) {
                if (clz == null) {
                    clz = new PatientDao();
                }
            }
        }
        return clz;
    }


    public boolean reg(Patient t) {
        String methodName = "reg";
        String result = Reqs.post(Config.URL + moduleName + "/" + methodName, t);
        JSONObject jsonObject = JSON.parseObject(result);
        Integer code = jsonObject.getInteger("code");
        if (code == 200) {
            return true;
        }
        return false;

    }

}
