package com.example.saharashop.api;

import com.example.saharashop.entity.Product;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IDiscount {
    @GET("product/getDiscountProductByName")
    Call<List<Product>> getDiscountProductByName(@Query("name") String TxtSearch);
}
