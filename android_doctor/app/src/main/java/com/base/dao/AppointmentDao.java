package com.base.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.bean.Appointment;
import com.base.bean.Doctor;
import com.base.util.Config;
import com.base.util.FirstLetterUtil;
import com.base.util.Reqs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AppointmentDao extends Dao<Appointment> {

    static AppointmentDao clz = null;

    public static AppointmentDao getInstance() {
        if (clz == null) {
            synchronized (AppointmentDao.class) {
                if (clz == null) {
                    clz = new AppointmentDao();
                }
            }
        }
        return clz;
    }

    public List<Appointment> queryDoctorApp(Long id) {
        String methodName = "queryDoctorApp";
        String result = Reqs.post(Config.URL + moduleName + "/" + methodName, id);
        JSONObject jsonObject = JSON.parseObject(result);
        Integer code = jsonObject.getInteger("code");
        if (code == 200) {
            result = jsonObject.getString("data");
            List<Appointment> data = JSON.parseArray(result, Appointment.class);

            Map<String, List<Appointment>> map = new HashMap<>();

            for (Appointment datum : data) {
                String key = FirstLetterUtil.getFirstOne(datum.getAppointmentName());
                List<Appointment> appointments = map.get(key);
                if (appointments == null) {
                    appointments = new ArrayList<>();
                }
                appointments.add(datum);
                map.put(key, appointments);
            }
            List<Appointment> sortEd = new ArrayList<>();
            Iterator<Map.Entry<String, List<Appointment>>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()){
                List<Appointment> values = iterator.next().getValue();
                for (Appointment value : values) {
                    sortEd.add(value);
                }
            }
            return sortEd;
        }
        return null;
    }


}
