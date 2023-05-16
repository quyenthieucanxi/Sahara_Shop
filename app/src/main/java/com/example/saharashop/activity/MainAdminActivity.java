package com.example.saharashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.saharashop.R;
import com.example.saharashop.entity.Account1;
import com.example.saharashop.fragment.CartFragment;
import com.example.saharashop.fragment.ConfirmOrderFragment;
import com.example.saharashop.fragment.DiscountFragment;
import com.example.saharashop.fragment.HomeFragment;
import com.example.saharashop.fragment.MenuFragment;
import com.example.saharashop.fragment.NotificationFragment;
import com.example.saharashop.untils.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainAdminActivity extends AppCompatActivity {
    public static Resources mainResources;
    public static final int FRAME_CONTAINER_ID_ADMIN = R.id.frame_container_admin;
    public static String SELECTED = "menuOrder_admin";
    @SuppressLint("NonConstantResourceId")
    private final BottomNavigationView.OnNavigationItemSelectedListener
            onNavItemSelectedListener = item -> {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.menuOrder_admin:
                SELECTED = "menuOrder_admin";
                fragment = new ConfirmOrderFragment();
                loadFragment(fragment);
                return true;
            case R.id.menuTransport_admin:
                SELECTED = "menuTransport_admin";
                fragment = new ConfirmOrderFragment();
                loadFragment(fragment);
                return true;
            case R.id.menuDelivery_admin:
                SELECTED = "menuDelivery_admin";
                fragment = new ConfirmOrderFragment();
                loadFragment(fragment);
                return true;
            case R.id.menuReceived_admin:
                SELECTED = "menuReceived_admin";
                fragment = new ConfirmOrderFragment();
                loadFragment(fragment);
                return true;
            case R.id.menuAvatar_admin:
                SELECTED = "menuAvatar_admin";
                fragment = new MenuFragment();
                loadFragment(fragment);
                return true;
            default:
                break;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        BottomNavigationView navigationView = this.findViewById(R.id.bottom_navbar_admin);
        navigationView.setOnNavigationItemSelectedListener(onNavItemSelectedListener);
        if (savedInstanceState == null) {
            navigationView.setSelectedItemId(R.id.menuOrder_admin);
        }
        mainResources = getResources();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.bottom_navbar_admin, menu);
        return true;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_admin, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}