package cn.zyblogs.example.utils;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @Title: FormatDate.java
 * @Description:
 * SimpleDateFormat源码中,所有的格式化和解析都需要通过一个中间对象进行转换，那就是Calendar，线程不安全的，试想一下当我们有多个线程操作同一个Calendar的时候后来的线程会覆* 盖先来线程的数据，那最后其实返回的是后来线程的数据，
 *
 * LocalDate无法包含时间；
 * LocalTime无法包含日期；
 * LocalDateTime同时包含日期和时间。
 *
 * @Version V1.0
 */
public class DateUtils {

    /**
     * 获得当前LocalDateTime
     * @return
     */
    public static LocalDateTime getLocalDateTime(){
        return LocalDateTime.now();
    }

    /**
     * LocalDateTime转换毫秒
     * @return
     */
    public static long LocalDateTime2Millisecond(LocalDateTime time){
        return time.atZone(ZoneId.of("Asia/Shanghai")).toInstant().toEpochMilli();
    }

    /**
     * 毫秒转换LocalDateTime
     * @param sa
     * @return
     */
    public static LocalDateTime millisecond2LocalDateTime(long sa){
        return Instant.ofEpochMilli(sa).atZone(ZoneId.of("Asia/Shanghai")).toLocalDateTime();
    }

    /**
     *Java8，在日期的格式化和解析方面不用考虑线程安全性，
     * @param time LocalDateTime
     * @param pattern 模式
     * @return
     */
    public static String formatTime(LocalDateTime time ,String pattern){
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 日期加运算
     * @param time LocalDateTime
     * @param number 减的数字
     * @param field  ChronoUnit枚举值 DAYS等 根据field不同加不同值
     * @return
     */
    public static LocalDateTime plus(LocalDateTime time, long number, TemporalUnit field) {
        return time.plus(number, field);
    }


    /**
     * 日期减运算
     * @param time LocalDateTime
     * @param number 减的数字
     * @param field  ChronoUnit枚举值 DAYS等 根据field不同减不同值
     * @return
     */
    public static LocalDateTime minu(LocalDateTime time, long number, TemporalUnit field){
        return time.minus(number,field);
    }

    /**
     * Date转换为LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime convertDateToLDT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }


    /**
     * LocalDateTime转换为Date
     * @param time
     * @return
     */
    public static Date convertLDTToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获得当天零点00:00:00
     * @return
     */
    public static LocalDateTime today_start(){
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    }

    /**
     * 获得当天结束59:59:59
     * @return
     */
    public static LocalDateTime today_end(){
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    }


    /**
     * 获取当前时间并格式化
     * @return
     */
    public static String getCurrentTimeformat(){
       return DateUtils.formatTime(DateUtils.getLocalDateTime() , GlobalConstant.DATE_FORMAT);
    }

    /**
     * 设定时间
     *
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date bookTime(int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        Date date = calendar.getTime();
        return date;
    }
    public static void main(String[] args) {
//today_start()
        String pattern = "yyyy-MM-dd HH:mm:ss";
        LocalDateTime minu = plus(today_start(), 1, DAYS);
        String s = formatTime(minu, pattern);
        System.out.println(s);

    }

}
