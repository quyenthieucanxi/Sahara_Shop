package com.example.saharashop.untils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountSessionManager {
    public static FirebaseUser currentUser;
    public static FirebaseAuth mAuth;
    public static FirebaseUser getCurrentUser() {
        if (currentUser == null)
            currentUser = mAuth.getCurrentUser();
        return currentUser;
    }

    public static FirebaseAuth getAuth() {
        if (mAuth == null)
            mAuth = FirebaseAuth.getInstance();
        return mAuth;
    }
    public static boolean isEmailVerified() {
        if (mAuth != null && currentUser != null) {
            return currentUser.isEmailVerified();
        }
        return false;
    }
}
