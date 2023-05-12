package com.example.saharashop.api;

import com.example.saharashop.entity.Store;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IStore {
    @GET("store/getStoreByProductId/{productId}")
    Call<Store> getStoreByProductId(@Path("productId") String productId);
}
