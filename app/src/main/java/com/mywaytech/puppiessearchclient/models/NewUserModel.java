package com.mywaytech.puppiessearchclient.models;

import java.io.Serializable;

/**
 * Created by Marco on 4/19/2016.
 */
public class NewUserModel implements Serializable {
    private String mUid;
    private String mName;
    private String mEmail;
    private String mPassword;
    private String mUserImagePath;
    private String mAddress;

    public NewUserModel() {

    }

    public NewUserModel(String name,
                        String email,
                        String password,
                        String address,
                        String userImagePath,
                        String uid) {
        this.mName = name;
        this.mEmail = email;
        this.mPassword = password;
        this.mAddress = address;
        this.mUserImagePath = userImagePath;
        this.mUid = uid;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
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
