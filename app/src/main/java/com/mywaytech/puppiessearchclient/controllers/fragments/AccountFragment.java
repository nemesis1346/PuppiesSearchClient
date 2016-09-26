package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.domain.UserSessionManager;
import com.mywaytech.puppiessearchclient.models.NewUserObject;
import com.mywaytech.puppiessearchclient.services.FireBaseHandler;

import java.io.ByteArrayInputStream;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Marco on 9/19/2016.
 */
public class AccountFragment extends Fragment {

    public static final String EXTRA_USERDATA_IN = "com.mywaytech.puppiessearchclient.extras.extra_userdata_in";
    public static final String EXTRA_EMAIL = "com.mywaytech.puppiessearchclient.extras.extra_email";

    private NewUserObject mNewUserObject;

    private TextView mName;
    private TextView mEmail;
    private TextView mAddress;
    private CircleImageView mUserPicture;

    private String authEmail;

    private String[] retrieve;

    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;

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
        mUserPicture = (CircleImageView) rootView.findViewById(R.id.account_image);

        FirebaseUser user=FireBaseHandler.getInstance(getContext()).getFirebaseAuth().getCurrentUser();

        mDatabaseReference= FireBaseHandler.getInstance(getContext()).getFirebaseDatabaseReference();
        mDatabaseReference.child(FireBaseHandler.OBJECT_USERS_NAME)
                .child(user.getUid())
                .addListenerForSingleValueEvent(mAccountFirebaseListener);

        return rootView;
    }



    private ValueEventListener mAccountFirebaseListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            mNewUserObject = dataSnapshot.getValue(NewUserObject.class);
            mName.setText(mNewUserObject.getmName());
            mEmail.setText(mNewUserObject.getmEmail());
            mAddress.setText(mNewUserObject.getAddress());

            StorageReference mFirebaseStorageReference = FireBaseHandler.getInstance(getContext())
                    .getmStorageRef().child(mNewUserObject.getUserImagePath());
            final long ONE_MEGABYTE = 1024 * 1024 * 2;

            mFirebaseStorageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    // Data for "images/island.jpg" is returns, use this as needed
                    Log.d("result bytes: ",""+ bytes);
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    mUserPicture.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
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

}
