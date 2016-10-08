package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mywaytech.puppiessearchclient.R;

/**
 * Created by nemesis1346 on 7/10/2016.
 */
public class AboutFragment extends Fragment {

    private TextView mCompanyName;
    private TextView mCompanyEmail;
    private TextView mCompanyPhone;
    private ImageView mCompanyImage;
    private TextView mCompanyLink;

    public static AboutFragment newInstance(){
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
        View rootView = inflater.inflate(R.layout.fragment_about,container, false);
        mCompanyName = (TextView)rootView.findViewById(R.id.txt_company_name);
        mCompanyImage = (ImageView) rootView.findViewById(R.id.image_company);
        mCompanyPhone = (TextView) rootView.findViewById(R.id.txt_company_phone);
        mCompanyEmail = (TextView) rootView.findViewById(R.id.txt_company_email);
        mCompanyLink = (TextView) rootView.findViewById(R.id.txt_company_link);

        mCompanyImage.setImageResource(R.mipmap.ic_company_logo);
        mCompanyName.setText(getResources().getString(R.string.txt_company_name));
        mCompanyEmail.setText(getResources().getString(R.string.txt_company_email));
        mCompanyLink.setText(getResources().getString(R.string.txt_company_link));
        mCompanyPhone.setText(getResources().getString(R.string.txt_company_phone));

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
