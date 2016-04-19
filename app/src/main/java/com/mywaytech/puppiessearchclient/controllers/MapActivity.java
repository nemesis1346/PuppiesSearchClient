package com.mywaytech.puppiessearchclient.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mywaytech.puppiessearchclient.R;

/**
 * Created by marco on 4/17/2016.
 */
public class MapActivity extends BaseActivity {
    @Override
    public int getToolbarTitle() {
        return R.string.mapactivity_title;
    }

    @Override
    public int getContentResource() {
        return R.layout.activity_map;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
