package com.base.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;

import java.util.Date;

public class TimeUtils {

    /**
     * 得到仿微信日期格式输出
     *
     * @param msgTimeMillis
     * @return
     */
    public static String getMsgFormatTime(long msgTimeMillis) {
        DateTime nowTime = new DateTime();
        DateTime msgTime = new DateTime(msgTimeMillis);
        int days = Days.daysBetween(nowTime, msgTime).getDays();
        if (days < 1) {
            //早上、下午、晚上 1:40
            int hourOfDay = msgTime.getHourOfDay();
            String when;
            if (hourOfDay >= 18) {//18-24
                when = "evening";
            } else if (hourOfDay >= 13) {//13-18
                when = "afternoon";
            } else if (hourOfDay >= 11) {//11-13
                when = "noon";
            } else if (hourOfDay >= 5) {//5-11
                when = "morning";
            } else {//0-5
                when = "early";
            }
            return when + " " + msgTime.toString("hh:mm");
        } else if (days == 1) {
            //昨天
            return "yesterday";
        } else if (days <= 7) {
            //星期
            switch (msgTime.getDayOfWeek()) {
                case DateTimeConstants.SUNDAY:
                    return "Sunday";
                case DateTimeConstants.MONDAY:
                    return "Monday";
                case DateTimeConstants.TUESDAY:
                    return "Tuesday";
                case DateTimeConstants.WEDNESDAY:
                    return "Wednesday";
                case DateTimeConstants.THURSDAY:
                    return "Thursday";
                case DateTimeConstants.FRIDAY:
                    return "Friday";
                case DateTimeConstants.SATURDAY:
                    return "Saturday";
            }
            return "";
        } else {
            //12月22日
            return msgTime.toString("MM moon dd day");
        }
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

    }



}
