package com.github.springcloud.basecommon.utils;

import com.google.common.base.Strings;
import org.joda.time.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ganzhen on 25/01/2018.
 */
public class DateUtils {

    /**
     * 将Date转换成String
     * @param date
     * @param format 日期格式，如果不传的话，默认为"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String convertDate2String(Date date, String format){
        String res = "";
        if(date != null){
            if(Strings.isNullOrEmpty(format))
                format = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            res = sdf.format(date);
        }
        return res;
    }

    /**
     * 将String转换成Date
     * @param str
     * @param format 日期格式，如果不传的话，默认为"yyyy-MM-dd HH:mm:ss"
     * @return
     * @throws ParseException
     */
    public static Date convertString2Date(String str, String format) throws ParseException {
        Date res = null;
        if(!Strings.isNullOrEmpty(str)){
            if(Strings.isNullOrEmpty(format))
                format = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            res = sdf.parse(str);
        }
        return res;
    }

    /**
     * 获取日期的年、月、日（一年的第几日、一月的第几日、一周的第几日）
     * @param date
     * @param type 获取的类型：y，年；m，月；dy，一年的第几日；dm，一月的第几日；dw，一周的第几日
     * @return 返回年、月、日的整型，如果出错的话，返回最小的整数（Integer.MIN_VALUE）
     */
    public static int getYearOrMonthOrDay(Date date, String type){
        int res = Integer.MIN_VALUE;
        if(!Strings.isNullOrEmpty(type) && date != null){
            LocalDate ld = LocalDate.fromDateFields(date);
            switch (type){
                case "y":
                    res = ld.getYear();
                    break;
                case "m":
                    res = ld.getMonthOfYear();
                    break;
                case "dy":
                    res = ld.getDayOfYear();
                    break;
                case "dm":
                    res = ld.getDayOfMonth();
                    break;
                case "dw":
                    res = ld.getDayOfWeek();
                    break;
            }
        }
        return res;
    }

    /**
     * 获取两个日期之间的年、月、日、时、分、秒的差值
     * @param date1 被减数
     * @param date2 减数
     * @param type y，年；m，月；d，日；hour，时；min，分；sec，秒
     * @return 返回对应的值，如果出错的话，返回最小的整数（Integer.MIN_VALUE）
     */
    public static int differentYearOrMonthOrDayOrHoursOrMinOrSec(Date date1, Date date2, String type){
        int res = Integer.MIN_VALUE;
        if(!Strings.isNullOrEmpty(type) && date1 != null && date2 != null){
            DateTime d1 = new DateTime(date1);
            DateTime d2 = new DateTime(date2);
            switch(type){
                case "y":
                    res = Years.yearsBetween(d1,d2).getYears();
                    break;
                case "m":
                    res = Months.monthsBetween(d1,d2).getMonths();
                    break;
                case "d":
                    res = Days.daysBetween(d1,d2).getDays();
                    break;
                case "hour":
                    res = Hours.hoursBetween(d1,d2).getHours();
                    break;
                case "min":
                    res = Minutes.minutesBetween(d1,d2).getMinutes();
                    break;
                case "sec":
                    res = Seconds.secondsBetween(d1,d2).getSeconds();
                    break;
            }
        }
        return res;
    }

    /**
     * 获取某天日期的开始日期or截止日期
     * @param date
     * @param type min，每天的00:00:00；max，每天的23:59:59
     * @return 失败返回null
     * @throws ParseException
     */
    public static Date getMaxOrMinDateOfDay(Date date, String type) throws ParseException {
        Date res = null;
        if(date != null && !Strings.isNullOrEmpty(type)){
            String dayStr = convertDate2String(date, "yyyy-MM-dd");
            if(type.equals("min"))
                dayStr += " 00:00:00";
            else
                dayStr += " 23:59:59";

            res = convertString2Date(dayStr,"yyyy-MM-dd HH:mm:ss");
        }
        return res;
    }

    /**
     * 比较两个date的大小
     * @param date1
     * @param date2
     * @return date1=date2，返回0；date1>date2，返回1；date1<date2，返回-1；
     * @throws NullPointerException 如果date1和date2其中1个or2个为null，则抛异常
     */
    public int compareDate(Date date1, Date date2){
        int res = 0;
        if(date1 != null && date2 != null){
            DateTime d1 = new DateTime(date1);
            DateTime d2 = new DateTime(date2);
            res = d1.compareTo(d2);
        }
        else
            throw new NullPointerException("one or both of date1 and date2 is null");
        return res;
    }
}
