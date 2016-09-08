package com.mywaytech.puppiessearchclient.models;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by m.maigua on 4/14/2016.
 */
public class UserPetObject implements Serializable {
    private String uId;
    private String uName;
    private String uAddress;
    private String imagePath;
    private String uComment;
    //TODO FIX THE ORDER AND ERASE THE PREVIOUS OBJECTS AND FIX THE VIEW AND PUT CURRENT USER UID


    public UserPetObject(String uName,String uAddress,String imagePath, String uComment) {
        this.uName = uName;
        this.uAddress = uAddress;
        this.imagePath = imagePath;
        this.uComment = uComment;
    }
    public UserPetObject() {
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuAddress() {
        return uAddress;
    }

    public void setuAddress(String uAddress) {
        this.uAddress = uAddress;
    }





    public String getuComment() {
        return uComment;
    }

    public void setuComment(String uComment) {
        this.uComment = uComment;
    }
}
