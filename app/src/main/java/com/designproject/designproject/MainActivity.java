package com.designproject.designproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goSpalsh();

    }
//    intent splash screen
    public void goSpalsh(){
        Intent i = new Intent(this, SplashActivity.class);
        startActivity(i);
    }
}