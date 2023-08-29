package com.base.util;

import com.base.bean.Doctor;
import com.base.bean.MCourse;
import com.base.bean.MQuantification;
import com.base.bean.Patient;
import com.base.bean.User;

public class Config {

    //     public  static String URL = "http://localhost:8080/";
//    public static String URL = "http://192.168.1.108:80/";
    public static String URL = "http://192.168.136.26:80/";


    public static String role = "doctor";


    //当前登录的用户
    public static Object currUser = null;

    //当前点击的通信的id
    public static long hisId = -1;
    public static String hisName = "";


    // 从预约 进入 预约详细页的用户信息
    public static  Object appointmentInfo = null;

    public static String myAction = "name";



    public  static  Long getCurId(){
        Long id = -1L;
        if(Config.role.equals("doctor")){
            Doctor d = (Doctor) Config.currUser;
            id = d.getId();
        }else {
            Patient p = (Patient) Config.currUser;
            id = p.getId();
        }
        return id;
    }

    public  static  String getCurShowName(){
        String name = "";
        if(Config.role.equals("doctor")){
            Doctor d = (Doctor) Config.currUser;
            name=d.getRealName();
        }else {
            Patient p = (Patient) Config.currUser;
            name=p.getRealName();
        }
        return name;
    }






}
