package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.app.Activity;
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
import com.mywaytech.puppiessearchclient.domain.UserSessionManager;
import com.mywaytech.puppiessearchclient.models.NewUserModel;
import com.mywaytech.puppiessearchclient.dataaccess.FireBaseHandler;

import java.io.ByteArrayInputStream;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Marco on 9/19/2016.
 */
public class AccountFragment extends Fragment {


    public static final int CODE_EDITING = 1234;
    public static final String EXTRA_EDIT_USER_OBJECT = "extra_edit_user_object";
    private NewUserModel mNewUserObject;

    private TextView mName;
    private TextView mEmail;
    private TextView mAddress;
    private CircleImageView mUserPicture;
    private Button mEditBtn;

    private ProgressBar mProgressBar;
    private TextView mProgressTextInfo;
    private ImageView mProgressErrorImg;

    public static AccountFragment newInstance() {
        Bundle args = new Bundle();
        AccountFragment fragment = new AccountFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.main_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.myAccountTitle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mName = (TextView) rootView.findViewById(R.id.show_user_name);
        mEmail = (TextView) rootView.findViewById(R.id.show_email);
        mAddress = (TextView) rootView.findViewById(R.id.show_address);
        mUserPicture = (CircleImageView) rootView.findViewById(R.id.account_image);
        mEditBtn = (Button) rootView.findViewById(R.id.btn_edit);

        mEditBtn.setOnClickListener(mEditBtnListener);
mNewUserObject = UserSessionManager.getInstance(getContext()).getLocalUser();

        setupUI(mNewUserObject);

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

    private View.OnClickListener mEditBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = RegistrationActivity.newIntent(getContext());
            intent.putExtra(RegistrationActivity.EXTRA_IS_EDITING, true);
            startActivityForResult(intent, CODE_EDITING);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_EDITING && resultCode == Activity.RESULT_OK) {
            NewUserModel resultado = (NewUserModel) data.getExtras().getSerializable(EXTRA_EDIT_USER_OBJECT);
            setupUI(resultado);
        }
    }

    private void setupUI(NewUserModel newUserModel) {
        mName.setText(newUserModel.getmName());
        mEmail.setText(newUserModel.getmEmail());
        mAddress.setText(newUserModel.getmAddress());

        mUserPicture.setImageBitmap(UserSessionManager.getInstance(getContext()).getUserImage());
    }

}
