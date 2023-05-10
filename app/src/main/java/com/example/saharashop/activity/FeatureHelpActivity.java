package com.example.saharashop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.saharashop.R;

public class FeatureHelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature_help);
        findViewById(R.id.btnBack_help).setOnClickListener(view -> finish());
        WebView webView = findViewById(R.id.webHelp);
        String url = getResources().getString(R.string.help_url);
        webView.loadUrl(url);
    }
}