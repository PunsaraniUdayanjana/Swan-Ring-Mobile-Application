package com.designproject.designproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

//  Variables
    Switch rotationSwitch;
    ImageView backIcon;
    private TextView versionTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

//      assign variables
        rotationSwitch = findViewById(R.id.rotationSwitch);
        backIcon = findViewById(R.id.backIcon);
        versionTextView = findViewById(R.id.versionTextView);

//      Display Mobile phone version
        String androidVersion = "Android Version: " + Build.VERSION.RELEASE;
        versionTextView.setText(androidVersion);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent now = new Intent(SettingActivity.this, MenuActivity.class);
                startActivity(now);
            }
        });

//      rotate a page
        rotationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                  Set the activity's orientation to SENSOR, allowing automatic screen rotation
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                } else {
//                  Set the activity's orientation to PORTRAIT, locking it to portrait mode
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });
    }
}