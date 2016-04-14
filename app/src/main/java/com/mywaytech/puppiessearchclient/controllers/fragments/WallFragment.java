package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.NewPetActivity;

/**
 * Created by Marco on 4/13/2016.
 */
public class WallFragment extends Fragment{

    private static final String ARG_POSITION = "ARG POSITION";
    private int mPosition;
    private FloatingActionButton btn_add_dog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_wall,container,false);
        btn_add_dog= (FloatingActionButton) rootView.findViewById(R.id.fab_add_dog_wall);
        btn_add_dog.setOnClickListener(addListener);
        return rootView;
    }

    public View.OnClickListener addListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(getContext(), NewPetActivity.class);
            startActivity(intent);
        }
    };


    public static WallFragment newInstance(int position){
        WallFragment fragment=new WallFragment();
        Bundle arg=new Bundle();
        arg.putInt(ARG_POSITION,position);
        fragment.setArguments(arg);
        return  fragment;
    }



}
