package com.mobileapplication.vrmain;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;


public class MenuActivity extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menue, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.savedRoutes_item:
                openSavedRoutes();
                return true;
            /*case R.id.scoreboard_item:
                openScoreboardActivity();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void openSavedRoutes() {
        Intent intent = new Intent(this, SavedRoutes.class);
        startActivity(intent);
    }


}
