package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mywaytech.puppiessearchclient.R;

/**
 * Created by Marco on 9/19/2016.
 */
public class AccountFragment extends Fragment {

    public static AccountFragment newInstance(){
        Bundle args = new Bundle();
        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
