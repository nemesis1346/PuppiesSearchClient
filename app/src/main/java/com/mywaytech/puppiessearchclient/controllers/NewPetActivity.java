package com.mywaytech.puppiessearchclient.controllers;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.EditText;

import com.mywaytech.puppiessearchclient.R;

/**
 * Created by m.maigua on 4/13/2016.
 */
public class NewPetActivity extends BaseActivity {
    private EditText newAddress;
    private EditText newComment;
    private int newImage;

    @Override
    public int getToolbarTitle() {
        return R.string.resport_lost_pet;
    }

    @Override
    public int getContentResource() {
        return R.layout.activity_new_lost_pet;
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        newAddress= (EditText) findViewById(R.id.edit_text_address);
        newComment= (EditText) findViewById(R.id.edit_text_password);
    }
}
