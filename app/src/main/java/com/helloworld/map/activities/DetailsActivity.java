package com.helloworld.map.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.helloworld.map.Classes.Connected;
import com.helloworld.map.R;
import com.helloworld.map.models.LocationModel;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends Activity implements OnMapReadyCallback {
    private static final String LOCATION = "Location";
    private static final String MILES = " (mi)";
    private static final String DETROIT = "Detroit";
    private static final String CHICAGO = "Chicago";
    private static final String NASHVILLE = "Nashville";
    private static final String NY = "New York";
    private static final String PHOENIX = "Phoenix";
    private static final String SEATTLE = "Seattle";
    private static final String LA = "Los Angeles";

    private LocationModel mLocationModel;

    private ImageView mOfficeImage;
    private TextView mCallButton, mDirectionsButton, mLocationName, mLocationAddress, mLocationDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initExtras();
        initMap();
        initViews();
        initHandlers();
    }

    private void initExtras() {
        Intent intent = getIntent();
        mLocationModel = intent.getParcelableExtra(LOCATION);
    }

    private void initMap() {
        Connected connected = new Connected(this);
        if (connected.isConnected()) {
            MapFragment myMap = (MapFragment) getFragmentManager().findFragmentById(R.id.detailsMap);
            myMap.getMapAsync(DetailsActivity.this);
        } else {
            setMap();
        }

    }

    private void initViews() {
        mOfficeImage = (ImageView) findViewById(R.id.officeImage);
        mCallButton = (TextView) findViewById(R.id.callButton);
        mDirectionsButton = (TextView) findViewById(R.id.directionsButton);
        mLocationName = (TextView) findViewById(R.id.locationName);
        mLocationAddress = (TextView) findViewById(R.id.locationAddress);
        mLocationDistance = (TextView) findViewById(R.id.locationDistance);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Picasso.with(DetailsActivity.this).load(mLocationModel.mOfficeImage).resize(width, (height / 5)).into(mOfficeImage);
        mLocationName.setText(mLocationModel.mName);
        mLocationAddress.setText(mLocationModel.mAddress + " " + mLocationModel.mAddress2);
        mLocationDistance.setText(mLocationModel.mDistance + MILES);
    }

    private void initHandlers() {
        mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "tel:" + mLocationModel.mPhone.replace("(", "").replace(")", "").replace(" ", "").replace("-", "");
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phone));
                startActivity(intent);
            }
        });

        mDirectionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=Current+Location&daddr=" + mLocationModel.mLatitude + "," + mLocationModel.mLongitude));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });
    }

    public void setMap() {
        String name = mLocationModel.mName;
        MapFragment myMap = (MapFragment) getFragmentManager().findFragmentById(R.id.detailsMap);
        ImageView staticMap = (ImageView) findViewById(R.id.detailsStaticMap);
        myMap.getView().setVisibility(View.GONE);
        staticMap.setVisibility(View.VISIBLE);

        if (name.contains(DETROIT)) {
            staticMap.setImageResource(R.drawable.staticmap_detroit);
        } else if (name.contains(CHICAGO)) {
            staticMap.setImageResource(R.drawable.staticmap_chicago);
        } else if (name.contains(NY)) {
            staticMap.setImageResource(R.drawable.staticmap_new_york);
        } else if (name.contains(NASHVILLE)) {
            staticMap.setImageResource(R.drawable.staticmap_nashville);
        } else if (name.contains(PHOENIX)) {
            staticMap.setImageResource(R.drawable.staticmap_phoenix);
        } else if (name.contains(SEATTLE)) {
            staticMap.setImageResource(R.drawable.staticmap_seattle);
        } else if (name.contains(LA)) {
            staticMap.setImageResource(R.drawable.staticmap_los_angeles);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        double newLat = Double.parseDouble(mLocationModel.mLatitude);
        double newLon = Double.parseDouble(mLocationModel.mLongitude);
        LatLng officeLocation = new LatLng(newLat, newLon);
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(officeLocation, 14));
        MarkerOptions mapMarker = new MarkerOptions();
        mapMarker.position(officeLocation);
        mapMarker.title(mLocationModel.mName);
        mapMarker.snippet(mLocationModel.mAddress + " " + mLocationModel.mAddress2);
        mapMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        googleMap.addMarker(mapMarker);
    }
}
