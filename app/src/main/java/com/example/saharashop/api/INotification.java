package com.example.saharashop.api;

import com.example.saharashop.entity.Notification;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface INotification {
    @GET("notification/getAllNotificationsByUserId/{userId}")
    Call<List<Notification>> getAllNotificationsByUserId(@Path("userId") String userId);

    @POST("notification/add")
    Call<Notification> add(@Body Notification notification);

    @PUT("notification/update/{id}")
    Call<Notification> update(@Path("id") String id);
}
