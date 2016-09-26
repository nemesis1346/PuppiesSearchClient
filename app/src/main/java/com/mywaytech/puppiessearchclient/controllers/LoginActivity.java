package com.mywaytech.puppiessearchclient.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.fragments.LoginFragment;


/**
 * Created by m.maigua on 4/13/2016.
 */
public class LoginActivity extends BaseActivity  {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, LoginFragment.newInstance());
        ft.commit();
    }
}
