package com.example.madguidesapp;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.madguidesapp.databinding.AppBarMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class DraweActivity extends BaseActivity {

    private static final String TAG = "DraweActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawe);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();















        firebaseAuth.signInWithEmailAndPassword("bemol54192@gmail.com", "B");
    }


}