package com.designproject.designproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InspiretionActivity extends AppCompatActivity implements View.OnClickListener{

//  Declare ImageViews for inspiration categories and back button.
    ImageView Idea_Image,Idea_Video,backIcon;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspiretion);
//      getSupportActionBar().hide();

//      Find the ImageView objects by their IDs and assign them to the variables declared earlier.
        backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent now = new Intent(InspiretionActivity.this, HomeActivity.class);
                startActivity(now);
            }
        });

 //      assign variables
         Idea_Image = (ImageView) findViewById(R.id.imageClick);
         Idea_Video = (ImageView) findViewById(R.id.videoClick);

         Idea_Image.setOnClickListener(this);
         Idea_Video.setOnClickListener(this);

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

//  This method is called when one of the clickable views is clicked, and it starts a new activity based on the view clicked
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageClick:
                Intent otherImage = new Intent(this, OtherImageActivity.class);
                startActivity(otherImage);
                break;

            case R.id.videoClick:
                Intent otherVideo = new Intent(this, OtherVideoActivity.class);
                startActivity(otherVideo);

            default:
                Toast.makeText(this, "In default", Toast.LENGTH_SHORT).show();
        }
    }
}
