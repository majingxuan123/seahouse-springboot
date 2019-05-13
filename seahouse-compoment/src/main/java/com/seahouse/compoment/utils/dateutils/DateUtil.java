package com.seahouse.compoment.utils.dateutils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */

public class DateUtil {

    public static final int YEAR = Calendar.YEAR;
    public static final int MONTH = Calendar.MONTH;
    public static final int DAY = Calendar.DATE;
    public static final int HOUR = Calendar.HOUR;
    public static final int MINUTE = Calendar.MINUTE;
    public static final int SECOND = Calendar.SECOND;


    /**
     * 将Date类型 格式化为发送过来的格式
     *
     * @param date
     * @param pattern yyyy-MM-dd HH:mm:ss  年-月-日 时：分：秒
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 把时间格式字符串转化为新的时间格式
     *
     * @param dateStr   字符串格式时间
     * @param pattern   原本的格式
     * @param toPattern 转化的格式
     * @return
     * @throws ParseException
     */
    public static String stringToString(String dateStr, String pattern, String toPattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = sdf.parse(dateStr);
        SimpleDateFormat sdf2 = new SimpleDateFormat(toPattern);
        return sdf2.format(date);
    }

    /**
     * @param dateStr 时间的String类型
     * @param pattern yyyy-MM-dd HH:mm:ss  年-月-日 时：分：秒
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String dateStr, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(dateStr);
    }

    /**
     * 对时间的年月日或者时分秒进行操作
     *
     * @param date  传入需要修改的时间
     * @param type  使用utils里面的几个属性  年月日时分秒
     * @param value 如   1   -1  代表时间+1 或者时间-1
     */
    public static Date addTime(Date date, int type, int value) {
        Calendar time_Calendar = Calendar.getInstance();
        //将时间对象设置进calindar对象
        //获取到date的 canlindar对象
        time_Calendar.setTime(date);
        time_Calendar.add(type, value);// 日期减少3个月
        return time_Calendar.getTime();
    }

    /**
     * 对时间的年月日或者时分秒进行操作
     *
     * @param date    传入需要修改的时间
     * @param type    使用utils里面的几个属性  年月日时分秒
     * @param value   如   1   -1  代表时间+1 或者时间-1
     * @param pattern yyyy-MM-dd HH:mm:ss  年-月-日 时：分：秒
     */
    public static String addTime(Date date, int type, int value, String pattern) {
        Calendar time_Calendar = Calendar.getInstance();
        //将时间对象设置进calindar对象
        //获取到date的 canlindar对象
        time_Calendar.setTime(date);
        time_Calendar.add(type, value);// 日期减少3个月
        return dateToString(time_Calendar.getTime(), pattern);
    }

    /**
     * 对时间的年月日或者时分秒进行操作
     *
     * @param date  传入需要修改的时间
     * @param type  使用utils里面的几个属性  年月日时分秒
     * @param value 如   1   -1  代表时间+1 或者时间-1
     */
    public static Date addTime(String dateStr, String pattern, int type, int value) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = sdf.parse(dateStr);
        Calendar time_Calendar = Calendar.getInstance();
        //将时间对象设置进calindar对象
        //获取到date的 canlindar对象
        time_Calendar.setTime(date);
        time_Calendar.add(type, value);// 日期减少3个月
        return time_Calendar.getTime();
    }

    /**
     * 修改String类型的时间的值
     *
     * @param dateStr    str类型的时间
     * @param pattern    旧的时间格式
     * @param type       使用utils里面的几个属性  年月日时分秒
     * @param value      如传入 1代表时间+1，传入-1代表时间-1
     * @param newPattern 新的时间格式
     * @return
     * @throws ParseException
     */
    public static String addTime(String dateStr, String pattern, int type, int value, String newPattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = sdf.parse(dateStr);
        Calendar time_Calendar = Calendar.getInstance();
        //将时间对象设置进calindar对象
        //获取到date的 canlindar对象
        time_Calendar.setTime(date);
        time_Calendar.add(type, value);// 日期减少3个月
        return dateToString(time_Calendar.getTime(), newPattern);
    }


    /**
     * @return String
     * @description 返回系统时间的字符串 (yyyy-MM-dd hh-mm-ss)
     * @date Nov 1, 2010
     * @author
     */
    public static String getTimes() {
        return dateToString(new Date(), "yyyy-MM-dd hh-mm-ss");
    }

    /**
     * @return String
     * @description 返回系统日期的字符串 (yyyy-MM-dd)
     * @date Nov 1, 2010
     * @author
     */
    public static String getDate() {
        return dateToString(new Date(), "yyyy-MM-dd");
    }


    /**
     * @return String
     * @description -返回系统现在时间的毫秒数
     * @date Nov 5, 2010 2:45:35 PM
     * @author
     */
    public static String getTimeMilliseconds() {
        return String.valueOf(new Date().getTime());
    }


    /**
     * 得到现在小时
     */
    public static String getHour() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String hour;
        hour = dateString.substring(11, 13);
        return hour;
    }

    /**
     * 得到现在分钟
     *
     * @return
     */
    public void getTime() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String min;
        min = dateString.substring(14, 16);
        System.out.println(min);
    }

}
