package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

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

        wallAdapter = new WallAdapter(getContext(), new ArrayList<UserPetObject>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        mListView = (RecyclerView) rootView.findViewById(R.id.item_list_wall);
        mListView.setLayoutManager(linearLayoutManager);

        btn_add_dog = (FloatingActionButton) rootView.findViewById(R.id.fab_add_dog_wall);
        btn_add_dog.setOnClickListener(addListener);
        mListView.setAdapter(wallAdapter);
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

    private ValueEventListener showFireBaseListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            pet_list = new ArrayList<>();

            for (DataSnapshot objectSnapshot : dataSnapshot.getChildren()) {
                UserPetObject object = objectSnapshot.getValue(UserPetObject.class);
                pet_list.add(object);
            }
            wallAdapter.setListItems(pet_list);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}
