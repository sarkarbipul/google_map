package com.example.binarygadget.googlemapday35;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {

                            mMap = googleMap;

                            // Add a marker in Sydney and move the camera
                            LatLng Farmgate = new LatLng(23.758155, 90.389319);
                            mMap.addMarker(new MarkerOptions().position(Farmgate).title("Marker in Farmgate"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(Farmgate));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(23.758155, 90.389319),11));

                    }
                });
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        LatLng DU = new LatLng(23.733835, 90.393152);
//
//
//        // Add a marker in Sydney and move the camera
//        LatLng Farmgate = new LatLng(23.758155, 90.389319);
//        mMap.addMarker(new MarkerOptions().position(Farmgate).title("Marker in Farmgate"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(Farmgate));

//        mMap.addMarker(new MarkerOptions().position(DU).title("Dhaka University").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(DU,11));
//
//        PolylineOptions polylineOptions = new PolylineOptions();
//        polylineOptions.add(DU);
//        polylineOptions.add(Farmgate);
//        polylineOptions.color(Color.GREEN);
//        polylineOptions.geodesic(true);
//        polylineOptions.startCap(new RoundCap());
//        polylineOptions.width(10);
//        polylineOptions.jointType(JointType.BEVEL);
//        googleMap.addPolyline(polylineOptions);
//
//        CircleOptions circleOptions = new CircleOptions();
//        circleOptions.center(DU).radius(8500).fillColor(Color.YELLOW).strokeColor(Color.RED).strokeWidth(3);
//        mMap.addCircle(circleOptions);

        currentLocation(googleMap);


    }

    private void currentLocation(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        mMap.setOnMyLocationClickListener(onMyLocationClickListener);
        enableMyLocationIfPrermitted();

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(11);
    }
    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener = new GoogleMap.OnMyLocationButtonClickListener() {
        @Override
        public boolean onMyLocationButtonClick() {
            mMap.setMinZoomPreference(15);
            return false;
        }
    };
    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener = new GoogleMap.OnMyLocationClickListener() {
        @Override
        public void onMyLocationClick(@NonNull Location location) {
            mMap.setMinZoomPreference(12);
            CircleOptions circleOptions = new CircleOptions();
            circleOptions.center(new LatLng(location.getLatitude(),location.getLongitude())).radius(200).fillColor(Color.GREEN).strokeColor(Color.RED).strokeWidth(3);
            mMap.addCircle(circleOptions);
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //write your code here
                    enableMyLocationIfPrermitted();
                }else {
                    // write your code here

                    showDefaultLocation();
                }
                return;
            }
        }
    }

    private void showDefaultLocation() {
        Toast.makeText(this,"Location not granted",Toast.LENGTH_SHORT).show();

        LatLng Farmgate = new LatLng(23.758155, 90.389319);
        mMap.addMarker(new MarkerOptions().position(Farmgate).title("Marker in Farmgate"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Farmgate));
    }

    private void enableMyLocationIfPrermitted() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_REQUEST_CODE);
        }else if (mMap != null){
            mMap.setMyLocationEnabled(true);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

}
