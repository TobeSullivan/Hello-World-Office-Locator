package com.helloworld.map.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tobe Sullivan on 4/21/2015.
 */
public class LocationModel implements Parcelable {
    @SerializedName("name")
    public String mName;

    @SerializedName("address")
    public String mAddress;

    @SerializedName("address2")
    public String mAddress2;

    @SerializedName("city")
    public String mCity;

    @SerializedName("state")
    public String mState;

    @SerializedName("zip_postal_code")
    public String mZipPostalCode;

    @SerializedName("phone")
    public String mPhone;

    @SerializedName("fax")
    public String mFax;

    @SerializedName("latitude")
    public String mLatitude;

    @SerializedName("longitude")
    public String mLongitude;

    @SerializedName("office_image")
    public String mOfficeImage;

    public String mDistance = "N/A";

    public void setDistance(String distance) {
        mDistance = distance;
    }

    public String getDistance() {
        return mDistance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mAddress);
        dest.writeString(mAddress2);
        dest.writeString(mCity);
        dest.writeString(mState);
        dest.writeString(mZipPostalCode);
        dest.writeString(mPhone);
        dest.writeString(mFax);
        dest.writeString(mLatitude);
        dest.writeString(mLongitude);
        dest.writeString(mOfficeImage);
        dest.writeString(mDistance);
    }

    public LocationModel(Parcel in) {
        mName = in.readString();
        mAddress = in.readString();
        mAddress2 = in.readString();
        mCity = in.readString();
        mState = in.readString();
        mZipPostalCode = in.readString();
        mPhone = in.readString();
        mFax = in.readString();
        mLatitude = in.readString();
        mLongitude = in.readString();
        mOfficeImage = in.readString();
        mDistance = in.readString();
    }

    public static final Parcelable.Creator<LocationModel> CREATOR = new Parcelable.Creator<LocationModel>() {
        public LocationModel createFromParcel(Parcel in) {
            return new LocationModel(in);
        }

        public LocationModel[] newArray(int size) {
            return new LocationModel[size];
        }
    };
}
