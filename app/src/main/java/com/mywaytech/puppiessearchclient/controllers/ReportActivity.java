package com.mywaytech.puppiessearchclient.controllers;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.fragments.ReportFragment;

/**
 * Created by m.maigua on 4/13/2016.
 */
public class ReportActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, ReportActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, ReportFragment.newInstance());
        ft.commit();

    }

}