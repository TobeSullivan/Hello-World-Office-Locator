package com.helloworld.map.async;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.helloworld.map.Classes.MyDistance;
import com.helloworld.map.interfaces.HandleJSON;
import com.helloworld.map.models.LocationJSON;
import com.helloworld.map.models.LocationModel;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

/**
 * Created by Tobe Sullivan on 4/21/2015.
 */
public class GetJSONAsync extends AsyncTask<Void, Void, LocationJSON> {
    private static final String URL = "http://www.helloworld.com/helloworld_locations.json";
    private static final String SAVED_JSON = "Saved JSON";

    private Context mContext;

    public GetJSONAsync(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected LocationJSON doInBackground(Void... params) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(URL);
        try {
            HttpResponse getResponse = httpClient.execute(getRequest);
            int statusCode = getResponse.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity getResponseEntity = getResponse.getEntity();
                Gson gson = new Gson();
                Reader reader = new InputStreamReader(getResponseEntity.getContent());
                LocationJSON locationJSON = gson.fromJson(reader, LocationJSON.class);
                String save = gson.toJson(locationJSON);
                PreferenceManager.getDefaultSharedPreferences(mContext).edit().putString(SAVED_JSON, save).commit();
                return locationJSON;
            } else {
                return null;
            }

        } catch (IOException e) {
            getRequest.abort();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(LocationJSON locationJSON) {
        super.onPostExecute(locationJSON);

        if (locationJSON != null) {
            ArrayList<LocationModel> locationModels = locationJSON.locationModels;
            MyDistance myDistance = new MyDistance(mContext);
            for (LocationModel locationModel : locationModels) {
                String distance = myDistance.getDistance(locationModel.mLatitude, locationModel.mLongitude);
                locationModel.setDistance(distance);
            }

            HandleJSON jsonHandler = (HandleJSON) mContext;
            jsonHandler.returnLocations(locationModels);
        }
    }
}
