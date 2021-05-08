package com.example.madguidesapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.madguidesapp.DraweActivity;
import com.example.madguidesapp.R;

public class SplashThemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(SplashThemeActivity.this, DraweActivity.class));
        finish();
    }
}