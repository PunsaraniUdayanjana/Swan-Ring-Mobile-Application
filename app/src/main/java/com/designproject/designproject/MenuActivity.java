package com.designproject.designproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

//  declare variables
    CardView homeBtn, profileBtn, settingBtn, logoutBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

//      Initialize the CardView buttons
        homeBtn = findViewById(R.id.homebtn);
        profileBtn =findViewById(R.id.profilebtn);
        logoutBtn =findViewById(R.id.logoutbtn);
        settingBtn = findViewById(R.id.settingbtn);

//      Set click listeners for the CardView buttons
        homeBtn.setOnClickListener(this);
        profileBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        settingBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//      Handle the click for the buttons
        switch (view.getId()) {
            case R.id.homebtn:
                Intent home = new Intent(this, HomeActivity.class);
                startActivity(home);
                break;

            case R.id.profilebtn:
                Intent profile = new Intent(this, ProfileActivity.class);
                startActivity(profile);
                break;

            case R.id.logoutbtn:
                Intent logout = new Intent(this, InformationActivity.class);
                startActivity(logout);
                break;

            case R.id.settingbtn:
                Intent setting = new Intent(this, SettingActivity.class);
                startActivity(setting);
                break;

            default:
                Toast.makeText(this, "In default", Toast.LENGTH_SHORT).show();
        }
    }
}