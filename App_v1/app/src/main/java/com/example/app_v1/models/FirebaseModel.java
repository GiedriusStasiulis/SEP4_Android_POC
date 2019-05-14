package com.example.app_v1.models;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseModel {
    private FirebaseAuth mFirebaseAuth;

    public FirebaseModel() {}

    public FirebaseAuth getMFirebaseAuth(){
        return mFirebaseAuth.getInstance();
    }
}
