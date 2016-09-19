package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.adapters.WallAdapter;
import com.mywaytech.puppiessearchclient.models.ReportObject;

import java.util.ArrayList;

/**
 * Created by m.maigua on 4/14/2016.
 */
public class AdoptionFragment extends Fragment {
    private static final String ARG_POSITION = "ARG POSITION";
    private static final String ARG_VALUE = "ARG VALUE";

    private RecyclerView mListView;
    private WallAdapter wallAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_adoption, container, false);

        wallAdapter = new WallAdapter(getContext(), new ArrayList<ReportObject>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        mListView = (RecyclerView) rootView.findViewById(R.id.item_list_adoption);
        mListView.setLayoutManager(linearLayoutManager);

        mListView.setAdapter(wallAdapter);


        return rootView;
    }



    public static AdoptionFragment newInstance(int position, int value) {
        AdoptionFragment fragment = new AdoptionFragment();
        Bundle arg = new Bundle();
        arg.putInt(ARG_VALUE, value);
        arg.putInt(ARG_POSITION, position);
        fragment.setArguments(arg);
        return fragment;
    }

}
