package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.app.Activity;
import android.app.AlertDialog;
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

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.adapters.WallAdapter;
import com.mywaytech.puppiessearchclient.controllers.NewPetActivity;
import com.mywaytech.puppiessearchclient.models.UserPetObject;

import java.util.ArrayList;

/**
 * Created by m.maigua on 4/14/2016.
 */
public class AdoptionFragment extends Fragment {
    private static final String ARG_POSITION = "ARG POSITION";
    private static final String ARG_VALUE = "ARG VALUE";
    public static final int ARRAY0 = 0;
    public static final int ARRAY1 = 1;
    public static final int ARRAY2 = 2;
    private FloatingActionButton btn_add_dog;
    private RecyclerView mListView;
    private WallAdapter wallAdapter;
    private ArrayList<UserPetObject> object_list;

    private static final int PET_REQUEST = 0;

    private int[] imagArray_10_adoption;
    private String[] mUser_10_adoption;
    private String[] mAddress_10_adoption;
    private String[] mComment_10_adoption;

    private int[] imagArray_20_adoption;
    private String[] mUser_20_adoption;
    private String[] mAddress_20_adoption;
    private String[] mComment_20_adoption;

    private int[] imagArray_30_adoption;
    private String[] mUser_30_adoption;
    private String[] mAddress_30_adoption;
    private String[] mComment_30_adoption;

    private int[] position_10 = new int[]{0, 1, 2, 3, 4, 5};
    private int[] position_20 = new int[]{7, 1, 9, 3, 11, 5, 8, 2, 10, 4, 6, 0, 5};
    private int[] position_30 = new int[]{14, 8, 2, 15, 9, 3, 16, 10, 4, 17, 11, 5, 12, 6, 13, 7, 1, 0};

    private int mValue;
    private UserPetObject newadoptPet;
    public static final String EXTRA_ADOPT_PET = "com.mywaytech.puppiessearchclient.extras.extra_adopt_pet";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mValue = getArguments().getInt(ARG_VALUE);
        object_list = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_adoption, container, false);


        wallAdapter = new WallAdapter(getContext(), new ArrayList<UserPetObject>());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        mListView = (RecyclerView) rootView.findViewById(R.id.item_list_adoption);
        mListView.setLayoutManager(linearLayoutManager);

        mListView.setAdapter(wallAdapter);

        btn_add_dog = (FloatingActionButton) rootView.findViewById(R.id.fab_add_dog_adoption);
        btn_add_dog.setOnClickListener(addListener_adoption);
        return rootView;
    }

    public View.OnClickListener addListener_adoption = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int adoption_activity_value=2;
            Intent intent = new Intent(getContext(), NewPetActivity.class);
            intent.putExtra(NewPetActivity.FRAGMENT_VALUE,adoption_activity_value);
            startActivityForResult(intent, PET_REQUEST);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode ==PET_REQUEST && resultCode == Activity.RESULT_OK) {
            newadoptPet = (UserPetObject) data.getSerializableExtra(EXTRA_ADOPT_PET);
            wallAdapter.updateData(newadoptPet);
        }
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
