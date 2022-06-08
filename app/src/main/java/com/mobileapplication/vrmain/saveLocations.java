package com.mobileapplication.vrmain;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class saveLocations extends AppCompatActivity {
    private static final String TAG = "saveLocations";
    private Date currentTime;
    private static ArrayList<Address> saveAdress;
    private int numOfItem;
    Address newAddress;
    public static ArrayList<String> stringAdresses;
    MapsActivity maps = new MapsActivity();

    saveLocations(){

    }


    saveLocations(Address address){
       this.newAddress = address;
       addAddress();
       savedAddresses();
       saveData(maps);
        //createString();
       numOfItem++;
    }

    public ArrayList<Address> addAddress(){
        saveAdress = new ArrayList<>();
        saveAdress.add(newAddress);
        numOfItem++;
        Log.d(TAG, "numOfItem: " + numOfItem);
        Log.d(TAG, "saveAdress: "+ saveAdress);
        return saveAdress;
    }

    public ArrayList<String> savedAddresses(){
        stringAdresses = new ArrayList<>();
        if(numOfItem > 0) {
            for (int i = 0; i < this.saveAdress.size(); i++) {
                stringAdresses.add(saveAdress.get(i).toString());
                Log.d(TAG, "savedAdress: " + saveAdress.get(i).toString());
                numOfItem++;
            }
        }
        Log.d(TAG, "stringAdresses: "+ stringAdresses);
       return stringAdresses;

    }

    public boolean savedAdressesEmpty(){
        Log.d(TAG, "numOfItem: " + numOfItem);
        if(numOfItem > 0){
            return true;
        }
        else{
            return false;
        }

    }

    public void createString(){
        if(numOfItem > 0) {
            for (int i = 0; i < stringAdresses.size(); i++) {
                currentTime = Calendar.getInstance().getTime();
                String finalAddress = currentTime.toString() + " Address: " + stringAdresses.get(i) + "\n";
                Log.d(TAG, "finalAdress: " + finalAddress);
            }
        }
    }

    /*public void writeToFile(String fileName, String content) {
        File path = getApplicationContext().getFilesDir();

        try {
            FileOutputStream writer = new FileOutputStream(new File(path,fileName), true);
            writer.write(content.getBytes());
            writer.close();
            Toast.makeText(getApplicationContext(), "Saved to file: " +  fileName, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    private void saveData(Context cxt){
        /*SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //if(numOfItem > 0) {
           // for (int i = 0; i < stringAdresses.size(); i++) {
                //editor.putString("Location" + i, stringAdresses.get(i));
                editor.putString("Test", "Den första stringen");
                editor.apply();
                Toast.makeText(this, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
            //}
        //}*/



// Creating an Editor object to edit(write to the file)

        /*SharedPreferences.Editor myEdit = .edit();

// Storing the key and its value as the data fetched from edittext
        myEdit.putString("name","John");
        myEdit.putInt("age", Integer.parseInt("45"));

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
        myEdit.commit();
        getData();*/
    }

    private void getData(){

// Retrieving the value using its keys the file name
// must be same in both saving and retrieving the data
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

// The value will be default as empty string because for
// the very first time when the app is opened, there is nothing to show
        String s1 = sh.getString("name", "");
        int a = sh.getInt("age", 0);

// We can then use the data
        Log.d(TAG, "StringShared: " + s1);
        Toast.makeText(this, s1, Toast.LENGTH_SHORT).show();

    }
}
