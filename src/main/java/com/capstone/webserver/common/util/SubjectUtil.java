package com.capstone.webserver.common.util;

import com.capstone.webserver.dto.SubjectDTO;
import com.capstone.webserver.entity.attendance.Attendance;
import com.capstone.webserver.entity.subject.Subject;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Slf4j
public class SubjectUtil {
    public static Map<String, ArrayList<String>> splitSubjectTime(String time) {
        /*
         * 시간표 자르는 로직
         * 예시
         * [SP115:월(0A-0)(1-2A)]
         * [SP503:수(8B-9), 금(7-8A)]
         * [SP116:월(6)] [SP403:수(3)]
         * [SP116:월(6), 금(7-8A)] [SP403:수(3)]
         */

        /*
         * 1단계: 불필요한 공백 제거
         * -> "[SP115:월(0A-0)(1-2A)]"
         * -> "[SP503:수(8B-9),금(7-8A)]"
         * -> "[SP116:월(6)][SP403:수(3)]"
         * -> "[SP116:월(6),금(7-8A)][SP403:수(3)]"
         */
        time = time.replace(" ", "");

        /*
         * 2단계: 대괄호([]) 기준
         * -> ["SP115:월(0A-0)(1-2A)"]
         * -> ["SP503:수(8B-9),금(7-8A)"]
         * -> ["SP116:월(6)", "SP403:수(3)"]
         * -> ["SP116:월(6),금(7-8A)", "SP403:수(3)"]
         */
        boolean isOpen = false;
        String temp = "";
        ArrayList<String> timeList = new ArrayList<String>();
        for (int i = 0; i < time.length(); i++) {
            if (time.charAt(i) == ']') {
                timeList.add(temp);
                temp = "";
            } else if (time.charAt(i) != '[')
                temp += time.charAt(i);
        }

        /*
         * 3단계: 콤마(,) 기준으로 구분
         * -> [["SP115:월(0A-0)(1-2A)"]]
         * -> [["SP503:수(8B-9)"], ["금(7-8A)"]]
         * -> [["SP116:월(6)", "SP403:수(3)"]]
         * -> [["SP116:월(6)", "금(7-8A)"], ["SP403:수(3)"]]
         */

        ArrayList<ArrayList<String>> time2dList = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < timeList.size(); i++) {
            time2dList.add(new ArrayList<String>());

            String[] strList = timeList.get(i).split(",");
            for (String str : strList)
                time2dList.get(i).add(str);
        }

        /*
         * 4단계: 콜론(:) 기준으로 구분해서 강의실 정보 삭제 후 일차원 리스트에 삽입
         * -> ["월(0A-0)(1-2A)"]
         * -> ["수(8B-9)", "금(7-8A)"]
         * -> ["월(6)", "수(3)"]
         * -> ["월(6)", "금(7-8A)", "수(3)"]
         */

        timeList.clear();
        for (ArrayList<String> tempArray : time2dList) {
            for (String s : tempArray) {
                int index = s.indexOf(":");
                s = s.substring(index + 1);
                timeList.add(s);
            }
        }

        /*
         * 5단계: MAP을 사용해서 요일, 시간표로 나누기
         * -> {"월": ["0A-0", "1-2A"]}
         * -> {"수": ["8B-9"], "금": ["7-8A"]}
         * -> {"월": ["6"], "수": ["3"]}
         * -> {"월": ["6"], "금": ["7-8A"], "수": ["3"]}
         */
        Map<String, ArrayList<String>> timeMap = new HashMap<String, ArrayList<String>>();
        for (String str : timeList) {
            String day = String.valueOf(str.charAt(0));
            if (!timeMap.containsKey(day))
                timeMap.put(day, new ArrayList<String>());

            time = str.substring(1);
            time = time.replace("(", "");

            temp = "";

            for (int i = 0; i < time.length(); i++) {

                if (time.charAt(i) == ')') {
                    timeMap.get(day).add(temp);
                    temp = "";
                } else
                    temp += (time.charAt(i) == '-' ? "~" : time.charAt(i));
            }
        }

        return timeMap;
    }


    public static ArrayList<SubjectDTO.TodaySubjectForm> createTodaySubjectList(ArrayList<Attendance> attendanceArrayList, ArrayList<Subject> subjectArrayList) {
        ArrayList<SubjectDTO.TodaySubjectForm> todaySubjectForms = new ArrayList<SubjectDTO.TodaySubjectForm>();

        LocalDate now = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String date = now.format(formatter);

        HashSet<Long> set = new HashSet<Long>();

        for (Attendance attendance : attendanceArrayList) {
            if (attendance.getDateAttendance().contains(date)) {
                if (!set.contains(attendance.getIdSubject())) {
                    Subject subject = findById(subjectArrayList, attendance.getIdSubject());
                    if (subject != null) {
                        todaySubjectForms.add(SubjectDTO.TodaySubjectForm
                                .builder()
                                .idSubject(attendance.getIdSubject())
                                .nameSubject(subject.getNameSubject())
                                .timeSubject(attendance.getDateAttendance().split("-")[4])
                                .weekSubject(attendance.getWeekAttendance())
                                .profSubject(subject.getProfSubject())
                                .locationSubject(splitLocation(subject.getTimeSubject(), attendance.getDateAttendance().split("-")[3]))
                                .build());
                        set.add(attendance.getIdSubject());
                    }
                }
            }
        }

        return todaySubjectForms;
    }

    private static Subject findById(ArrayList<Subject> subjectArrayList, Long idSubject) {
        for (Subject subject : subjectArrayList)
            if (Objects.equals(subject.getId(), idSubject))
                return subject;

        return null;
    }

    private static String splitLocation(String time, String day) {
        time = time.replace(" ", "");


        boolean isOpen = false;
        String temp = "";
        ArrayList<String> timeList = new ArrayList<String>();
        for (int i = 0; i < time.length(); i++) {
            if (time.charAt(i) == ']') {
                timeList.add(temp);
                temp = "";
            } else if (time.charAt(i) != '[')
                temp += time.charAt(i);
        }

        for (String str : timeList) {
            if (str.contains(day)) {
                int idx = str.indexOf(':');
                return str.substring(0, idx);
            }
        }

        return null;
    }
}