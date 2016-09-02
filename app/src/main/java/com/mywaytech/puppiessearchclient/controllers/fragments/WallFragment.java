package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.reflect.TypeToken;
import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.adapters.WallAdapter;
import com.mywaytech.puppiessearchclient.controllers.NewPetActivity;
import com.mywaytech.puppiessearchclient.controllers.SearchDialog;
import com.mywaytech.puppiessearchclient.models.UserPetObject;
import com.mywaytech.puppiessearchclient.services.FireBaseHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marco on 4/13/2016.
 */
public class WallFragment extends Fragment {

    private static final String ARG_POSITION = "ARG POSITION";
    private static final String ARG_VALUE = "ARG VALUE";

    private int mValue;
    private FloatingActionButton btn_add_dog;
    private RecyclerView mListView;
    private WallAdapter wallAdapter;
    private List<UserPetObject> pet_list;
    private FireBaseHandler mFireBaseHandler;
    private DatabaseReference mDatabaseReference;

    private ProgressBar mProgressBar;
    private Button mRetryBtn;
    private TextView mProgressTextInfo;
    private ImageView mProgressErrorImg;


    private static final int PET_REQUEST = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mValue = getArguments().getInt(ARG_VALUE);
        pet_list = new ArrayList<>();
        mFireBaseHandler = FireBaseHandler.getInstance(getContext());
        mDatabaseReference = mFireBaseHandler.getFirebaseDatabaseReference().child(FireBaseHandler.OBJECT_PET_NAME);
        mDatabaseReference.addValueEventListener(showFireBaseListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wall, container, false);

        //here must come the processing

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        mRetryBtn = (Button) rootView.findViewById(R.id.btn_retry);
        mProgressTextInfo = (TextView) rootView.findViewById(R.id.text_progress_info);
        mProgressErrorImg = (ImageView) rootView.findViewById(R.id.img_error_icon);
        
        wallAdapter = new WallAdapter(getContext(), new ArrayList<UserPetObject>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        mListView = (RecyclerView) rootView.findViewById(R.id.item_list_wall);
        mListView.setLayoutManager(linearLayoutManager);

        btn_add_dog = (FloatingActionButton) rootView.findViewById(R.id.fab_add_dog_wall);
        btn_add_dog.setOnClickListener(addListener);
        mListView.setAdapter(wallAdapter);
        wallAdapter.registerAdapterDataObserver(adapterOnChangeData);
        showProgress();
        return rootView;
    }

    public View.OnClickListener addListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), NewPetActivity.class);
            int pet_activity_value = 1;
            intent.putExtra(NewPetActivity.FRAGMENT_VALUE, pet_activity_value);
            startActivityForResult(intent, PET_REQUEST);
        }
    };

    public static WallFragment newInstance(int position, int value) {
        WallFragment fragment = new WallFragment();
        Bundle arg = new Bundle();
        arg.putInt(ARG_VALUE, value);
        arg.putInt(ARG_POSITION, position);
        fragment.setArguments(arg);
        return fragment;
    }

    public RecyclerView.AdapterDataObserver adapterOnChangeData = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            hideProgress();
        }
    };

    private ValueEventListener showFireBaseListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            pet_list = new ArrayList<>();
            wallAdapter.setListItems(pet_list);
            showProgress();
            if (dataSnapshot != null) {
                for (DataSnapshot objectSnapshot : dataSnapshot.getChildren()) {
                    UserPetObject object = objectSnapshot.getValue(UserPetObject.class);
                    pet_list.add(object);
                }
                wallAdapter.setListItems(pet_list);
            } else {
                showError(R.string.error_no_results_found);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            showErrorRetry();
        }
    };


    @Override
    public void onPause() {
        super.onPause();
        hideProgress();
    }

    private void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressTextInfo.setVisibility(View.VISIBLE);
        mProgressErrorImg.setVisibility(View.GONE);
        mRetryBtn.setVisibility(View.GONE);
        mProgressTextInfo.setText(R.string.pet_loading_message);
    }

    private void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
        mProgressTextInfo.setVisibility(View.GONE);
        mProgressErrorImg.setVisibility(View.GONE);
        mRetryBtn.setVisibility(View.GONE);
    }

    public void showError(@StringRes int stringId) {
        mProgressBar.setVisibility(View.GONE);
        mProgressTextInfo.setVisibility(View.VISIBLE);
        mProgressErrorImg.setVisibility(View.VISIBLE);
        mRetryBtn.setVisibility(View.GONE);
        mProgressTextInfo.setText(stringId);
    }

    private void showErrorRetry() {
        mProgressBar.setVisibility(View.GONE);
        mProgressTextInfo.setVisibility(View.GONE);
        mProgressErrorImg.setVisibility(View.GONE);
        mRetryBtn.setVisibility(View.VISIBLE);
    }

}
