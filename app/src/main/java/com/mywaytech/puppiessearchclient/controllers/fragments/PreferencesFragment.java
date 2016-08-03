package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.mywaytech.puppiessearchclient.R;

/**
 * Created by m.maigua on 7/20/2016.
 */
public class PreferencesFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_preferences_layout);
    }
}
