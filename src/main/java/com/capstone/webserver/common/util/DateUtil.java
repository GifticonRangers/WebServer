package com.capstone.webserver.common.util;

import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
    public static Map<Integer, ArrayList<String>> computeDate(int year, String semester, String time) {
        int month = (Objects.equals(semester, "1학기") ? Calendar.MARCH : Calendar.SEPTEMBER);
        int day = (Objects.equals(semester, "1학기") ? 2 : 1);
        Calendar startDay = new GregorianCalendar(year, month, day);

        Map<String, ArrayList<String>> splitSubjectTime = SubjectUtil.splitSubjectTime(time);

        /*
         * 1단계: 요일 배열 만들기
         * 대충 월 ~ 일까지
         */
        ArrayList<String> week = new ArrayList<String>(Arrays.asList("일", "월", "화", "수", "목", "금", "토"));

        /*
         * 2단계: startDay에서 요일 받아와서 요일 배열 정렬하기
         * 함수로 만들면 이쁠듯 1단계랑 같이 ㅋㅋ
         */
        int dayOfWeek = startDay.get(Calendar.DAY_OF_WEEK);

        for (int i = 0; i < dayOfWeek - 1; i++) {
            String temp = week.get(0);
            week.remove(0);
            week.add(temp);
        }

        /*
         * 3단계: for문 돌면서 attendance에 시간 추가하기
         */
        Map<Integer, ArrayList<String>> attendance = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 1; i <= 16; i++) {
            attendance.put(i, new ArrayList<String>());

            for (String w : week) {
                if (splitSubjectTime.containsKey(w)) {
                    int j = 1;
                    for (String t : splitSubjectTime.get(w)) {
                        attendance.get(i).add(sdf.format(
                                startDay.getTime()) +
                                "-" + w + "-(" + t + ")-" +
                                i + "주차-" + j + "차시");
                        j++;
                    }
                }
                startDay.add(Calendar.DATE, 1);
            }

        }

        return attendance;
    }

    public static String changeSubjectTime(String time) {
        String[] times = time.split("-");
        return times[0]+"-"+times[1]+"-"+times[2]+"-"+getStartTime(times[4]);
    }

    public static String getStartTime(String time) {
        switch (time) {
            case "(0A~0)":
                return "07:30";
            case "(0)":
                return "08:00";
            case "(1~2A)":
            case "(1)":
                return "09:00";
            case "(2)":
                return "10:00";
            case "(2B~3)":
                return "10:30";
            case "(3)":
                return "11:00";
            case "(4~5A)":
            case "(4)":
                return "12:00";
            case "(5)":
                return "13:00";
            case "(5B~6)":
                return "13:30";
            case "(6)":
                return "14:00";
            case "(7~8A)":
            case "(7)":
                return "15:00";
            case "(8)":
                return "16:00";
            case "(8B~9)":
                return "16:30";
            case "(9)":
                return "17:00";
            case "(야1~2A)":
            case "(야1)":
                return "18:00";
            case "(야2)":
                return "18:55";
            case "(야2B~3)":
                return "19:25";
            case "(야3)":
                return "19:50 ";
            case "(야4)":
                return "20:45";
            case "(야4~5A)":
                return "20:50";
            case "(야5)":
                return "21:40";
        }
        return null;
    }

    public static String getEndTime(String time) {
        switch (time) {
            case "(0A~0)":
                return "08:45";
            case "(0)":
                return "08:50";
            case "(1)":
                return "09:50";
            case "(1~2A)":
                return "10:15";
            case "(2)":
                return "10:50";
            case "(2B~3)":
                return "11:45";
            case "(3)":
                return "11:50";
            case "(4)":
                return "12:50";
            case "(4~5A)":
                return "13:15";
            case "(5)":
                return "13:50";
            case "(5B~6)":
                return "14:45";
            case "(6)":
                return "14:50";
            case "(7)":
                return "15:50";
            case "(7~8A)":
                return "16:15";
            case "(8)":
                return "16:50";
            case "(8B~9)":
                return "17:45";
            case "(9)":
                return "17:50";
            case "(야1)":
                return "18:50";
            case "(야1~2A)":
                return "19:15";
            case "(야2)":
                return "19:45";
            case "(야4)":
                return "21:35";
            case "(야2B~3)":
                return "20:40";
            case "(야3)":
                return "20:40";
            case "(야4~5A)":
                return "22:05";
            case "(야5)":
                return "22:30";
        }
        return null;
    }
}
