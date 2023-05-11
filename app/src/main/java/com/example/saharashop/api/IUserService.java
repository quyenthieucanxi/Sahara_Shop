package com.example.saharashop.api;

import com.example.saharashop.entity.Account1;
import com.example.saharashop.entity.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IUserService {
    @GET("user/getUserByAccountId/{accountId}")
    Call<User> getUserByAccountId(@Path("accountId") String accountId);

    @PUT("user/update/{idUser}")
    Call<User> updateUser(@Path("idUser") String idUser, @Body User user);

    @PUT("account/update/{idAccount}")
    Call<Account1> updateAccount(@Path("idAccount") String idAccount, @Body Account1 account1);
}
