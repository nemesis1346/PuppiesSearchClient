package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.RegistrationActivity;
import com.mywaytech.puppiessearchclient.models.NewUserModel;
import com.mywaytech.puppiessearchclient.dataaccess.FireBaseHandler;

import java.io.ByteArrayInputStream;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Marco on 9/19/2016.
 */
public class AccountFragment extends Fragment {

    private NewUserModel mNewUserObject;

    private TextView mName;
    private TextView mEmail;
    private TextView mAddress;
    private CircleImageView mUserPicture;
    private Button mEditBtn;

    private ProgressBar mProgressBar;
    private TextView mProgressTextInfo;
    private ImageView mProgressErrorImg;

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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.myAccountTitle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        mProgressTextInfo = (TextView) rootView.findViewById(R.id.text_progress_info);
        mProgressErrorImg = (ImageView) rootView.findViewById(R.id.img_error_icon);

        mName = (TextView) rootView.findViewById(R.id.show_user_name);
        mEmail = (TextView) rootView.findViewById(R.id.show_email);
        mAddress= (TextView) rootView.findViewById(R.id.show_address);
        mUserPicture = (CircleImageView) rootView.findViewById(R.id.account_image);
        mEditBtn = (Button) rootView.findViewById(R.id.btn_edit);

        mEditBtn.setOnClickListener(mEditBtnListener);

        showProgress();
        FireBaseHandler.getInstance(getContext()).getUserObjectFirebaseDatabaseReference()
                .addListenerForSingleValueEvent(mAccountFirebaseListener);

        return rootView;
    }

    private ValueEventListener mAccountFirebaseListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mNewUserObject = dataSnapshot.getValue(NewUserModel.class);
            mName.setText(mNewUserObject.getmName());
            mEmail.setText(mNewUserObject.getmEmail());
            mAddress.setText(mNewUserObject.getAddress());

            StorageReference mFirebaseStorageReference = FireBaseHandler.getInstance(getContext())
                    .getmStorageRef().child(mNewUserObject.getUserImagePath());
            final long ONE_MEGABYTE = 1024 * 1024 * 2;

            mFirebaseStorageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    mUserPicture.setImageBitmap(bitmap);
                    hideProgress();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    showError(R.string.error_no_results_found);
                }
            });
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

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

    private void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressTextInfo.setVisibility(View.VISIBLE);
        mProgressTextInfo.setText(R.string.pet_loading_user_picture);
    }

    private void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
        mProgressTextInfo.setVisibility(View.GONE);
    }

    private void showError(@StringRes int stringId) {
        mProgressBar.setVisibility(View.GONE);
        mProgressTextInfo.setVisibility(View.VISIBLE);
        mProgressErrorImg.setVisibility(View.VISIBLE);
        mProgressTextInfo.setText(stringId);
    }

    private View.OnClickListener mEditBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = RegistrationActivity.newIntent(getContext());
            intent.putExtra(RegistrationActivity.EXTRA_IS_EDITING,true);
            startActivity(intent);
        }
    };

}
