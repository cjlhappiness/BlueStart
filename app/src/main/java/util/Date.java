package util;

/*
日期时间工具类
*/

import java.util.Calendar;

public class Date {

    //获得当前年月
    public static String getDate(){
        int year, month;
        Calendar c = getCalendar();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        return String.format("%d年%d月", year, month);
    }

    //获得当前月份
    public static int getNowMonth(){
        int month;
        Calendar c = getCalendar();
        month = c.get(Calendar.MONTH) + 1;
        return  month;
    }

    //获得今天是星期几
    public static int getNowDayInWeek(){
        int day;
        Calendar c = getCalendar();
        day = c.get(Calendar.DAY_OF_WEEK) - 1;
        if(day == 0) day = 7;
        return day;
    }

    //获得指定日期是星期几
    public static int getOneDayInWeek(Calendar calendar){
        int day;
        Calendar c = calendar;
        day = c.get(Calendar.DAY_OF_WEEK) - 1;
        if(day == 0) day = 7;
        return day;
    }

    //获得本月第一天是星期几
    public static int getFirstDayInMonth(){
        int day;
        Calendar c = getCalendar();
        c.set(Calendar.DATE, 1);
        day = getOneDayInWeek(c);
        return day;
    }

    //获得当前在本月中的日期
    public static int getNowDayInMonth(){
        int day;
        Calendar c = getCalendar();
        day = c.get(Calendar.DATE);
        return day;
    }

    //获取本月总天数
    public static int getDayCountInMonth(){
        int count;
        Calendar c = getCalendar();
        c.set(Calendar.DATE, 1);
        c.roll(Calendar.DATE, -1);
        count = c.get(Calendar.DATE);
        return count;
    }

    //实例化一个日历对象
    public static Calendar getCalendar(){
        return Calendar.getInstance();
    }
}
