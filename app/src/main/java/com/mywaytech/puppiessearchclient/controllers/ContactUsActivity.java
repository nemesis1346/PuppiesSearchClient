package com.mywaytech.puppiessearchclient.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.fragments.ContactUsFragment;
import com.mywaytech.puppiessearchclient.controllers.fragments.LoginFragment;

/**
 * Created by nemesis1346 on 7/10/2016.
 */
public class ContactUsActivity extends BaseActivity{

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ContactUsActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.fragment_container, ContactUsFragment.newInstance());
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
