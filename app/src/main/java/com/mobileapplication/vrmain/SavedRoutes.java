package com.mobileapplication.vrmain;

import android.content.SharedPreferences;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileapplication.vrmain.databinding.ActivitySavedRoutes3Binding;

import java.util.ArrayList;
import java.util.Map;

public class SavedRoutes extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivitySavedRoutes3Binding binding;


    private static final String TAG = "SavedRoutes";

    public ArrayList<String> mDistance = new ArrayList<>();
    public ArrayList<String> mtimes = new ArrayList<>();
    public ArrayList<Address> locations;
    private saveLocations location;
    //ArrayList<String> distance;
    ArrayList<String> times;
    public MapsActivity maps;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_routes3);
        location = new saveLocations();
        //distance = new ArrayList<>();

        findViewById(R.id.savedroutes_title);

            Log.d(TAG, "initiatingSavedAdresses");
            location.savedAddresses();
            this.times = new ArrayList<>();

        //Log.d(TAG, "Distance1: " + location.stringAdresses);
        Log.d(TAG, "Times1: " + times);
        maps = new MapsActivity();

        /*NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_saved_routes3);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);*/

        if(!location.savedAdressesEmpty()) {
            initRecyclerView();
            //minitView(location.savedAddresses(), times);
        }

    }

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_saved_routes3);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }*/

    private void minitView(ArrayList<String> distance, ArrayList<String> time) {
        /*Log.d(TAG, "initiatingView ");
        for(int i = 0; i < distance.size(); i++){
            Log.d(TAG, "prepaing view");
            location.savedAddresses().add("Distance:  "+ location.savedAddresses().get(i) + "km");
            this.mtimes.add("Route: "+ i);
        }
        //Log.d(TAG, "Distances: " + this.mDistance);
        Log.d(TAG, "Times: " + this.mtimes);
        */
        //initRecyclerView(maps);
    }

    public ArrayList<String> setmDistance(String location){
        Log.d(TAG, "Saved locations: " + location);
        mDistance.add(location);
        Log.d(TAG, "Array: " + mDistance.toString());
        return mDistance;

    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview. ");
        RecyclerView recyclerView = findViewById(R.id.recyclerview2);
        Log.d(TAG, "initRecyclerView: recyclerview found. ");
        Log.d(TAG, "Distances3: " + mDistance.toString());
        //Log.d(TAG, "Times3: " + this.mtimes);
        String def = "";
        ArrayList<String> strings = new ArrayList<>();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyLocations",0);
        Map<String,?> keys = sharedPreferences.getAll();
        for(Map.Entry<String,?> entry : keys.entrySet()) {
            //String loc = sharedPreferences.getString("name1", " ");
            //Log.d(TAG, "loc" + loc);
            strings.add(entry.getValue().toString());
        }
        /*strings.add("en string");
        strings.add("en till");*/
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, strings);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}