package com.ensak911.teachers.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hp on 1/8/2018.
 */
public class TeachersList {
    @SerializedName("teachers")
    private ArrayList<Prof> teachersList;

    public ArrayList<Prof> getTeachersList() {
        return teachersList;
    }

    public void setTeachersList(ArrayList<Prof> teachersList) {
        this.teachersList = teachersList;
    }
}
