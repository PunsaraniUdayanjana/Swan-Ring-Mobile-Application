package com.designproject.designproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.designproject.designproject.databinding.ActivityLocationBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

//  Declare instance variables
    private GoogleMap mMap;
    private ActivityLocationBinding binding;
    List<Address> listGeoCodes;
    double latitude,longitude;
    String countryName;

    SupportMapFragment mapFragment;
    SearchView searchView;
    private static final int LOCATION_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//      Check if the location permission has been granted
        if(isPermissionGranted()){

//      Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        }else{
            requestLocationPermission();
        }

//      Try to get the geocodes for a location and log the latitude, longitude, and country name
        try {
            listGeoCodes = new Geocoder(this).getFromLocationName("Matara",1);
            latitude = listGeoCodes.get(0).getLatitude();
            longitude = listGeoCodes.get(0).getLongitude();
            countryName = listGeoCodes.get(0).getCountryName();
        }catch(Exception exception){
            exception.printStackTrace();
        }

//      Search bar related codes
        searchView =findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(LocationActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                    if (addressList.size()== 0){
                        Toast.makeText(LocationActivity.this, "Not found", Toast.LENGTH_SHORT).show();
                    }else {
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        if (latLng == null) {
                            Toast.makeText(LocationActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                        } else {
                            mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                        }
                    }

                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);


//      SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setPadding(0,180,0,0);
//      Check if the ACCESS_FINE_LOCATION permission is granted
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
//          Enable the "My Location" feature on the map
            mMap.setMyLocationEnabled(true);
        }

        LatLng Matara = new LatLng(latitude, longitude);
//      Add a marker to the map at the specified LatLng coordinates
        mMap.addMarker(new MarkerOptions().position(Matara).title("Matara"));
//      Move the camera to the specified LatLng coordinates and zoom to a level of 14.0f
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Matara, 14.0f));
//      Enable zoom controls on the map
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
    public boolean isPermissionGranted(){
//      Check if the ACCESS_FINE_LOCATION permission is granted
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return false;
        }
    }
    public void requestLocationPermission(){
//      Request the ACCESS_FINE_LOCATION permission from the user
        ActivityCompat.requestPermissions(this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_CODE);
    }
}