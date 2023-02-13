package com.capstone.webserver.util;

import java.util.*;

public class DateUtil {
    public static ArrayList<Map<Integer, String>> computeDate(int year, String semester, String time){
        int month = (Objects.equals(semester, "1학기") ? Calendar.MARCH : Calendar.SEPTEMBER);
        int day = (Objects.equals(semester, "1학기") ? 2 : 1);
        Calendar startDay = new GregorianCalendar(year, month, day);


        return null;
    }
}
