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

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.adapters.WallAdapter;
import com.mywaytech.puppiessearchclient.controllers.NewPetActivity;
import com.mywaytech.puppiessearchclient.controllers.SearchDialog;
import com.mywaytech.puppiessearchclient.models.UserPetObject;

import java.util.ArrayList;

/**
 * Created by Marco on 4/13/2016.
 */
public class WallFragment extends Fragment {

    private static final String ARG_POSITION = "ARG POSITION";
    private static final String ARG_VALUE = "ARG VALUE";
    public static final int ARRAY0 = 0;
    public static final int ARRAY1 = 1;
    public static final int ARRAY2 = 2;
    private int mPosition;
    private int mValue;
    private FloatingActionButton btn_add_dog;
    private RecyclerView mListView;
    private WallAdapter wallAdapter;
    private ArrayList<UserPetObject> object_list;

    private int[] imagArray_10_lost;
    private String[] mUser_10_lost;
    private String[] mAddress_10_lost;
    private String[] mComent_10_lost;

    private int[] imagArray_20_lost;
    private String[] mUser_20_lost;
    private String[] mAddress_20_lost;
    private String[] mComent_20_lost;
    private static final int PET_REQUEST = 0;

    private int[] imagArray_30_lost;
    private String[] mUser_30_lost;
    private String[] mAddress_30_lost;
    private String[] mComent_30_lost;

    private UserPetObject updatedPet;
    public static final String EXTRA_UPDATE_PET = "com.mywaytech.puppiessearchclient.extras.extra_update_pet";

    private int[] position_10 = new int[]{0, 1, 2, 3, 4, 5};
    private int[] position_20 = new int[]{7, 1, 9, 3, 11, 5, 8, 2, 10, 4, 6, 0, 5};
    private int[] position_30 = new int[]{14, 8, 2, 15, 9, 3, 16, 10, 4, 17, 11, 5, 12, 6, 13, 7, 1, 0};


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mValue = getArguments().getInt(ARG_VALUE);
        object_list = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wall, container, false);

        imagArray_10_lost = new int[]{
                R.drawable.pet1,
                R.drawable.pet2,
                R.drawable.pet3,
                R.drawable.pet4,
                R.drawable.pet5,
                R.drawable.pet6,
        };

        mUser_10_lost = getResources().getStringArray(R.array.users_10_lost);
        mAddress_10_lost = getResources().getStringArray(R.array.address_10_lost);
        mComent_10_lost = getResources().getStringArray(R.array.comment_10_lost);

        imagArray_20_lost = new int[]{
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
        };

        mUser_20_lost = getResources().getStringArray(R.array.users_20_lost);
        mAddress_20_lost = getResources().getStringArray(R.array.address_20_lost);
        mComent_20_lost = getResources().getStringArray(R.array.comment_20_lost);

        imagArray_30_lost = new int[]{
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
        };

        mUser_30_lost = getResources().getStringArray(R.array.users_30_lost);
        mAddress_30_lost = getResources().getStringArray(R.array.address_30_lost);
        mComent_30_lost = getResources().getStringArray(R.array.comment_30_lost);


        switch (mValue) {
            case ARRAY0:
                for (int i = 0; i < position_10.length; i++) {
                    object_list.add(new UserPetObject(mUser_10_lost[position_10[i]], mAddress_10_lost[position_10[i]], imagArray_10_lost[position_10[i]], mComent_10_lost[position_10[i]]));
                }
                break;
            case ARRAY1:
                for (int i = 0; i < position_20.length; i++) {
                    object_list.add(new UserPetObject(mUser_20_lost[position_20[i]], mAddress_20_lost[position_20[i]], imagArray_20_lost[position_20[i]], mComent_20_lost[position_20[i]]));
                }
                break;
            case ARRAY2:
                for (int i = 0; i < position_30.length; i++) {
                    object_list.add(new UserPetObject(mUser_30_lost[position_30[i]], mAddress_30_lost[position_30[i]], imagArray_30_lost[position_30[i]], mComent_30_lost[position_30[i]]));
                }
                break;
            default:
                for (int i = 0; i < position_10.length; i++) {
                    object_list.add(new UserPetObject(mUser_10_lost[position_10[i]], mAddress_10_lost[position_10[i]], imagArray_10_lost[position_10[i]], mComent_10_lost[position_10[i]]));
                }
                break;
        }

//here must come the processing
     //   onActivityResult(PET_REQUEST,Activity.RESULT_OK,this);

        wallAdapter = new WallAdapter(getContext(), object_list);
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
            int pet_activity_value=1;
            intent.putExtra(NewPetActivity.FRAGMENT_VALUE,pet_activity_value);
            startActivityForResult(intent,PET_REQUEST);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PET_REQUEST && resultCode == Activity.RESULT_OK) {
          updatedPet= (UserPetObject) data.getSerializableExtra(EXTRA_UPDATE_PET);
            wallAdapter.updateData(updatedPet);
        }
    }

    public static WallFragment newInstance(int position, int value) {
        WallFragment fragment = new WallFragment();
        Bundle arg = new Bundle();
        arg.putInt(ARG_VALUE, value);
        arg.putInt(ARG_POSITION, position);
        fragment.setArguments(arg);
        return fragment;
    }


}
