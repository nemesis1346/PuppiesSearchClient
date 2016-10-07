package com.mywaytech.puppiessearchclient.models;

/**
 * Created by nemesis1346 on 7/10/2016.
 */
public class ContactUsModel {
    private String mName;
    private String mEmailText;
    private String mFacebook;
    private String mCellphone;
    private String mAddress;
    private String mLink;

    public ContactUsModel() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getEmailText() {
        return mEmailText;
    }

    public void setEmailText(String emailText) {
        mEmailText = emailText;
    }

    public String getFacebook() {
        return mFacebook;
    }

    public void setFacebook(String facebook) {
        mFacebook = facebook;
    }

    public String getCellphone() {
        return mCellphone;
    }

    public void setCellphone(String cellphone) {
        mCellphone = cellphone;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }
}
