package com.example.saharashop.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.saharashop.api.APIService;
import com.example.saharashop.api.IAuthService;
import com.example.saharashop.api.IUserService;
import com.example.saharashop.databinding.LoginBinding;
import com.example.saharashop.entity.Account1;
import com.example.saharashop.entity.Test;
import com.example.saharashop.entity.User;
import com.example.saharashop.fragment.MenuFragment;
import com.example.saharashop.untils.AppUtilities;
import com.example.saharashop.untils.SharedPrefManager;

import javax.mail.MessagingException;

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
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        binding.btnLogIn.setOnClickListener(view -> Login());
        binding.txtSignUp.setOnClickListener(view -> Singup());
        binding.btnForgotPassword.setOnClickListener(view -> ForgotPassword());
    }

    private void ForgotPassword() {
        Intent intent = new Intent(Login.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void Singup() {
        Intent intent = new Intent(Login.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }

    private void addUserToSharedPref(Account1 account1){
        APIService.createService(IUserService.class).getUserByAccountId(account1.getId()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Sai tài khoản hoặc mật khẩu !", Toast.LENGTH_SHORT).show();
                }
                User userJson = response.body();
                User user = new User(
                        userJson.getId(),
                        userJson.getAccountId(),
                        userJson.getFullname(),
                        userJson.getSex(),
                        userJson.getPhone(),
                        userJson.getAddress(),
                        userJson.getAvatar(),
                        userJson.getState()
                );
                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user, account1);
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi hệ thống !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("NotConstructor")
    private void Login() {
        String email = binding.email.getText().toString().trim();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Nếu email không đúng định dạng, hiển thị thông báo và focus vào ô email
            binding.email.setError("Email không hợp lệ !");
            binding.email.requestFocus();
            return;
        }
        String password = binding.txtPassword.getText().toString().trim();
        Test user = new Test(email,password);
        APIService.createService(IAuthService.class).login(user).enqueue(new Callback<Account1>() {
            @Override
            public void onResponse(Call<Account1> call, Response<Account1> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Account1 account1 = response.body();
                        addUserToSharedPref(account1);
                        Toast.makeText(getApplicationContext(), "Đăng nhập thành công với " + account1.getUsername(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Sai tài khoản hoặc mật khẩu !", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Sai tài khoản hoặc mật khẩu !", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Account1> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi hệ thống !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
