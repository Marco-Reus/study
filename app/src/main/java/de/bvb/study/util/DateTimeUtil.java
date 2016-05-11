package de.bvb.study.util;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateTimeUtil {

    public final class FormatType {
        public static final String FULL = "yyyy:MM:dd HH:mm:ss:SSS";
        public static final String DATE = "yyyy:MM:dd";
        public static final String TIME = "HH:mm:ss:SSS";
    }

    public static String formatDateTime(Long date, String format) {
        return new SimpleDateFormat(format).format(new Date(date));
    }

    public static String formatCurrentTime(String format) {
        return formatDateTime(System.currentTimeMillis(), format);
    }

}
