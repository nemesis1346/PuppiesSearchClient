package com.mywaytech.puppiessearchclient.models;

/**
 * Created by m.maigua on 7/29/2016.
 */
public class LocationModel {

    private int mId;
    private String mName;
    private double mLongitude;
    private double mLatitude;
    private String mAddress;

    public LocationModel(){}

    public LocationModel(int id, String name, double lng, double lat, String address){
        this.mId = id;
        this.mName = name;
        this.mLatitude = lng;
        this.mLatitude = lat;
        this.mAddress = address;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }
}
