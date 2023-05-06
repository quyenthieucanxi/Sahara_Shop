package com.example.saharashop.api;

import com.example.saharashop.entity.Account1;
import com.example.saharashop.entity.Test;
import com.example.saharashop.entity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IAuthService {


    @POST("account/login")
    Call<Account1> login(@Body Test test);
    /*@FormUrlEncoded
    Call<Resp> login(@Field("username") String username,
                     @Field("password") String password);*/
    @POST("account/sign-up")
    Call<Account1> signUp(@Body Account1 account);
    @POST("user/addUser")
    Call<User> addUser(@Body User user);

}
