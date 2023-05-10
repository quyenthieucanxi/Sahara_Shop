package com.example.saharashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.saharashop.R;
import com.example.saharashop.fragment.MessageBoxFragment;

public class FeatureChangeLanguageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_change_language);
        findViewById(R.id.btnBack_ChangeLanguage).setOnClickListener(v -> {
            finish();
        });
    }
    public void chooseLanguage(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MessageBoxFragment msg = new MessageBoxFragment();
        msg.show(transaction, "OKe");
    }
}