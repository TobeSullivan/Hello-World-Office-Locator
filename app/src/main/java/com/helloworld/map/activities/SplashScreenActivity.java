package com.helloworld.map.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.helloworld.map.Classes.Connected;
import com.helloworld.map.Classes.MyDistance;
import com.helloworld.map.R;
import com.helloworld.map.async.GetJSONAsync;
import com.helloworld.map.interfaces.HandleJSON;
import com.helloworld.map.models.LocationJSON;
import com.helloworld.map.models.LocationModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SplashScreenActivity extends Activity implements HandleJSON {
    private static final String LOCATIONS_LIST = "Locations List";
    private static final String SAVED_JSON = "Saved JSON";
    private static final String NO_DATA = "Please restart the application with a network connection";

    private ArrayList<LocationModel> mLocationsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initLoad();
    }

    private void initLoad() {
        Connected connected = new Connected(this);

        if (connected.isConnected()) {
            GetJSONAsync json = new GetJSONAsync(this);
            json.execute();
        } else {
            createList();
        }
    }

    public void createList() {
        MyDistance myDistance = new MyDistance(this);
        String saved = PreferenceManager.getDefaultSharedPreferences(this).getString(SAVED_JSON, NO_DATA);

        if (saved.equals(NO_DATA)) {
            ImageView logo = (ImageView) findViewById(R.id.logo);
            TextView error = (TextView) findViewById(R.id.connectionError);
            logo.setVisibility(View.GONE);
            error.setVisibility(View.VISIBLE);
        } else {
            Gson gson = new Gson();
            LocationJSON locationJSON = gson.fromJson(saved, LocationJSON.class);
            if (mLocationsList != null) {
                mLocationsList = new ArrayList<>();
            }
            mLocationsList = locationJSON.locationModels;
            for (LocationModel locationModel : mLocationsList) {
                String distance = myDistance.getDistance(locationModel.mLatitude, locationModel.mLongitude);
                locationModel.setDistance(distance);
            }
            Collections.sort(mLocationsList, new DistanceComparator());
            launchActivity();
        }
    }

    public void launchActivity() {
        Intent intent = new Intent(SplashScreenActivity.this, LocationsActivity.class);
        intent.putParcelableArrayListExtra(LOCATIONS_LIST, mLocationsList);
        startActivity(intent);
    }

    @Override
    public void returnLocations(ArrayList<LocationModel> locationModels) {
        mLocationsList = locationModels;
        Collections.sort(mLocationsList, new DistanceComparator());
        launchActivity();
    }

    static class DistanceComparator implements Comparator<LocationModel> {
        @Override
        public int compare(LocationModel lm1, LocationModel lm2) {
            return Double.compare(Double.parseDouble(lm1.getDistance()), Double.parseDouble(lm2.getDistance()));
        }
    }
}
