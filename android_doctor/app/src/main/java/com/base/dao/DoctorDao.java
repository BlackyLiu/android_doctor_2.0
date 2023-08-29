package com.base.dao;

import com.base.bean.Doctor;
import com.base.util.FirstLetterUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DoctorDao extends  Dao<Doctor> {

    static  DoctorDao clz = null;

    public static  DoctorDao getInstance(){
          if(clz == null){
               synchronized (DoctorDao.class){
                    if(clz == null){
                           clz = new DoctorDao();
                    }
               }
          }
          return  clz;
    }

    public List<Doctor> query(Doctor t) {
        List<Doctor> d = super.query(t);
        Map<String,List<Doctor>> map = new HashMap<>();

        for (Doctor doctor : d) {
            String key = FirstLetterUtil.getFirstOne(doctor.getRealName());
            List<Doctor> doctors = map.get(key);
            if(doctors == null){
                doctors= new ArrayList<>();
            }
            doctors.add(doctor);
            map.put(key,doctors);
        }

        Set<Map.Entry<String, List<Doctor>>> entries = map.entrySet();
        Iterator<Map.Entry<String, List<Doctor>>> iterator = entries.iterator();
        List<Doctor> sortEd = new ArrayList<>();
        while (iterator.hasNext()){
            List<Doctor> values = iterator.next().getValue();
            for (Doctor value : values) {
            sortEd.add(value);
            }
        }
        return sortEd;
    }

}
