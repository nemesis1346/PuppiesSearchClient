package com.mywaytech.puppiessearchclient.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.models.NewUserObject;
import com.mywaytech.puppiessearchclient.services.UserDatabase;

/**
 * Created by marco on 4/17/2016.
 */
public class AccountActivity extends BaseActivity {
    public static final String EXTRA_USERDATA_IN = "com.mywaytech.puppiessearchclient.extras.extra_userdata_in";
    public static final String EXTRA_EMAIL = "com.mywaytech.puppiessearchclient.extras.extra_email";

    private NewUserObject newUserObject;

    private TextView mName;
    private TextView mEmail;
    private TextView mAddress;
    private UserDatabase myDB;

    private String authEmail;

    private String[] retrieve;

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

        authEmail = getIntent().getStringExtra(EXTRA_EMAIL);

        //INSTANCE OF LOCAL DATABASE
        //myDB = new UserDatabase(this);
        //newUserObject=new newUserObject();

        mName = (TextView) findViewById(R.id.show_user_name);
        mEmail = (TextView) findViewById(R.id.show_email);
        mAddress= (TextView) findViewById(R.id.show_address);

        //PRIOR METHOD USIGN DATABASES
//        retrieve = new String[]{
//                myDB.getAllDatabyEmail(authEmail)[0],
//                myDB.getAllDatabyEmail(authEmail)[1],
//                myDB.getAllDatabyEmail(authEmail)[2]};
//        mName.setText(retrieve[0]);
//        mEmail.setText(retrieve[1]);
//        mAddress.setText(retrieve[2]);


    }

}
