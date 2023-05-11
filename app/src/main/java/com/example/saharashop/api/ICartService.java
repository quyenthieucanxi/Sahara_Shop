package com.example.saharashop.api;

import com.example.saharashop.entity.Cart;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ICartService {
    @POST("cart/addCart")
    Call<Cart> addCart(@Body Cart cart);

    @GET("cart/getCartByUserId/{userId}")
    Call<List<Cart>> getCartByUserId(@Path("userId") String userId);

}