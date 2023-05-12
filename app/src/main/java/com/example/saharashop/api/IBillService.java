package com.example.saharashop.api;

import com.example.saharashop.entity.Bill;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IBillService {
    @POST("bill/add")
    Call<Bill> add(@Body Bill bill);

    @GET("bill/getAllBillByUserId/{userId}")
    Call<List<Bill>> getAllBillByUserId(@Path("userId") String userId);
}
