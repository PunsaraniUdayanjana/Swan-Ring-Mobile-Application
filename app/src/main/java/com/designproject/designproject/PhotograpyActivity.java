package com.designproject.designproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PhotograpyActivity extends AppCompatActivity implements View.OnClickListener {

//  Variables
    VideoView videoView;
    CardView photographer1;
    ImageView backIcon;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photograpy);

//      Initialize variables
        photographer1 = findViewById(R.id.grapherBtn);
        backIcon = findViewById(R.id.backIcon);

        backIcon.setOnClickListener(this);
        photographer1.setOnClickListener(this);

//      Set up the video background
        videoView = findViewById(R.id.video);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.photo_2);
        videoView.start();

//      Restart the video when it completes playing
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });

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
            case R.id.grapherBtn:
                Intent photo = new Intent(this, PhotographerName1Activity.class);
                startActivity(photo);
                break;
            case R.id.backIcon:
                Intent backIcon = new Intent(this, WeddingPlanesActivity.class);
                startActivity(backIcon);
                break;
            default:
                Toast.makeText(this, "In default", Toast.LENGTH_SHORT).show();
        }
    }
}