package com.mywaytech.puppiessearchclient.models;

import java.io.Serializable;

/**
 * Created by Marco on 4/19/2016.
 */
public class NewUserObject implements Serializable {
    private String mName;
    private String mEmail;
    private String mPassword;
    private String mUserImagePath;
    private String mAddress;

    public NewUserObject(){

    }

    public NewUserObject(String mName, String mEmail, String mPassword) {
        this.mName = mName;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
    }

    public String getUserImagePath() {
        return mUserImagePath;
    }

    public void setUserImagePath(String userImagePath) {
        mUserImagePath = userImagePath;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }
}
