package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.models.NewUserObject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Marco on 21/9/2016.
 */
public class UserPictureRegistrationFragment extends RegistrationBaseFragment{
    private Button mBtnBack;
    private Button mBtnForward;
    private CircleImageView mUserPicture;
    private FloatingActionButton mFloatingActionButton;

    public static UserPictureRegistrationFragment newInstance(NewUserObject userObject){
        Bundle args =new Bundle();
        UserPictureRegistrationFragment fragment = new UserPictureRegistrationFragment();
        fragment.setArguments(args);
        return  fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_picture_registration,container, false);
        mUserPicture = (CircleImageView) rootView.findViewById(R.id.image_userPicture_container);
        mUserPicture.setImageResource(R.drawable.ic_user_picture);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.icon_add_user_picture);
        mFloatingActionButton.setOnClickListener(mBtnActionButton);

        return rootView;
    }


    private View.OnClickListener mBtnActionButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    public boolean isFormReady() {
        return true;
    }
}
