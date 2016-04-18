package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.content.Intent;
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

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.adapters.WallAdapter;
import com.mywaytech.puppiessearchclient.controllers.NewPetActivity;
import com.mywaytech.puppiessearchclient.models.UserPetObject;

import java.util.ArrayList;

/**
 * Created by Marco on 4/13/2016.
 */
public class WallFragment extends Fragment {

    private static final String ARG_POSITION = "ARG POSITION";
    private int mPosition;
    private FloatingActionButton btn_add_dog;
    private RecyclerView mListView;
    private WallAdapter wallAdapter;
    private ArrayList<UserPetObject> object_list;
    private int[] imagArray_raw;
    private String[] mUser_10;
    private String[] mAddress_10;
    private String[] mComment_adop_10;
    private String[] mComent_lost_10;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wall, container, false);

        imagArray_raw = new int[]{
                R.drawable.pet1,
                R.drawable.pet2,
                R.drawable.pet3,
                R.drawable.pet4,
                R.drawable.pet5,
                R.drawable.pet6,
                R.drawable.pet7,
                R.drawable.pet8,
                R.drawable.pet9,
                R.drawable.pet10,
                R.drawable.pet11,
                R.drawable.pet12,
                R.drawable.pet13,
                R.drawable.pet14,
                R.drawable.pet15,
                R.drawable.pet16,
                R.drawable.pet17,
                R.drawable.pet18,
                R.drawable.pet19,
                R.drawable.pet20,
                R.drawable.pet21,
        };

        object_list = new ArrayList<>();

        mUser_10 = getResources().getStringArray(R.array.users_10);
        mAddress_10 = getResources().getStringArray(R.array.address_10);
        mComment_adop_10 = getResources().getStringArray(R.array.comment_10_adoption);
        mComent_lost_10 = getResources().getStringArray(R.array.comment_10_lost);

        wallAdapter = new WallAdapter(getContext(), object_list);
        for (int i = 0; i < mUser_10.length; i++) {
            object_list.add(new UserPetObject(mUser_10[i], mAddress_10[i], imagArray_raw[i], mComent_lost_10[i]));
        }

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());


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
            startActivity(intent);
        }
    };


    public static WallFragment newInstance(int position) {
        WallFragment fragment = new WallFragment();
        Bundle arg = new Bundle();
        arg.putInt(ARG_POSITION, position);
        fragment.setArguments(arg);
        return fragment;
    }


}
