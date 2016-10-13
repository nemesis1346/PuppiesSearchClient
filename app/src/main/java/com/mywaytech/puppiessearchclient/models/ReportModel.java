package com.mywaytech.puppiessearchclient.models;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by m.maigua on 4/14/2016.
 */
public class ReportModel implements Serializable {
    private String uId;
    private String uName;
    private String uAddress;
    private String imagePath;
    private String uComment;
    private String uEmail;
    private String uType;
//    private String uDate;
    //TODO FIX THE ORDER AND ERASE THE PREVIOUS OBJECTS AND FIX THE VIEW AND PUT CURRENT USER UID


    public ReportModel(String uid, String uName, String uAddress, String imagePath, String uComment, String uEmail,String uType) {
        this.uId = uid;
        this.uName = uName;
        this.uAddress = uAddress;
        this.imagePath = imagePath;
        this.uComment = uComment;
        this.uEmail = uEmail;
        this.uType = uType;
//        this.uDate = uDate;
    }
    public ReportModel() {
    }
//
//    public String getuDate() {
//        return uDate;
//    }
//
//    public void setuDate(String uDate) {
//        this.uDate = uDate;
//    }

    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
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
