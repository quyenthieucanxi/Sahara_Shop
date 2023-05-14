package com.example.saharashop.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.example.saharashop.untils.AccountSessionManager.getAuth;
import static com.example.saharashop.untils.AccountSessionManager.getCurrentUser;
import static com.example.saharashop.untils.AccountSessionManager.mAuth;
import static com.example.saharashop.untils.AccountSessionManager.currentUser;
public class FirebaseActivity extends Activity {
    public static final String TAG = "EmailPassword";
    public static final String EMAIL = "Email";
    public static final String PASSWORD = "Password";
    public static final String SIGN_IN_ACTION = "Sign in";
    public static final String CREATE_ACCOUNT_ACTION = "Create account";
    public static final String VERIFY_ACTION = "Verify";
    public static final String CHANGE_EMAIL_ACTION = "Change email";
    public static final String CHANGE_PASSWORD_ACTION = "Change password";
    public static final String FORGOT_PASSWORD_ACTION = "Forgot password";
    public static final int SIGN_IN = 1;
    public static final int CREATE_ACCOUNT = 2;
    public static final int VERIFY = 3;
    public static final int CHANGE_EMAIL = 4;
    public static final int CHANGE_PASSWORD = 5;
    public static final int FORGOT_PASSWORD = 6;
    public static final int SIGN_IN_OK = 100;
    public static final int CREATE_ACCOUNT_OK = 200;
    public static final int VERIFY_OK = 300;
    public static final int CHANGE_EMAIL_OK = 400;
    public static final int CHANGE_PASSWORD_OK = 500;
    public static final int FORGOT_PASSWORD_OK = 600;
    private String email;
    private String password; //encoded

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        Intent intent = getIntent();
        this.email = intent.getStringExtra(EMAIL);
        this.password = intent.getStringExtra(PASSWORD);
        action(Objects.requireNonNull(intent.getAction()));
    }

    private void action(@NotNull String action) {
        if (action.equals(SIGN_IN_ACTION)) {
            signIn();
        } else if (action.equals(CREATE_ACCOUNT_ACTION)) {
            createAccount();
        } else if (action.equals(VERIFY_ACTION)) {
            sendEmailVerification();
        } else if (action.equals(CHANGE_PASSWORD_ACTION)) {
            updatePassword(this.password);
        } else if (action.equals(FORGOT_PASSWORD_ACTION)) {
            forgotPassword(this.email);
        } else if (action.equals(CHANGE_EMAIL_ACTION)) {
            updateEmail();
        }
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
    }
    // [END on_start_check_user]

    private void createAccount() {
        // [START create_user_with_email]
        mAuth = getAuth();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        currentUser = mAuth.getCurrentUser();
                   //   updateAccountSession();
                        setResult(CREATE_ACCOUNT_OK);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(FirebaseActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        setResult(-1);
                        finish();
                    }
                });
        // [END create_user_with_email]
    }

    private void signIn() {
        // [START sign_in_with_email]
        // password has been encoded
        mAuth = getAuth();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                       //pdateAccountSession();
                        setResult(SIGN_IN_OK);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(FirebaseActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        setResult(-1);
                        finish();
                    }
                });
        // [END sign_in_with_email]
    }

    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        currentUser = getCurrentUser();
        currentUser.sendEmailVerification()
                .addOnCompleteListener(this, task -> {
                    Log.i(EMAIL, "Email sent.");
                    Toast.makeText(this, "Vui lòng kiểm tra email để xác thực tài khoản.", Toast.LENGTH_SHORT).show();
                    setResult(VERIFY_OK);
                    finish();
                });
        // [END send_email_verification]
    }

    private void updatePassword(String newPassword) {
        currentUser = getCurrentUser();
        currentUser.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                            setResult(CHANGE_PASSWORD_OK);
                            finish();
                        }
                    }
                });
    }

    private void forgotPassword(String email) {
        FirebaseAuth auth = getAuth();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            setResult(FORGOT_PASSWORD_OK);
                            finish();
                        }
                    }
                });
    }
/*
    private void updateAccountSession() {
        currentUser = getCurrentUser();
        AccountDbHelper accountDbHelper = new AccountDbHelper(this);
        account = accountDbHelper.getAccountByEmail(email);
        if (account != null) {
            UserDbHelper userDbHelper = new UserDbHelper(this);
            user = userDbHelper.getUserByAccountId(account.getId());
            if (user == null) {
                account = null;
                Toast.makeText(this, "Đã có lỗi phát sinh trong quá trình đăng nhập.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Đăng nhập thất bại. Vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
        }
    }*/

    private void updateEmail() {
        currentUser = getCurrentUser();
        currentUser.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                            setResult(CHANGE_EMAIL_OK);
                            finish();
                        }
                    }
                });
    }
}