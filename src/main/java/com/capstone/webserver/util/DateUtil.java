package com.capstone.webserver.util;

import java.util.*;

public class DateUtil {
    public static Map<Integer, ArrayList<String>> computeDate(int year, String semester, String time){
        int month = (Objects.equals(semester, "1학기") ? Calendar.MARCH : Calendar.SEPTEMBER);
        int day = (Objects.equals(semester, "1학기") ? 2 : 1);
        Calendar startDay = new GregorianCalendar(year, month, day);

        Map<String, ArrayList<String>> splitSubjectTime = SubjectTimeUtil.splitSubjectTime(time);

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

        for (int i = 0; i < dayOfWeek-1; i++) {
            String temp = week.get(0);
            week.remove(0);
            week.add(temp);
        }

        /*
         * 3단계: for문 돌면서 attendance에 시간 추가하기
         */
        Map<Integer, ArrayList<String>> attendance = new HashMap<>();

        for (int i = 1; i <= 16; i++) {
            attendance.put(i, new ArrayList<String>());

            for (String w : week) {
                if(splitSubjectTime.containsKey(w))
                    for (String t : splitSubjectTime.get(w))
                        attendance.get(i).add(w+t);
            }
        }

        return attendance;
    }
}
