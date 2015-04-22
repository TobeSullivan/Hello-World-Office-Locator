package com.helloworld.map.interfaces;

import com.helloworld.map.models.LocationModel;

import java.util.ArrayList;

/**
 * Created by Tobe Sullivan on 4/21/2015.
 */
public interface HandleJSON {
    void returnLocations(ArrayList<LocationModel> locationModels);
}
