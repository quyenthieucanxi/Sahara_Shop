package com.example.saharashop.api;

import com.example.saharashop.entity.Bill;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IBillService {
    @POST("bill/add")
    Call<Bill> add(@Body Bill bill);

    @GET("bill/getAllBillByUserId/{userId}")
    Call<List<Bill>> getAllBillByUserId(@Path("userId") String userId);

    @GET("bill/getAllBill/")
    Call<List<Bill>> getAllBill(@Query("status") String status);

    @PUT("bill/update/{idBill}")
    Call<Bill> update(@Path("idBill") String idBill, @Body Map<String, String> status);
}
