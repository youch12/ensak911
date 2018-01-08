package com.ensak911.teachers.service;

import com.ensak911.teachers.models.TeachersList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by hp on 1/8/2018.
 */
public interface TeacherService {
    @GET("teachers")
    Call<TeachersList> getTeachers();
}
