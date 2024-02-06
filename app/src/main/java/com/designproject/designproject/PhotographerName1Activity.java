package com.designproject.designproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PhotographerName1Activity extends AppCompatActivity implements View.OnClickListener {

//  Variables
    TextView viewImage;
    ImageView call,whatsapp,icon,backIcon;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographer_name1);

//      Initialize variables
        call = findViewById(R.id.callBtn);
        whatsapp = findViewById(R.id.whatsapp);
        viewImage = findViewById(R.id.viewImage);
        icon = findViewById(R.id.star);
        backIcon = findViewById(R.id.backIcon);

        icon.setOnClickListener(this);
        viewImage.setOnClickListener(this);
        call.setOnClickListener(this);
        whatsapp.setOnClickListener(this);
        backIcon.setOnClickListener(this);

//      Set up the bottom navigation view and handle item selection
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.navi_item);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homeId:
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    return true;

                case R.id.listId:
                    startActivity(new Intent(getApplicationContext(),InvitationActivity.class));
                    return true;

                case R.id.hotelId:
                    startActivity(new Intent(getApplicationContext(),WeddingPlanesActivity.class));
                    return true;

                case R.id.menuId:
                    startActivity(new Intent(getApplicationContext(),MenuActivity.class));
                    return true;
            }
            return false;
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewImage:
                Intent photo = new Intent(this, MadhawaImageActivity.class);
                startActivity(photo);
                break;
            case R.id.callBtn:
                Intent call = new Intent(this, CallActivity.class);
                startActivity(call);
                break;
            case R.id.whatsapp:
                Intent whatsapp = new Intent(this,WhatsappActivity.class);
                startActivity(whatsapp);
            case R.id.star:
                changeHeartIcon(icon);
                break;
            case R.id.backIcon:
                Intent backIcon = new Intent(this, PhotograpyActivity.class);
                startActivity(backIcon);
                break;
            default:
                Toast.makeText(this, "In default", Toast.LENGTH_SHORT).show();
        }
    }

//  The color changes when click on the icon
    public void changeHeartIcon(ImageView icon) {
        int i = (icon.getTag().toString().equals("black"))? R.drawable.ic_yellow_star: R.drawable.ic_star;
        String j = (icon.getTag().toString().equals("black"))? "yellow": "black";
        icon.setImageResource(i);
        icon.setTag(j);
    }
}