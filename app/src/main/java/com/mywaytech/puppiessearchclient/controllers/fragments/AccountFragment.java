package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.models.NewUserObject;

/**
 * Created by Marco on 9/19/2016.
 */
public class AccountFragment extends Fragment {

    public static final String EXTRA_USERDATA_IN = "com.mywaytech.puppiessearchclient.extras.extra_userdata_in";
    public static final String EXTRA_EMAIL = "com.mywaytech.puppiessearchclient.extras.extra_email";

    private NewUserObject newUserObject;

    private TextView mName;
    private TextView mEmail;
    private TextView mAddress;

    private String authEmail;

    private String[] retrieve;

    public static AccountFragment newInstance(){
        Bundle args = new Bundle();
        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return  fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.main_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.report_title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mName = (TextView) rootView.findViewById(R.id.show_user_name);
        mEmail = (TextView) rootView.findViewById(R.id.show_email);
        mAddress= (TextView) rootView.findViewById(R.id.show_address);
        return rootView;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
