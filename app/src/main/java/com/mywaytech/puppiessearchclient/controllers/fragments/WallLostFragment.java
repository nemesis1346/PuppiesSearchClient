package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.adapters.WallAdapter;
import com.mywaytech.puppiessearchclient.models.ReportObject;
import com.mywaytech.puppiessearchclient.services.FireBaseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marco on 4/13/2016.
 */
public class WallLostFragment extends Fragment {

    private static final String ARG_POSITION = "ARG POSITION";
    private static final String ARG_VALUE = "ARG VALUE";

    private int mValue;

    private RecyclerView mListView;
    private WallAdapter wallAdapter;
    private List<ReportObject> pet_list;

    private ProgressBar mProgressBar;
    private Button mRetryBtn;
    private TextView mProgressTextInfo;
    private ImageView mProgressErrorImg;


    public static WallLostFragment newInstance(int position, int value) {
        WallLostFragment fragment = new WallLostFragment();
        Bundle arg = new Bundle();
        arg.putInt(ARG_VALUE, value);
        arg.putInt(ARG_POSITION, position);
        fragment.setArguments(arg);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mValue = getArguments().getInt(ARG_VALUE);
        pet_list = new ArrayList<>();
        FireBaseHandler.getInstance(getContext()).getLostPetFirebaseDatabaseReference()
                .addValueEventListener(showFireBaseListener);
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

        wallAdapter = new WallAdapter(getContext(), new ArrayList<ReportObject>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        mListView = (RecyclerView) rootView.findViewById(R.id.item_list_wall);
        mListView.setLayoutManager(linearLayoutManager);

        mListView.setAdapter(wallAdapter);
        wallAdapter.registerAdapterDataObserver(adapterOnChangeData);
        showProgress();
        return rootView;
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
                    ReportObject object = objectSnapshot.getValue(ReportObject.class);
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
