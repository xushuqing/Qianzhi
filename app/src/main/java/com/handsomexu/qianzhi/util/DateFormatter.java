package com.handsomexu.qianzhi.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HandsomeXu on 2017/3/10.
 * 将long类型的date转换为String类型
 */

public class DateFormatter {

    public  String ZhihuDateFormat(long date){
        String sDate;
        //long类型先转为Date类型
        Date d = new Date(date + 24*60*60*1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        //Date类型转为String类型
        sDate = format.format(d);
        return sDate;
    }


    public  String DoubanDateFormat(long date){
        String sDate;
        //long类型先转为Date类型
        Date d = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //Date类型转为String类型
        sDate = format.format(d);
        return sDate;
    }

}
