package com.example.saharashop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.saharashop.R;
import com.example.saharashop.api.APIService;
import com.example.saharashop.api.IUserService;
import com.example.saharashop.databinding.ForgotPasswordBinding;
import com.example.saharashop.entity.Account1;
import com.example.saharashop.entity.User;
import com.example.saharashop.untils.AppUtilities;
import com.example.saharashop.untils.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ForgotPasswordBinding binding;
    private int otpCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.otp.setVisibility(View.GONE);
        binding.btnGetPassword.setVisibility(View.GONE);
        binding.btnBack.setOnClickListener(view -> onBackPressed());
        binding.btnGetPassword.setOnClickListener(view -> setGetPassword());
        binding.btnGetOTP.setOnClickListener(view -> setGetOTP());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setGetOTP() {
        String email = binding.email.getText().toString().trim();
        if (!email.contains("@")) {
            Toast.makeText(this, "Vui lòng nhập chính xác email.", Toast.LENGTH_SHORT).show();
            return;
        }
        otpCode = makeOTP();
        AppUtilities.SendMailTask sendEmailTask = new AppUtilities.SendMailTask(email, "MÃ XÁC NHẬN OTP", "Mã otp của bạn là: " + otpCode);
        sendEmailTask.execute();
        binding.otp.setVisibility(View.VISIBLE);
        binding.btnGetOTP.setVisibility(View.GONE);
        binding.btnGetPassword.setVisibility(View.VISIBLE);
    }

    private boolean validate(@NotNull String otp) {
        if (otp.equals("")) return false;
        return true;
    }

    private int makeOTP(){
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;
        return randomNumber;
    }

    private void getPassword(String email){
        APIService.createService(IUserService.class).getAccountByEmail(email).enqueue(new Callback<Account1>() {
            @Override
            public void onResponse(Call<Account1> call, Response<Account1> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Lỗi hệ thống ", Toast.LENGTH_SHORT).show();
                    return;
                }
                binding.txtLabel.setText("Mật khẩu của bạn là: " + response.body().getPassword());
                binding.txtLabel.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<Account1> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setGetPassword() {
        String email = binding.email.getText().toString().trim();
        String valueOtp = binding.otp.getText().toString().trim();
        if (!validate(valueOtp)) {
            Toast.makeText(this, "Vui lòng nhập mã OTP!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Integer.parseInt(valueOtp) != otpCode){
            Toast.makeText(this, "Nhập sai mã OTP. Vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
        }
        else{
            getPassword(email);
        }
    }
}