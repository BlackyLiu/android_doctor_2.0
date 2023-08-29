package com.base.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateOlds {

    private final static String pattern = "yyyy-MM-dd HH:mm:ss";

    private final static  DateFormat dfm  = new SimpleDateFormat(pattern);


    public static String formatDate(Date date) {
        if(date ==null) {
            return "";
        }
        return dfm.format(date);
    }

    public static String formatDate(Date date,String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }


    public static Date parseD(String date) throws ParseException {
        return dfm.parse(date);
    }

    public static Date parse(String date)  {
        try {
            return dfm.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String nowStr() {
        return formatDate(new Date());
    }


    public static String nowStr(String pattern) {
        return formatDate(new Date(),pattern);
    }


    public static long diffMinute(Date beforeDate) {
        Date  now = new Date();
        return (now.getTime() - beforeDate.getTime())/(1000*60);
    }


    public  static  boolean isToday(Date date){
        long dayMis = 60*60*24*1000;
        Date now = new Date();
        return  Math.abs(date.getTime() - now.getTime())< dayMis;
    }


    /**
     * 返回当前时间与传过来的时间 距离多少个小时
     * @param date
     * @return
     */
    public static long nowDiffYesHour(Date date) {
        Date now = new Date();
        long diff = Math.abs(now.getTime() - date.getTime());
        long hour = diff/1000/60/60;
        return hour;
    }


    public static void main(String[] args) {
        Date date  = new Date();
        String str = "2021-03-19 8:10:21";
        Date d = DateOlds.parse(str);
        boolean today = DateOlds.isToday(d);
        System.out.println(today);


    }



}
