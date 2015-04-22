package com.helloworld.map.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.helloworld.map.Classes.Connected;
import com.helloworld.map.R;
import com.helloworld.map.adapters.CustomListAdapter;
import com.helloworld.map.models.LocationModel;

import java.util.ArrayList;


public class LocationsActivity extends Activity implements OnMapReadyCallback {
    private static final String LOCATIONS_LIST = "Locations List";
    private static final String LOCATION = "Location";

    private ListView mListView;

    private ArrayList<LocationModel> mLocationsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        initExtras();
        initViews();
        initMap();
        initList();
    }

    private void initExtras() {
        Intent intent = getIntent();
        mLocationsList = intent.getParcelableArrayListExtra(LOCATIONS_LIST);
    }

    private void initViews() {
        mListView = (ListView) findViewById(R.id.locationsList);
    }

    private void initMap() {
        Connected connected = new Connected(this);
        MapFragment myMap = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        ImageView staticMap = (ImageView) findViewById(R.id.staticMap);
        if (connected.isConnected()) {
            staticMap.setVisibility(View.GONE);
            myMap.getView().setVisibility(View.VISIBLE);
            myMap.getMapAsync(LocationsActivity.this);
        } else {
            staticMap = (ImageView) findViewById(R.id.staticMap);
            myMap.getView().setVisibility(View.GONE);
            staticMap.setVisibility(View.VISIBLE);
        }
    }

    private void initList() {
        CustomListAdapter listAdapter = new CustomListAdapter(this, mLocationsList);
        mListView.setAdapter(listAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LocationsActivity.this, DetailsActivity.class);
                intent.putExtra(LOCATION, mLocationsList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng northAmerica = new LatLng(39.828127, -98.579404);
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(northAmerica, 3));

        for (LocationModel locationModel : mLocationsList) {
            LatLng next = new LatLng(Double.parseDouble(locationModel.mLatitude), Double.parseDouble(locationModel.mLongitude));
            MarkerOptions mapMarker = new MarkerOptions();
            mapMarker.position(next);
            mapMarker.title(locationModel.mName);
            mapMarker.snippet(locationModel.mAddress + " " + locationModel.mAddress2);
            mapMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
            googleMap.addMarker(mapMarker);
        }
    }
}
