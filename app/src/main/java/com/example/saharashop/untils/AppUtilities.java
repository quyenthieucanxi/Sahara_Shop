package com.example.saharashop.untils;


import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AppUtilities {
    public static final int PERMISSION_REQUEST_CODE = 100;
    public static final int SELECT_PICTURE = 200;
    public static final int TAKE_PICTURE = 300;
    public static final String MY_IMAGES = "images";
    public static String currentPhotoPath;

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    public static void setChoosePhoto(View view) {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        Activity activity = (Activity) view.getContext();
        activity.startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

}
