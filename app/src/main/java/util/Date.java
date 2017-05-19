package util;

/*
日期时间工具类
*/

import java.util.Calendar;

public class Date {

    //获得当前月份
    public static int getNowMonth(){
        int month;
        Calendar c = Calendar.getInstance();
        month = c.get(Calendar.MONTH);
        return  month;
    }

}
