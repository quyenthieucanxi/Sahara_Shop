package com.example.saharashop.untils;


import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
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
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AppUtilities {
    public static final int PERMISSION_REQUEST_CODE = 100;
    public static final int SELECT_PICTURE = 200;
    public static final int TAKE_PICTURE = 300;
    public static final String MY_IMAGES = "images";
    public static String currentPhotoPath;

    private static final String FROM_EMAIL = "luongthangg268@gmail.com";
    private static final String PASSWORD = "kkxsulmmwodlanqt";
    private static final String TAG = "SendMailTask";
    public static final int REQUEST_CODE_OPEN_DOCUMENT = 1;
    public static void setChoosePhoto(View view) {
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        String[] mimeTypes = {"image/*", "application/pdf"};
        i.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        Activity activity = (Activity) view.getContext();
        activity.startActivityForResult(i, REQUEST_CODE_OPEN_DOCUMENT);
    }

    public static class SendMailTask extends AsyncTask<Void, Void, Void> {
        private String mRecipientEmail;
        private String mSubject;
        private String mBody;

        public SendMailTask(String recipientEmail, String subject, String body) {
            mRecipientEmail = recipientEmail;
            mSubject = subject;
            mBody = body;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Thiết lập các thuộc tính của mail server
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");

                // Thiết lập session để gửi mail
                Session session = Session.getInstance(props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
                    }
                });

                // Tạo một email mới và thiết lập thông tin
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(FROM_EMAIL));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mRecipientEmail));
                message.setSubject(mSubject);
                message.setText(mBody);

                // Gửi email
                Transport.send(message);

                Log.i(TAG, "Email sent successfully!");
            } catch (AddressException e) {
                Log.e(TAG, "AddressException: " + e.getMessage());
            } catch (MessagingException e) {
                Log.e(TAG, "MessagingException: " + e.getMessage());
            }
            return null;
        }
    }

}
