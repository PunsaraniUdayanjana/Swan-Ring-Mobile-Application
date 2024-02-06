package com.designproject.designproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WeddingPlanesActivity extends AppCompatActivity implements View.OnClickListener {

//  Variables
    CardView photography;
    ImageView backIcon;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wdding_planes);

//      assign variables
        photography = (CardView) findViewById(R.id.photoBtn);
        backIcon = findViewById(R.id.backIcon);

        photography.setOnClickListener(this);
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
            case R.id.photoBtn:
                Intent photo = new Intent(this, PhotograpyActivity.class);
                startActivity(photo);
                break;

            case R.id.backIcon:
                Intent backIcon = new Intent(this, HomeActivity.class);
                startActivity(backIcon);
                break;

            default:
                Toast.makeText(this, "In default", Toast.LENGTH_SHORT).show();
        }
    }
}