package com.example.saharashop.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.saharashop.api.APIService;
import com.example.saharashop.api.IAuthService;
import com.example.saharashop.databinding.LoginBinding;
import com.example.saharashop.entity.Account1;
import com.example.saharashop.entity.Test;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login  extends AppCompatActivity {
    private LoginBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Context ctx = this;
        /*if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }*/
        binding.btnLogIn.setOnClickListener(view -> Login());
        binding.txtSignUp.setOnClickListener(view -> Singup());



    }
    private void Singup() {
        Intent intent = new Intent(Login.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }
    private void setForgotPassword(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
        finish();
    }
    /*@SuppressLint("NotConstructor")
    private void Login() {
        String username = binding.email.getText().toString();
        String password = binding.txtPassword.getText().toString();
        APIService.createService(IAuthService.class).login(username, password).enqueue(new Callback<Resp>() {
            @Override
            public void onResponse(Call<Resp> call, Response<Resp> response) {

                if (response.body().getError().equals("false")) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(),
                            Toast.LENGTH_SHORT).show();
                    User userJson = response.body().getUser();
                    User user = new User(
                            userJson.getId(),
                            userJson.getUsername(),
                            userJson.getEmail(),
                            userJson.getGender(),
                            userJson.getImages(),
                            userJson.getFname()
                    );
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                    finish();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Resp> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });
    }*/
    @SuppressLint("NotConstructor")
    private void Login() {
        String username = binding.email.getText().toString().trim();
        String password = binding.txtPassword.getText().toString().trim();
        Test user = new Test(username,password);
        APIService.createService(IAuthService.class).login(user).enqueue(new Callback<Account1>() {
            @Override
            public void onResponse(Call<Account1> call, Response<Account1> response) {
                if (response.isSuccessful()) {
                    Account1 account1 = new Account1(response.body().getId(),response.body().getUsername(),response.body().getEmail(),response.body().getPassword()
                    ,response.body().getRoleID(),response.body().getState());

                    Toast.makeText(getApplicationContext(),account1.getUsername() , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Account1> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
