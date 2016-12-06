package com.mywaytech.puppiessearchclient.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.fragments.LoginFragment;


/**
 * Created by m.maigua on 4/13/2016.
 */
public class LoginActivity extends BaseActivity  {

    public static final String EXTRA_NOTIFICATION_FLAG = "extra_notification_flag";
    private boolean mNotificationFlag=false;

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);


        mNotificationFlag = getIntent().getBooleanExtra(EXTRA_NOTIFICATION_FLAG,false);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.replace(R.id.fragment_container, LoginFragment.newInstance(mNotificationFlag));
            ft.commit();



    }




}
