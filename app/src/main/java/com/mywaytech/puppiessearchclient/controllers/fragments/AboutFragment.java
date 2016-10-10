package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.utils.Utils;

/**
 * Created by nemesis1346 on 7/10/2016.
 */
public class AboutFragment extends Fragment {

    private TextView mCompanyName;
    private TextView mCompanyEmail;
    private TextView mCompanyPhone;
    private ImageView mCompanyImage;
    private TextView mCompanyLink;

    public static AboutFragment newInstance() {
        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.main_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.aboutTitle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCompanyName = (TextView) rootView.findViewById(R.id.txt_company_name);
        mCompanyImage = (ImageView) rootView.findViewById(R.id.image_company);
        mCompanyPhone = (TextView) rootView.findViewById(R.id.txt_company_phone);
        mCompanyEmail = (TextView) rootView.findViewById(R.id.txt_company_email);
        mCompanyLink = (TextView) rootView.findViewById(R.id.txt_company_link);

        mCompanyImage.setImageResource(R.mipmap.ic_company_logo);
        mCompanyName.setText(getResources().getString(R.string.txt_company_name));
        mCompanyEmail.setText(getResources().getString(R.string.txt_company_email));
        mCompanyLink.setText(getResources().getString(R.string.txt_company_link));
        mCompanyPhone.setText(getResources().getString(R.string.txt_company_phone));

        mCompanyEmail.setPaintFlags(mCompanyEmail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mCompanyLink.setPaintFlags(mCompanyLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mCompanyPhone.setPaintFlags(mCompanyPhone.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mCompanyLink.setLinkTextColor(Color.BLUE);

        mCompanyImage.setOnClickListener(mCompanyLinkListener);
        mCompanyLink.setOnClickListener(mCompanyLinkListener);
        mCompanyEmail.setOnClickListener(mCompanyEmailListener);
        mCompanyPhone.setOnClickListener(mCompanyPhoneListener);

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

    private View.OnClickListener mCompanyEmailListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Utils.openEmail(v.getContext(),new String[]{mCompanyEmail.getText().toString()},null, null);
        }
    };

    private View.OnClickListener mCompanyLinkListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Utils.openBrowser(v.getContext(),mCompanyLink.getText().toString());
        }
    };

    private View.OnClickListener mCompanyPhoneListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Utils.openPhone(v.getContext(),mCompanyPhone.getText().toString());
        }
    };
}
