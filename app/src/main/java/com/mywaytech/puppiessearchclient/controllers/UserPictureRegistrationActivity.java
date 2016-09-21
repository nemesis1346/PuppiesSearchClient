package com.mywaytech.puppiessearchclient.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.fragments.LoginFragment;
import com.mywaytech.puppiessearchclient.controllers.fragments.UserPictureRegistrationFragment;

/**
 * Created by Marco on 21/9/2016.
 */
public class UserPictureRegistrationActivity extends BaseActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, UserPictureRegistrationActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_picture_registration);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, UserPictureRegistrationFragment.newInstance());
        ft.commit();
    }
}
