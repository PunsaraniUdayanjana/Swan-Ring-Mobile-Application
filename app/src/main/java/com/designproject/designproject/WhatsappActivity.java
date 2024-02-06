package com.designproject.designproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WhatsappActivity extends AppCompatActivity implements View.OnClickListener {

//  Variables
    Button sendBtn;
    EditText enterNumber,enterMessage;
    FloatingActionButton closeBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsapp);

//      assign variables
        sendBtn = findViewById(R.id.sendbtn);
        enterNumber = findViewById(R.id.enternumber);
        enterMessage = findViewById(R.id.entermessage);
        closeBtn = findViewById(R.id.closebtn);

        closeBtn.setOnClickListener(this);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              Get the mobile number and message from the respective input fields
                String mobileNumber = enterNumber.getText().toString();
                String message = enterMessage.getText().toString();

//              Check if WhatsApp is installed on the device
                boolean installed = appInstalledOrNot("com.whatsapp");

                if (installed){
//                  If WhatsApp is installed, create an intent to open the WhatsApp chat with the specified mobile number and message
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+94"+mobileNumber + "&text="+message));
                    startActivity(intent);
                }else{
//                  If WhatsApp is not installed, show a toast message indicating the same
                    Toast.makeText(WhatsappActivity.this, "whatsapp not installed on your device", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean appInstalledOrNot(String url){
//      Check if the specified app package is installed on the device
        PackageManager packageManager = getPackageManager();
        boolean app_installed;
        try{
            packageManager.getPackageInfo(url,PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }catch (PackageManager.NameNotFoundException e){
            app_installed = false;
        }
        return app_installed;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.closebtn:
                Intent photo = new Intent(this, PhotographerName1Activity.class);
                startActivity(photo);
                break;
            default:
                Toast.makeText(this, "In default", Toast.LENGTH_SHORT).show();
        }
    }
}