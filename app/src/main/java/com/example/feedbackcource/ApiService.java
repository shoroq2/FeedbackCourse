package com.example.feedbackcource;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.*;

public interface ApiService {
    @GET("feedback")
    Call<List<Course>> getFeedback();
    @POST("/add_feedback")
    Call<Void> addFeedback(@Query("courseName") String courseName, @Query("feedback") String feedback);
    @POST("/search_course")
    Call<List<Course>> searchCourse(@Body String query);
}