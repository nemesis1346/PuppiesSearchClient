package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.mywaytech.puppiessearchclient.utils.AlertDialogUtils;
import com.mywaytech.puppiessearchclient.utils.PhotoUtils;

import java.io.File;
import java.io.Serializable;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Marco on 21/9/2016.
 */
public class UserPictureRegistrationFragment extends RegistrationBaseFragment {

    private static final int CAMERA_REQUEST = 0;

    public UserPictureImageCallback mUserPictureImageCallback;

    private Bitmap mPhoto;
    private File mFile;
    private String mFinalPath = "";

    private Button mBtnBack;
    private Button mBtnForward;
    private CircleImageView mUserPicture;
    private FloatingActionButton mFloatingActionButton;
    private NewUserObject mNewUserObject;

    public static final String ARG_NEW_USER_OBJECT = "arg_new_user_object";


    public static UserPictureRegistrationFragment newInstance(NewUserObject userObject) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_NEW_USER_OBJECT, userObject);
        UserPictureRegistrationFragment fragment = new UserPictureRegistrationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewUserObject = (NewUserObject) getArguments().getSerializable(ARG_NEW_USER_OBJECT);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_picture_registration, container, false);
        mUserPicture = (CircleImageView) rootView.findViewById(R.id.image_userPicture_container);
        mUserPicture.setImageResource(R.drawable.ic_user_picture);
        mUserPicture.setOnClickListener(mBtnActionButton);
        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.icon_add_user_picture);
        mFloatingActionButton.setOnClickListener(mBtnActionButton);
        return rootView;
    }


    private View.OnClickListener mBtnActionButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mUserPictureImageCallback = (UserPictureImageCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mUserPictureImageCallback = null;
    }

    @Override
    public boolean isFormReady() {
        if (mPhoto != null) {
            String mUserImagePath = "userPicture/user" + mNewUserObject.getmEmail() + ".jpg";
            mNewUserObject.setUserImagePath(mUserImagePath);
            mUserPictureImageCallback.userPictureImageResult(mPhoto);
            return true;
        } else {
            new AlertDialogUtils.Builder(getContext())
                    .setResourceMessage(R.string.user_picture_lack)
                    .setPositiveText(R.string.btn_ok)
                    .show();
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            //this is the global variable of the photo
            mPhoto = (Bitmap) data.getExtras().get("data");

            if (mPhoto != null) {
                mUserPicture.setImageBitmap(mPhoto);
                mUserPicture.setVisibility(View.VISIBLE);
            }
        }

    }

    public interface UserPictureImageCallback {
        void userPictureImageResult(Bitmap userPicture);
    }
}
