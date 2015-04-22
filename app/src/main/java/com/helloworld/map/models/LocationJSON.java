package com.helloworld.map.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Tobe Sullivan on 4/21/2015.
 */
public class LocationJSON {
    @SerializedName("locations")
    public ArrayList<LocationModel> locationModels;
}
