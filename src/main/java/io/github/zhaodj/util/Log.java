package io.github.zhaodj.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {

    public static final DateTimeFormatter LOG_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static void print() {
        print("");
    }

    public static void print(String msg) {
        StackTraceElement element = new Throwable().getStackTrace()[1];
        String time = LocalDateTime.now().format(LOG_DATE_TIME_FORMATTER);
        System.out.println(time + " " + element.getClassName() + "#" + element.getMethodName() + " " + msg);
    }

}
