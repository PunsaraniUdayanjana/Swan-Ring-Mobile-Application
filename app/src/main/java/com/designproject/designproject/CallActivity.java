package com.designproject.designproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CallActivity extends AppCompatActivity implements View.OnClickListener {

//  declare variables
    EditText number;
    FloatingActionButton callBtn,closeBtn;
    static int PERMISSION_CODE = 100;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

//      Initialize views
        closeBtn = findViewById(R.id.closebtn);
        number = findViewById(R.id.phone);
        callBtn = findViewById(R.id.callbtn);

        closeBtn.setOnClickListener(this);

//      Request CALL_PHONE permission if not granted
        if (ContextCompat.checkSelfPermission(CallActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CallActivity.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CODE);
        }

//      Set click listener for call button
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneno = number.getText().toString();
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+phoneno));
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//          Handle close button click
            case R.id.closebtn:
                Intent photo = new Intent(this, PhotographerName1Activity.class);
                startActivity(photo);
                break;
            default:
                Toast.makeText(this, "In default", Toast.LENGTH_SHORT).show();
        }
    }
}