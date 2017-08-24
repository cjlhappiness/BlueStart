package util;

/*
日期时间工具类
*/

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Date {

    //获得当前系统中年月字符串
    public static String[] getDateString(){
        List l = getDate();
        String dateO  = String.format("%d年%d月", l.get(0), l.get(1));
        String dateT  = String.format("%d%02d", l.get(0), l.get(1));
        String[] date = new String[]{dateO, dateT};
        return date;
    }

    //获得指定日期年月字符串
    public static String[] getDateString(int monthLength){
        List l = getDate(monthLength);
        String dateO  = String.format("%d年%d月", l.get(0), l.get(1));
        String dateT  = String.format("%d%02d", l.get(0), l.get(1));
        String[] date = new String[]{dateO, dateT};
        return date;
    }

    //获得当前系统中年月
    public static List getDate(){
        int year, month;
        List l = new ArrayList();
        Calendar c = getCalendar();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        l.add(year);
        l.add(month);
        return l;
    }

    //获得指定日期年月
    public static List getDate(int monthLength){
        int year, month;
        List l = new ArrayList();
        Calendar c = getCalendar();
        c.add(Calendar.MONTH, monthLength);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        l.add(year);
        l.add(month);
        return l;
    }

    //获得当前系统年份
    public static int getNowYear(){
        int year;
        Calendar c = getCalendar();
        year = c.get(Calendar.YEAR);
        return year;
    }

    //获得当前系统中当前月份
    public static int getNowMonth(){
        int month;
        Calendar c = getCalendar();
        month = c.get(Calendar.MONTH) + 1;
        return  month;
    }

    //获得当前系统中现在是周几
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

    //获得当前系统中第一天是星期几
    public static int getFirstDayInMonth(){
        int day;
        Calendar c = getCalendar();
        c.set(Calendar.DATE, 1);
        day = getOneDayInWeek(c);
        return day;
    }

    //获得指定月第一天是星期几
    public static int getFirstDayInMonth(int monthLength){
        int day;
        Calendar c = getCalendar();
        c.add(c.MONTH, monthLength);
        c.set(Calendar.DATE, 1);
        day = getOneDayInWeek(c);
        return day;
    }

    //获得系统日期中当前日期
    public static int getNowDayInMonth(){
        int day;
        Calendar c = getCalendar();
        day = c.get(Calendar.DATE);
        return day;
    }

    //获得系统日期中本月总天数
    public static int getDayCountInMonth(){
        int count;
        Calendar c = getCalendar();
        c.set(Calendar.DATE, 1);
        c.roll(Calendar.DATE, -1);
        count = c.get(Calendar.DATE);
        return count;
    }

    //获得指定月总天数
    public static int getDayCountInMonth(int monthLength){
        int count;
        Calendar c = getCalendar();
        c.add(Calendar.MONTH, monthLength);
        c.set(Calendar.DATE, 1);
        c.roll(Calendar.DATE, -1);
        count = c.get(Calendar.DATE);
        return count;
    }

    public static String getDateTime(){
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = format.format(date);
        return dateTime;
    }

    //实例化一个日历对象
    public static Calendar getCalendar(){
        return Calendar.getInstance();
    }
}
