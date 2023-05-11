package com.example.saharashop.untils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.saharashop.activity.Login;
import com.example.saharashop.entity.Account1;
import com.example.saharashop.entity.User;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "retrofitregisterlogin";
    private static final String KEY_IDUSER = "keyiduser";
    private static final String KEY_ACCOUNTID = "keyaccountid";
    private static final String KEY_FULLNAME = "keyfullname";
    private static final String KEY_SEX = "keysex";
    private static final String KEY_PHONE = "keyphone";
    private static final String KEY_ADDRESS = "keyaddress";
    private static final String KEY_AVATAR = "keyavatar";
    private static final String KEY_STATEUSER = "keystate";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_PASSWORD = "keypassword";
    private static final String KEY_ROLEID = "keyroleid";
    private static final String KEY_STATEACCOUNT = "keystateaccount";

    private static SharedPrefManager instance;
    private static Context ctx;

    public SharedPrefManager(Context context) {
        ctx = context;
    }
    public static synchronized  SharedPrefManager getInstance(Context context){
        if(instance == null){
            instance = new SharedPrefManager(context);
        }
        return instance;
    }
    //this method will store the user data in shared preferences
    public void userLogin (User user, Account1 account1) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_IDUSER, user.getId());
        editor.putString(KEY_ACCOUNTID, user.getAccountId());
        editor.putString(KEY_FULLNAME, user.getFullname());
        editor.putString(KEY_SEX, user.getSex());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_ADDRESS, user.getAddress());
        editor.putString(KEY_AVATAR, user.getAvatar());
        editor.putBoolean(KEY_STATEUSER, user.getState());
        editor.putString(KEY_USERNAME, account1.getUsername());
        editor.putString(KEY_EMAIL, account1.getEmail());
        editor.putString(KEY_PASSWORD, account1.getPassword());
        editor.putString(KEY_ROLEID, account1.getRoleID());
        editor.putBoolean(KEY_STATEACCOUNT, account1.getState());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences (SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME,null) != null;
    }
    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences (SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_IDUSER, null),
                sharedPreferences.getString(KEY_ACCOUNTID, null),
                sharedPreferences.getString(KEY_FULLNAME, null),
                sharedPreferences.getString(KEY_SEX, null),
                sharedPreferences.getString(KEY_PHONE, null),
                sharedPreferences.getString(KEY_ADDRESS, null),
                sharedPreferences.getString(KEY_AVATAR, null),
                sharedPreferences.getBoolean(KEY_STATEUSER, true)
        );
    }
    public Account1 getAccount(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences (SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Account1(
                sharedPreferences.getString(KEY_ACCOUNTID, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PASSWORD, null),
                sharedPreferences.getString(KEY_ROLEID, null),
                sharedPreferences.getBoolean(KEY_STATEACCOUNT, true)
        );
    }
    public String getIdUser(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences (SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_IDUSER, null);
    }
    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(ctx, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }
}
