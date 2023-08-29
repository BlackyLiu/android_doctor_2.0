package com.base.util;

import android.text.TextUtils;

import com.base.bean.User;

import java.util.Timer;

public class Commons {

     public  static  final Timer timer = new Timer();


     public static String getShowName(User user) {
          return (!TextUtils.isEmpty(user.getRealName())) ? user.getRealName() : user.getUsername();
     }
}
