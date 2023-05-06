package com.example.saharashop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.saharashop.R;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.IAuthService;
import com.example.saharashop.databinding.LoginBinding;
import com.example.saharashop.databinding.SignupBinding;
import com.example.saharashop.entity.Account1;
import com.example.saharashop.entity.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private SignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Context ctx = this;
        binding.btnSignUp.setOnClickListener(view -> handleSignUp());
        binding.txtSignIn.setOnClickListener(view -> setLogIn());

    }
    void setLogIn() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
    private void handleSignUp() {
        String valueFullName = binding.txtFullName.getText().toString();
        String valueUsername = binding.txtUsername.getText().toString();
        String valueAddress = binding.txtAddress.getText().toString();
        String valuePhone = binding.txtPhone.getText().toString();
        String valueEmail = binding.txtEmail.getText().toString();
        String valuePassword = binding.txtPassword.getText().toString();
        String valueConfirmPassword = binding.txtConfirmPassword.getText().toString();

        String valueChipMale = binding.chipMale.getText().toString();
        String valueChipFemale = binding.chipFemale.getText().toString();
        String valueChipOthers = binding.chipOthers.getText().toString();

        Account1 account = new Account1(valueUsername, valueEmail, valuePassword, "user", true);

        APIService.createService(IAuthService.class).signUp(account).enqueue(new Callback<Account1>() {
            String valueAccountId;
            @Override
            public void onResponse(Call<Account1> call, Response<Account1> response) {
                if (response.code() != 200) {
                    Toast.makeText(getApplicationContext(), "Đăng ký thất bại 1", Toast.LENGTH_SHORT).show();

                    return;
                }
                valueAccountId = response.body().getId();
                Toast.makeText(getApplicationContext(), "Tạo tài khoản thành công thành công: " + response.body().getId(), Toast.LENGTH_SHORT).show();

                User user = new User(valueAccountId, valueFullName, valueChipMale, valuePhone, valueAddress, valueConfirmPassword, true);

                APIService.createService(IAuthService.class).addUser(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.code() != 200) {
                            Toast.makeText(getApplicationContext(), "Đăng ký thất bại 2", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "Đăng ký thành công: " + response.body().getAccountId(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Đăng ký thất bại 22", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onFailure(Call<Account1> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Đăng ký thất bại 11", Toast.LENGTH_SHORT).show();
            }
        });
    }


}