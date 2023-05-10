package com.example.saharashop.untils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class AppUtilities {
    public static final int PERMISSION_REQUEST_CODE = 100;
    public static final int SELECT_PICTURE = 200;
    public static final int TAKE_PICTURE = 300;
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
