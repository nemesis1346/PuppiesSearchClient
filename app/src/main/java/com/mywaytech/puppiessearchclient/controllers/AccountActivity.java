package com.mywaytech.puppiessearchclient.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mywaytech.puppiessearchclient.R;

/**
 * Created by marco on 4/17/2016.
 */
public class AccountActivity extends BaseActivity {
    @Override
    public int getToolbarTitle() {
        return R.string.myAccountTitle;
    }

    @Override
    public int getContentResource() {
        return R.layout.activity_user_account;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
