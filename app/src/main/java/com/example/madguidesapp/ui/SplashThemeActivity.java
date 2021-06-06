package com.example.madguidesapp.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.madguidesapp.DraweActivity;

public class SplashThemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(SplashThemeActivity.this, DraweActivity.class));
        finish();
    }
}