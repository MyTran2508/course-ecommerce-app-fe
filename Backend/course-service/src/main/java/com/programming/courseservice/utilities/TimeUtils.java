package com.programming.courseservice.utilities;

public class TimeUtils {

    // Convert hours to milliseconds
    public static Long converHoursToMilliseconds(Long hours) {
        return hours * 60 * 60 * 1000;
    }

    // Convert milliseconds to days
    public static Integer convertMilisecondsToDays(Long miliseconds) {
        return (int) (miliseconds / (1000 * 60 * 60 * 24));
    }
}
