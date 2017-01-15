package de.bvb.study.util;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateTimeUtil extends BaseUtil {

    public static final int SECOND = 1000;
    public static final int MINUTE = 60 * SECOND;
    public static final int HOUR = 60 * MINUTE;
    public static final int DAY = 24 * HOUR;


    public static final class FormatType {
        //        public static final String FULL = "yyyy:MM:dd HH:mm:ss:SSS";
        public static final String FULL = "yyyy:MM:dd_HH:mm:ss";
        public static final String DATE = "yyyy:MM:dd";
        public static final String TIME = "HH:mm:ss:SSS";
    }

    public static String formatDateTime(Long date, String format) {
        return new SimpleDateFormat(format).format(new Date(date));
    }

    public static String formatCurrentTime(String format) {
        return formatDateTime(System.currentTimeMillis(), format);
    }

    public static String formatCurrentTime() {
        return formatDateTime(System.currentTimeMillis(), FormatType.FULL);
    }

}
