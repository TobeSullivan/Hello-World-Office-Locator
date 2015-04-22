package com.helloworld.map.Classes;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by Tobe Sullivan on 4/22/2015.
 */
public class MyDistance {
    private Context mContext;

    public MyDistance(Context context) {
        mContext = context;
    }

    public String getDistance(String lat, String lon) {
        String distance = "";
        Double newLat = Double.parseDouble(lat);
        Double newLon = Double.parseDouble(lon);
        Location newLocation = new Location("");
        newLocation.setLatitude(newLat);
        newLocation.setLongitude(newLon);

        Connected connected = new Connected(mContext);

        if (connected.isConnected()) {
            LocationManager locationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
            Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            float distanceInMeters = newLocation.distanceTo(myLocation);
            double distanceInMiles = distanceInMeters * 0.00062137;
            distance = String.format("%.2f", distanceInMiles);
        }

        return distance;
    }

}
