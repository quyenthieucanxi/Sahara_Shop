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


public class SettingsActivity extends AppCompatActivity {
    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //binding.menuEmailVerified.setOnClickListener(this::setCheckAccountVerified);
        binding.menuLogin.setVisibility(View.GONE);
        binding.menuForgotPassword.setVisibility(View.GONE);
        binding.btnBack.setOnClickListener(view -> finish());
        binding.menuUpdateAccount.setOnClickListener(view -> updateAccountInfo());
        binding.menuLogout.setOnClickListener(this::setLogoutClick);
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