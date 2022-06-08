package com.mobileapplication.vrmain;

import android.location.Address;
import android.location.Location;
import android.util.Log;

public class myParserJSON {
    private final static String TAG = "myParserJSON";
    Location location1;
    Address location2;

    myParserJSON(Location location1, Address location2){
        this.location1 = location1;
        this.location2 = location2;
    }

    myParserJSON(Location location){
        this.location1 = location;
    }

    public String getLongAndLatAdress(Address adress){
        Log.d(TAG, "location" + adress);
        Double mlong = adress.getLongitude();
        Double mlat = adress.getLatitude();
        return  mlat + "," + mlong;
    }

    public String getLongAndLatLocation(Location myLocation){
        Log.d(TAG, "location" + myLocation);
        Double mlong = myLocation.getLongitude();
        Double mlat = myLocation.getLatitude();

        return  mlat + "," + mlong;
    }



    public String toString(){
        return location1.toString();
    }


}

