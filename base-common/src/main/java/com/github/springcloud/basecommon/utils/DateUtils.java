package com.github.springcloud.basecommon.utils;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.joda.time.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    /**
     * 给原始时间，加上一个规定类型的数值，获得结果时间
     * @param org 原始时间
     * @param add 加数
     * @param type 类型：y，年；m，月；d，日；hour，时；min，分；sec，秒
     * @return
     */
    public static Date plus(Date org, int add, String type){
        Date res = null;
        if(org != null && !Strings.isNullOrEmpty(type)){
            DateTime dt = new DateTime(org);
            switch(type){
                case "y":
                    res = dt.plusYears(add).toDate();
                    break;
                case "m":
                    res = dt.plusMonths(add).toDate();
                    break;
                case "d":
                    res = dt.plusDays(add).toDate();
                    break;
                case "hour":
                    res = dt.plusHours(add).toDate();
                    break;
                case "min":
                    res = dt.plusMinutes(add).toDate();
                    break;
                case "sec":
                    res = dt.plusSeconds(add).toDate();
                    break;
            }
        }
        return res;
    }

    /**
     * 计算给定时间段的每一天的日期
     * @param from 起始时间
     * @param to 结束时间
     * @param maxYears 从结束时间往前推，获取的最大年份范围，如果超过to-from，则from的值就变成to-maxYears;maxYears＜0，则就计算from-to的范围的每天日期
     * @return
     */
    public static List<Date> getEveryDayFromThen2NowByMaxYears(Date from, Date to, int maxYears){
        List<Date> dates = Lists.newArrayList();
        int count = 1;//记录已生成的数量，与maxYears作比较
        Date tmp = from;//临时值
        int realYears = differentYearOrMonthOrDayOrHoursOrMinOrSec(from,to,"y");
        if(maxYears>=0 && realYears>maxYears)//now-then>maxYears，则从now-maxYears开始算
            tmp = plus(to,0-maxYears,"y");
        while(tmp.compareTo(to)<1){
            dates.add(tmp);
            tmp=plus(tmp,1,"d");
        }
        return dates;
    }

    /**
     * 判断给定日期是星期几
     * @param date
     * @return 返回结果从1--7，与周一到周日一一对应
     */
    public static int dayOfWeek(Date date){
        if(date != null){
            DateTime dt = new DateTime(date);
            return dt.dayOfWeek().get();
        }
        return -1;
    }

    public static void main(String[] args){
        Date now = new Date();
        try {
            dayOfWeek(convertString2Date("2018-04-22","yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
