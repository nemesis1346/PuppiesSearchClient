package com.mywaytech.puppiessearchclient.controllers.controllers;

import com.mywaytech.puppiessearchclient.R;

/**
 * Created by m.maigua on 4/13/2016.
 */
public class NewPetActivity extends BaseActivity {
    @Override
    public int getToolbarTitle() {
        return R.string.resport_lost_pet;
    }

    @Override
    public int getContentResource() {
        return R.layout.activity_new_lost_pet;
    }
}
