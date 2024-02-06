package com.designproject.designproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MadhawaImageActivity extends AppCompatActivity implements View.OnClickListener  {

//  Variables
    ImageView icon, icon1, icon2, icon3, icon4, icon5, icon6, icon7, icon8, icon9, backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madhawa_image);

//      Initialize ImageView references
        icon = findViewById(R.id.heart);
        icon1 = findViewById(R.id.heart1);
        icon2 = findViewById(R.id.heart2);
        icon3 = findViewById(R.id.heart3);
        icon4 = findViewById(R.id.heart4);
        icon5 = findViewById(R.id.heart5);
        icon6 = findViewById(R.id.heart6);
        icon7 = findViewById(R.id.heart7);
        icon8 = findViewById(R.id.heart8);
        icon9 = findViewById(R.id.heart9);
        backIcon = findViewById(R.id.backIcon);

//      Set click listeners for the ImageView icons
        icon.setOnClickListener(this);
        icon1.setOnClickListener(this);
        icon2.setOnClickListener(this);
        icon3.setOnClickListener(this);
        icon4.setOnClickListener(this);
        icon5.setOnClickListener(this);
        icon6.setOnClickListener(this);
        icon7.setOnClickListener(this);
        icon8.setOnClickListener(this);
        icon9.setOnClickListener(this);
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
//          Call the changeHeartIcon method for the clicked heart icon
            case R.id.heart:
                changeHeartIcon(icon);
                break;

            case R.id.heart1:
                changeHeartIcon(icon1);
                break;

            case R.id.heart2:
                changeHeartIcon(icon2);
                break;

            case R.id.heart3:
                changeHeartIcon(icon3);
                break;

            case R.id.heart4:
                changeHeartIcon(icon4);
                break;

            case R.id.heart5:
                changeHeartIcon(icon5);
                break;

            case R.id.heart6:
                changeHeartIcon(icon6);
                break;

            case R.id.heart7:
                changeHeartIcon(icon7);
                break;

            case R.id.heart8:
                changeHeartIcon(icon8);
                break;

            case R.id.heart9:
                changeHeartIcon(icon9);
                break;
            case R.id.backIcon:
                Intent backIcon = new Intent(this, PhotographerName1Activity.class);
                startActivity(backIcon);
        }
    }

//    The color changes when click on the icon
    public void changeHeartIcon(ImageView icon) {
        int i = (icon.getTag().toString().equals("black"))? R.drawable.ic_red_heart: R.drawable.ic_baseline_favorite_24;
        String j = (icon.getTag().toString().equals("black"))? "red": "black";
        icon.setImageResource(i);// Set the appropriate heart icon
        icon.setTag(j);// Update the tag to reflect the new state
    }
}