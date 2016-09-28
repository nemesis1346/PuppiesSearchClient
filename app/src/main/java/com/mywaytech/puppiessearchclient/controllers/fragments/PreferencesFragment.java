package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.AccountActivity;
import com.mywaytech.puppiessearchclient.controllers.MainActivity;

/**
 * Created by m.maigua on 7/20/2016.
 */
public class PreferencesFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private static final String KEY_ACCOUNT = "preference_key_account";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_preferences_layout);
    }
    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        switch (key) {
            case KEY_ACCOUNT:
                Intent intent = AccountActivity.newIntent(getContext());
                startActivity(intent);
                break;
            default:
                break;
        }
        return false;
    }
}
