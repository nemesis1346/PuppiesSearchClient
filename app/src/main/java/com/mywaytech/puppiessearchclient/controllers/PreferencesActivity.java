package com.mywaytech.puppiessearchclient.controllers;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.fragments.PreferencesFragment;

/**
 * Created by m.maigua on 7/19/2016.
 */
public class PreferencesActivity extends BaseActivity {
    @Override
    public int getToolbarTitle() {
        return R.id.preferencesTitle;
    }

    @Override
    public int getContentResource() {
        return R.layout.activity_preferences;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragmentManager().beginTransaction()
                .replace(R.id.container_preferences, new PreferencesFragment())
                .commit();
    }

}
