package com.mywaytech.puppiessearchclient.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.fragments.LoginFragment;
import com.mywaytech.puppiessearchclient.services.FireBaseHandler;
import com.mywaytech.puppiessearchclient.utils.AlertDialogUtils;
import com.mywaytech.puppiessearchclient.utils.ProgressDialogUtils;


/**
 * Created by m.maigua on 4/13/2016.
 */
public class LoginActivity extends BaseActivity  {

    public static final String EXTRA_NOTIFICATION_FLAG = "extra_notification_flag";
    private boolean mNotificationFlag=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mNotificationFlag = getIntent().getBooleanExtra(EXTRA_NOTIFICATION_FLAG,false);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(mNotificationFlag){
            ft.replace(R.id.fragment_container, LoginFragment.newInstance(mNotificationFlag));
            ft.commit();
        }else{
            ft.replace(R.id.fragment_container, LoginFragment.newInstance(mNotificationFlag));
            ft.commit();
        }

    }




}
