package com.mywaytech.puppiessearchclient.models;

import java.io.Serializable;

/**
 * Created by m.maigua on 4/14/2016.
 */
public class UserPetObject implements Serializable {
    private String uName;
    private String uAddress;
    private int pImage;
    private String imagePath;
    private String uComment;

    public UserPetObject(String uName, String uAddress, int pImage, String uComment) {
        this.uName = uName;
        this.uAddress = uAddress;
        this.pImage = pImage;
        this.uComment = uComment;
    }

    public UserPetObject(String uName,String uAddress,String imagePath, String uComment) {
        this.uName = uName;
        this.uAddress = uAddress;
        this.imagePath = imagePath;
        this.uComment = uComment;


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

    public int getpImage() {
        return pImage;
    }

    public void setpImage(int pImage) {
        this.pImage = pImage;
    }

    public String getuComment() {
        return uComment;
    }

    public void setuComment(String uComment) {
        this.uComment = uComment;
    }
}
