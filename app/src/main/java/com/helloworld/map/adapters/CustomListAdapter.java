package com.helloworld.map.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.helloworld.map.R;
import com.helloworld.map.models.LocationModel;

import java.util.ArrayList;

/**
 * Created by Tobe Sullivan on 4/21/2015.
 */
public class CustomListAdapter extends BaseAdapter {
    private static final String MILES = " (mi)";

    private Context mContext;
    private ArrayList<LocationModel> mLocationsList;

    public CustomListAdapter(Context context, ArrayList<LocationModel> locationsList) {
        mContext = context;
        mLocationsList = locationsList;
    }

    @Override
    public int getCount() {
        return mLocationsList.size();
    }

    @Override
    public LocationModel getItem(int position) {
        return mLocationsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView mName;
        TextView mAddress;
        TextView mDistance;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mName = (TextView) convertView.findViewById(R.id.listLocationName);
            viewHolder.mAddress = (TextView) convertView.findViewById(R.id.listLocationAddress);
            viewHolder.mDistance = (TextView) convertView.findViewById(R.id.listLocationDistance);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LocationModel locationModel = getItem(position);

        if (locationModel != null) {
            viewHolder.mName.setText(locationModel.mName);
            viewHolder.mAddress.setText(locationModel.mAddress);
            viewHolder.mDistance.setText(locationModel.mDistance + MILES);
        }

        return convertView;
    }
}
