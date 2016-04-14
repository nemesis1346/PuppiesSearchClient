package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.app.AlertDialog;
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
 * Created by m.maigua on 4/14/2016.
 */
public class AdoptionFragment extends Fragment {
    private static final String ARG_POSITION = "ARG POSITION";
    private FloatingActionButton btn_add_dog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_adoption,container,false);
        btn_add_dog= (FloatingActionButton) rootView.findViewById(R.id.fab_add_dog_adoption);
        btn_add_dog.setOnClickListener(addListener_adoption);
        return rootView;
    }

    public View.OnClickListener addListener_adoption=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(getContext(), NewPetActivity.class);
            startActivity(intent);
        }
    };

    public static AdoptionFragment newInstance(int position){
        AdoptionFragment fragment=new AdoptionFragment();
        Bundle arg=new Bundle();
        arg.putInt(ARG_POSITION,position);
        fragment.setArguments(arg);
        return  fragment;
    }


}
