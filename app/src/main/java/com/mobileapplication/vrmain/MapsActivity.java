package com.mobileapplication.vrmain;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.mobileapplication.vrmain.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MapsActivity extends MenuActivity implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static String TAG = "MapsActivity";
    private FusedLocationProviderClient fusedLocationClient;
    private myParserJSON parser;
    public static Location myLocation;
    public Address destination;
    public SharedPreferences sharedpreferences;



    public ArrayList<String> times = new ArrayList<>();
    public ArrayList<String> distances = new ArrayList<>();
    private Geocoder coder;
    private GetDirections directions;
    private int numOfDestinations;
    private SavedRoutes routes;

    private boolean permissionDenied = false;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    public static final String PREFS_COUNT = "MyLocations";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        coder = new Geocoder(this);
        routes = new SavedRoutes();
        sharedpreferences = getApplicationContext().getSharedPreferences(PREFS_COUNT, Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedpreferences.edit();
        if(myLocation != null) {
            parser = new myParserJSON(myLocation);
        }

// Storing the key and its value as the data fetched from edittext




        String def = "";
        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_COUNT, Context.MODE_PRIVATE);

        String value = settings.getString("name1", def);
        Log.d(TAG, "value: " + value);
        routes.setmDistance(value);
        //myEdit.putInt("age", Integer.parseInt("45"));
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment)
            getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
            autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ADDRESS, Place.Field.ADDRESS_COMPONENTS));
            autocompleteSupportFragment.setTypeFilter(TypeFilter.ADDRESS);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyCtff1v5pHh2DMqvl9Ns6aL8v6N0CRZJvc");
        }
        getMyLocation();
        if(getMyLocation() && destination != null) {
            Log.d(TAG, "putLocationMarker");

            VolleyRequest.startRequest(this);
            //directions = new GetDirections("Disneyland", "Universal+Studios+Hollywood");
        }

        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                if(place.getAddressComponents().asList().get(0).getTypes().get(0).equalsIgnoreCase("route")){
                    Log.d(TAG, "place1: " + place);

                    List<Address> adress;
                    try {
                        adress = coder.getFromLocationName(place.getAddress(), 1);
                        Address location = adress.get(0);
                        destination = location;
                        numOfDestinations++;
                        myEdit.putString(Double.toString(destination.getLatitude()), destination.getAddressLine(0));
                        myEdit.commit();
                        //writeDestination();
                        Log.d(TAG, "numOfDestination: " + numOfDestinations);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(place != null) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(destination.getLatitude(),destination.getLongitude()))
                                .title("Destination"));
                        Distance();
                    }
                    if(numOfDestinations > 0){
                        Log.d(TAG, "Write destination: " + destination);
                        //writeDestination();
                        saveLocations savelocation = new saveLocations(destination);
                        savelocation.addAddress();
                    }
                }else{ //If user does not choose a specific place.
                    Log.d(TAG, "An error occured");
                }
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.mMap = googleMap;
        this.mMap.setOnMyLocationButtonClickListener(this);
        this.mMap.setOnMyLocationClickListener(this);
        enableMyLocation();



        //mMap.addMarker(new MarkerOptions().position().title("Marker in Sydney"));
        //mMap.addMarker(getDire)
        //https://maps.googleapis.com/maps/api/directions/json?origin=place_id:ChIJ3S-JXmauEmsRUcIaWtf4MzE&destination=Universal+Studios+Hollywood&key=AIzaSyCtff1v5pHh2DMqvl9Ns6aL8v6N0CRZJvc

    }

    private void putMyLocationMarker(){

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()))
                .title("My position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(myLocation.getLongitude(), myLocation.getLatitude())));
    }
    private boolean getMyLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Log.d(TAG, "fusedlocation");
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Log.d(TAG, "MyLocation" + location);
                            myLocation = location;

                        }
                        else{
                            Log.d(TAG, "nullocation");
                            return;
                        }
                    }

                });
        if(myLocation != null){
            Log.d(TAG, "Have postion");
            putMyLocationMarker();
            return true;
        }
        else {
            return false;
        }
    }

    private void writeDestination(){
        if(myLocation != null) {
            Log.d(TAG, "myLocation: " + parser.getLongAndLatLocation(myLocation));
            //Log.d(TAG, "myDestination: " + parser.getLongAndLatAdress(destination));
            String def = "";
            ArrayList<String> strings = new ArrayList<>();
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyLocations",0);
            Map<String,?> keys = sharedPreferences.getAll();
            for(Map.Entry<String,?> entry : keys.entrySet()) {
                //String loc = sharedPreferences.getString("name1", " ");
                //Log.d(TAG, "loc" + loc);
                strings.add(entry.getValue().toString());
                directions = new GetDirections(parser.getLongAndLatLocation(myLocation), entry.getValue().toString());
                Log.d(TAG, "JsonReturn" + directions.mhttpBuild());
            }


        }
    }




    private void Distance() {
     /*String Surl = new GetDirections(myLocation.toString(),destination.toString()).mhttpBuild();
     Log.d(TAG, "Distance string: " + Surl);*/
    }



    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        // [START maps_check_location_permission]
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            return;
        }

        // 2. Otherwise, request location permissions from the user.
        PermissionUtils.requestLocationPermissions(this, LOCATION_PERMISSION_REQUEST_CODE, true);
        // [END maps_check_location_permission]
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        getMyLocation();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    // [START maps_check_location_permission_result]
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION) || PermissionUtils
                .isPermissionGranted(permissions, grantResults,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // [START_EXCLUDE]
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
            // [END_EXCLUDE]
        }
    }
    // [END maps_check_location_permission_result]

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
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
    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f));

    }*/
}
   /* private void enableMyLocation() {
        //Check permissions
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
        mMap.setMyLocationEnabled(true);
        }
        PermissionUtils.requestLocationPermissions(this, LOCATION_PERMISSION_REQUEST_CODE, true);
    }*/

