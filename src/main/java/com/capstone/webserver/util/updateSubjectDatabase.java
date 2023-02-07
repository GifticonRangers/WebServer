package com.capstone.webserver.util;

import com.capstone.webserver.entity.Subject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

public class updateSubjectDatabase {
    public static void main(String[] args)
            throws FileNotFoundException
    {
        Reader reader = new FileReader("D:\\INU-LECTURE\\WebServer\\src\\main\\java\\com\\capstone\\webserver\\util\\subject.json");
        Gson gson = new Gson();
        ArrayList<Subject> subjects = gson.fromJson(reader, new TypeToken<ArrayList<Subject>>(){}.getType());
        for (Subject subject: subjects) {
            System.out.println(subject.toString());
        }
    }
}
