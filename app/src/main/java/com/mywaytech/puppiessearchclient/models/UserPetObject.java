package com.mywaytech.puppiessearchclient.models;

import java.io.Serializable;

/**
 * Created by m.maigua on 4/14/2016.
 */
public class UserPetObject implements Serializable {
    private String uName;
    private String uAddress;
    private int pImage;
    private int uImage;
    private int uComment;

    public UserPetObject(String uName, String uAddress, int pImage, int uImage, int uComment) {
        this.uName = uName;
        this.uAddress = uAddress;
        this.pImage = pImage;
        this.uImage = uImage;
        this.uComment = uComment;
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

    public int getuImage() {
        return uImage;
    }

    public void setuImage(int uImage) {
        this.uImage = uImage;
    }

    public int getuComment() {
        return uComment;
    }

    public void setuComment(int uComment) {
        this.uComment = uComment;
    }
}
