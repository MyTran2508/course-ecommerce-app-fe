package com.programming.userservice.utilities;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static String getTimeExpTime5Minutes() {
        ZonedDateTime expTime = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).plusMinutes(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return expTime.format(formatter);
    }
}
