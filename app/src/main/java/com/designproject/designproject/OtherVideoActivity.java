package com.designproject.designproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OtherVideoActivity extends AppCompatActivity implements View.OnClickListener {

//  Declare variables for VideoView, MediaController, and ImageView
    VideoView videoIdea, videoIdea1, videoIdea2, videoIdea3, videoIdea4;
    MediaController mediaController;
    ImageView icon, icon1, icon2, icon3, icon4, backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_video);

//      Find the heart icon view and set an onClickListener to change the icon when clicked
        icon = findViewById(R.id.heart);
        icon1 = findViewById(R.id.heart1);
        icon2 = findViewById(R.id.heart2);
        icon3 = findViewById(R.id.heart3);
        icon4 = findViewById(R.id.heart4);
        backIcon = findViewById(R.id.backIcon);

//      Set click listeners for the ImageView icons
        icon.setOnClickListener(this);
        icon1.setOnClickListener(this);
        icon2.setOnClickListener(this);
        icon3.setOnClickListener(this);
        icon4.setOnClickListener(this);


        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent now = new Intent(OtherVideoActivity.this, InspiretionActivity.class);
                startActivity(now);
            }
        });

//      Find the VideoView in the layout and set the path of the video to play
        videoIdea = findViewById(R.id.vi_idea);
        videoIdea.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.vi_idea);

//      Create a new MediaController and set it as the anchor view for the VideoView, then set the MediaController to the VideoView
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoIdea);
        videoIdea.setMediaController(mediaController);

        videoIdea1 = findViewById(R.id.vi_idea2);
        videoIdea1.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.vi_idea1);

        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoIdea1);
        videoIdea1.setMediaController(mediaController);

        videoIdea2 = findViewById(R.id.vi_idea3);
        videoIdea2.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.vi_idea2);

        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoIdea2);
        videoIdea2.setMediaController(mediaController);

        videoIdea3 = findViewById(R.id.vi_idea4);
        videoIdea3.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.vi_idea3);

        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoIdea3);
        videoIdea3.setMediaController(mediaController);

        videoIdea4 = findViewById(R.id.vi_idea5);
        videoIdea4.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.vi_idea4);

        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoIdea4);
        videoIdea4.setMediaController(mediaController);

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
        }
    }

//  The color changes when click on the icon
    public void changeHeartIcon(ImageView icon) {
        int i = (icon.getTag().toString().equals("black")) ? R.drawable.ic_red_heart : R.drawable.ic_baseline_favorite_24;
        String j = (icon.getTag().toString().equals("black")) ? "red" : "black";
        icon.setImageResource(i);
        icon.setTag(j);
    }
}

