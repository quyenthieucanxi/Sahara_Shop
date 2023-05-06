package com.example.saharashop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.saharashop.R;
import com.example.saharashop.databinding.ForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ForgotPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Context ctx = this;
        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnGetPassword.setOnClickListener(view -> setGetPassword());
    }
    private void setGetPassword() {
        String email = binding.email.getText().toString();
        if (!email.contains("@")) {
            Toast.makeText(this, "Vui lòng nhập chính xác email.", Toast.LENGTH_SHORT).show();
            return;
        }
//        Intent intent = new Intent(this, FirebaseActivity.class);
//        intent.putExtra(EMAIL, email);
//        intent.setAction(FORGOT_PASSWORD_ACTION);

    }
}