package com.mywaytech.puppiessearchclient.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.models.NewUserObject;

/**
 * Created by marco on 4/17/2016.
 */
public class AccountActivity extends BaseActivity {
    public static final String EXTRA_USERDATA_IN = "com.mywaytech.puppiessearchclient.extras.extra_userdata_in";
    private NewUserObject newUserObject;

    private TextView mName;
    private TextView mEmail;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //newUserObject=new newUserObject();

        mName = (TextView) findViewById(R.id.show_user_name);
        mEmail = (TextView) findViewById(R.id.show_user_email);
        newUserObject = (NewUserObject) getIntent().getSerializableExtra(EXTRA_USERDATA_IN);
        mName.setText(newUserObject.getmName());
        mEmail.setText(newUserObject.getmEmail());

    }
}
