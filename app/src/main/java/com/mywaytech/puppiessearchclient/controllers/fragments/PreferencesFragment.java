package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.AccountActivity;
import com.mywaytech.puppiessearchclient.controllers.MainActivity;

/**
 * Created by m.maigua on 7/20/2016.
 */
public class PreferencesFragment extends PreferenceFragment  {

    private static final String KEY_ACCOUNT = "preference_key_account";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_preferences_layout);

        Preference accountPref = findPreference(KEY_ACCOUNT);
        accountPref.setOnPreferenceClickListener(mAccountPrefListener);
    }

    private Preference.OnPreferenceClickListener mAccountPrefListener = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            Intent intent = AccountActivity.newIntent(getActivity());
            startActivity(intent);
            return true;
        }
    };

}
