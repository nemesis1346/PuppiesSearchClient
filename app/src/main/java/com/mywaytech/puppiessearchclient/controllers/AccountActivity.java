package com.mywaytech.puppiessearchclient.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.fragments.AccountFragment;

/**
 * Created by marco on 4/17/2016.
 */
public class AccountActivity extends AppCompatActivity {

    public static final String EXTRA_OUTSIDE_USER_FLAG ="extra_outside_user_flag";
    private boolean mOutsideUserFlag;

    public static Intent newIntent(Context context){
        return new Intent(context, AccountActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        mOutsideUserFlag=getIntent().getBooleanExtra(EXTRA_OUTSIDE_USER_FLAG,mOutsideUserFlag);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, AccountFragment.newInstance(mOutsideUserFlag));
        ft.commit();
    }

}
