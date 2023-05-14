package com.example.saharashop.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.saharashop.R;
import com.example.saharashop.databinding.ActivitySettingsBinding;
import com.example.saharashop.untils.SharedPrefManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {
    private ActivitySettingsBinding binding;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.menuEmailVerified.setOnClickListener(this::setCheckAccountVerified);
        binding.menuLogin.setVisibility(View.GONE);
        binding.menuForgotPassword.setVisibility(View.GONE);
        binding.btnBack.setOnClickListener(view -> finish());
        binding.menuUpdateAccount.setOnClickListener(view -> updateAccountInfo());
        binding.menuLogout.setOnClickListener(this::setLogoutClick);
    }
    private void setCheckAccountVerified(View view) {
        checkVerify();
    }

    private void checkVerify() {
        currentUser = mAuth.getCurrentUser();
        boolean isVerified = currentUser.isEmailVerified();
        if (!isVerified) {
            binding.menuEmailVerified.setVisibility(View.VISIBLE);
            androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Tài khoản của bạn chưa được xác minh. Vùi lòng truy cập email đã được dùng đăng ký tài khoản để xác nhận.")
                    .setIcon(R.drawable.warning_32px)
                    .setPositiveButton("OK", (dialogInterface, i) -> {
//                        Intent intent = new Intent(this, FirebaseActivity.class);
//                        intent.putExtra(FirebaseActivity.EMAIL, currentUser.getEmail());
//                        intent.setAction(FirebaseActivity.VERIFY_ACTION);
//                        this.startActivityForResult(intent, FirebaseActivity.VERIFY);
                    })
                    .show();

        } else {
            binding.menuEmailVerified.setVisibility(View.GONE);
        }
    }
    private void sendEmailVerification(String EMAIL) {
        // Send verification email
        // [START send_email_verification]
        currentUser = mAuth.getCurrentUser();
        currentUser.sendEmailVerification()
                .addOnCompleteListener(this, task -> {
                    Log.i(EMAIL, "Email sent.");
                    Toast.makeText(this, "Vui lòng kiểm tra email để xác thực tài khoản.", Toast.LENGTH_SHORT).show();
                    finish();
                });
        // [END send_email_verification]
    }

    private void updateAccountInfo() {
        Intent intent = new Intent(this, AccountInfoActivity.class);
        startActivity(intent);
    }
    private void setLogoutClick(View view) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Đăng xuất")
                .setMessage("Bạn có muốn đăng xuất khỏi ứng dụng?")
                .setPositiveButton("Có", (dialogInterface, i) -> {
                    //AppUtilities.clearSession(view.getContext());
                    SharedPrefManager.getInstance(getApplicationContext()).logout();
                    //setLoginVisible();
                    Toast.makeText(this, "Đã đăng xuất thành công", Toast.LENGTH_SHORT).show();
                    //updateUiIfLoggedIn();
                })
                .setNegativeButton("Không", null)
                .setIcon(R.drawable.shutdown)
                .show();
    }

}