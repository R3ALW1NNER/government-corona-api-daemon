package com.realwinner.governmentcoronaapidaemon.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {
    public final DateTimeFormatter formatterYYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");
    public final DateTimeFormatter formatterYYYYMMDDHHMMSSSSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    public final String dateTimeFormatYYYY = "yyyy";
    public final String dateTimeFormatMM = "MM";
    public final String dateTimeFormatDD = "dd";


    public String getTodayDateString() {
        LocalDateTime todayDateTime = LocalDateTime.now();

        return todayDateTime.format(formatterYYYYMMDD);
    }

    public String getTodayDateString(String format) {
        LocalDateTime todayDateTime = LocalDateTime.now();

        return todayDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    public String getYesterdayDateString() {
        LocalDateTime todayDateTime = LocalDateTime.now();
        LocalDateTime yesterdayDateTime = todayDateTime.minusDays(1);

        return yesterdayDateTime.format(formatterYYYYMMDD);
    }
}
