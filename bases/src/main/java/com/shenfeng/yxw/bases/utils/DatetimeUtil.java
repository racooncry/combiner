package com.shenfeng.yxw.bases.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Author: yangxiaowei37
 * @Date: 11/3/2021 下午7:49
 * @Version: 1.0
 * @Description:
 */
public class DatetimeUtil {

    private void date() {
        Date date = new Date(2019 - 1900, 11, 31, 11, 12, 13);
        System.out.println(date);
    }

    private void calendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, 11, 31, 11, 12, 13);
        System.out.println(calendar.getTime());
        Calendar calendar2 = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
        calendar2.set(2019, Calendar.DECEMBER, 31, 11, 12, 13);
        System.out.println(calendar2.getTime());
    }

    private void localDate() {

        // 一个时间表示
        String stringDate = "2020-01-02 22:00:00";
        // 初始化三个时区
        ZoneId timeZoneSH = ZoneId.of("Asia/Shanghai");
        ZoneId timeZoneNY = ZoneId.of("America/New_York");
        ZoneId timeZoneJST = ZoneOffset.ofHours(9);
        // 格式化器
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime date = ZonedDateTime.of(LocalDateTime.parse(stringDate, dateTimeFormatter), timeZoneJST);
        // 使用DateTimeFormatter格式化时间，可以通过withZone方法直接设置格式化使用的时区
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
        System.out.println(timeZoneSH.getId() + outputFormat.withZone(timeZoneSH).format(date));
        System.out.println(timeZoneNY.getId() + outputFormat.withZone(timeZoneNY).format(date));
        System.out.println(timeZoneJST.getId() + outputFormat.withZone(timeZoneJST).format(date));
    }


    public static void main(String[] args) {


    }
}
