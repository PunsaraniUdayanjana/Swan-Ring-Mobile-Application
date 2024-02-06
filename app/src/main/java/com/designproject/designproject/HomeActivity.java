package com.designproject.designproject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements SensorEventListener,View.OnClickListener {

//  Variables
    VideoView videoView;
    CardView invitation,inspiration,plane,location;

//  sensor related variables
    SensorManager sensorManager;
    Sensor sensor;
    public boolean isRunning = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//      getSupportActionBar().hide();

//      sensor related variables
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
//      Initialize views
        invitation = (CardView) findViewById(R.id.invitationBtn);
        inspiration = (CardView) findViewById(R.id.inspiretionBtn);
        location = (CardView) findViewById(R.id.locationBtn);
        plane = (CardView) findViewById(R.id.planeBtn);

        invitation.setOnClickListener(this);
        inspiration.setOnClickListener(this);
        plane.setOnClickListener(this);

//      Start LocationActivity when the location button is clicked
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent location = new Intent(HomeActivity.this, LocationActivity.class);
                startActivity(location);
            }
        });

//      Set up the video background
        videoView = findViewById(R.id.main);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.wedding1);
        videoView.start();

//      Restart the video when it completes playing
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });

//      Check if the device has a light sensor
        PackageManager packageManager = getPackageManager();
        boolean hasLightSensor = packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_LIGHT);
        if (hasLightSensor) {
            // Light sensor is available on the device
            Toast.makeText(this, "Light sensor available", Toast.LENGTH_SHORT).show();
        } else {
            // Light sensor is not available on the device
            Toast.makeText(this, "Light sensor not available", Toast.LENGTH_SHORT).show();
        }

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
    //SENSOR RELATED METHODS
    // check the light intensity whether it is increased or not
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float lightvalue = event.values[0];

            if (event.values[0] > 100 && !isRunning) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Alert");
                alertDialog.setMessage(lightvalue + "\n" + "Increase your Display Brightness !");
                isRunning = true;
//              When click "Yes" it will execute this
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            } else if (event.values[0] <= 100) {
                isRunning = false;
            }
        }
    }
//  This method is called when the accuracy of the sensor changes, but we don't need to do anything in this case
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
//  This method is called when the activity resumes, and it registers the sensor listener
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
//  This method is called when the activity pauses, and it unregisters the sensor listener
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

//  This method is called when one of the buttons is clicked, and it starts a new activity based on the button clicked
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.invitationBtn:
                Intent invitation = new Intent(this, InvitationActivity.class);
                startActivity(invitation);
                break;

            case R.id.inspiretionBtn:
                Intent inspiration = new Intent(this, InspiretionActivity.class);
                startActivity(inspiration);
                break;

            case R.id.planeBtn:
                Intent plane = new Intent(this, WeddingPlanesActivity.class);
                startActivity(plane);

            default:
                Toast.makeText(this, "In default", Toast.LENGTH_SHORT).show();
        }
    }

    //When try to back from Login page this method will execute
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Exit App");
        alertDialog.setMessage("Do you want to exit Swan Ring ?");

        //When click "Yes" it will execute this
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                onStop();
                onDestroy();
            }
        });

        //When click "No" it will execute this
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}