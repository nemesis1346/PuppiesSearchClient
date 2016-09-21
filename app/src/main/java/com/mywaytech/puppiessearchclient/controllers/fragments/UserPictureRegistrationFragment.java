package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mywaytech.puppiessearchclient.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Marco on 21/9/2016.
 */
public class UserPictureRegistrationFragment extends Fragment{
    private Button mBtnBack;
    private Button mBtnForward;
    private CircleImageView mUserPicture;
    private FloatingActionButton mFloatingActionButton;

    public static UserPictureRegistrationFragment newInstance(){
        Bundle args =new Bundle();
        UserPictureRegistrationFragment fragment = new UserPictureRegistrationFragment();
        fragment.setArguments(args);
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_picture_registration,container, false);
        mUserPicture = (CircleImageView) rootView.findViewById(R.id.image_userPicture_container);
        mUserPicture.setImageResource(R.drawable.ic_user_picture);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.icon_add_user_picture);
        mFloatingActionButton.setOnClickListener(mBtnActionButton);
        mBtnBack = (Button) rootView.findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(mBtnbackListener);
        mBtnForward = (Button) rootView.findViewById(R.id.btn_foward);
        mBtnForward.setOnClickListener(mBtnNextListener);

        return rootView;
    }

    private View.OnClickListener mBtnNextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener mBtnbackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener mBtnActionButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
