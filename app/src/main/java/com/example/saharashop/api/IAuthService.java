package com.example.saharashop.api;

import com.example.saharashop.entity.Account;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IAuthService {
    @POST("/appfoods/registrationapi.php?apicall=login")
    @FormUrlEncoded
    Call<Account> login(@Field("email") String username,
                        @Field("password") String password);

}
