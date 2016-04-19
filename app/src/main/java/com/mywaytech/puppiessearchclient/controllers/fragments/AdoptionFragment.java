package com.mywaytech.puppiessearchclient.controllers.fragments;

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

        imagArray_10_adoption = new int[]{
                R.drawable.pet17,
                R.drawable.pet18,
                R.drawable.pet19,
                R.drawable.pet20,
                R.drawable.pet21,
                R.drawable.pet16,
        };
        mUser_10_adoption = getResources().getStringArray(R.array.users_10_adoption);
        mAddress_10_adoption = getResources().getStringArray(R.array.address_10_adoption);
        mComment_10_adoption = getResources().getStringArray(R.array.comment_10_adoption);

        imagArray_20_adoption = new int[]{
                R.drawable.pet17,
                R.drawable.pet18,
                R.drawable.pet19,
                R.drawable.pet20,
                R.drawable.pet21,
                R.drawable.pet16,
                R.drawable.pet15,
                R.drawable.pet14,
                R.drawable.pet13,
                R.drawable.pet12,
                R.drawable.pet11,
                R.drawable.pet10,
        };

        mUser_20_adoption = getResources().getStringArray(R.array.users_20_adoption);
        mAddress_20_adoption = getResources().getStringArray(R.array.address_20_adoption);
        mComment_20_adoption = getResources().getStringArray(R.array.comment_20_adoption);

        imagArray_30_adoption = new int[]{
                R.drawable.pet17,
                R.drawable.pet18,
                R.drawable.pet19,
                R.drawable.pet20,
                R.drawable.pet21,
                R.drawable.pet16,
                R.drawable.pet15,
                R.drawable.pet14,
                R.drawable.pet13,
                R.drawable.pet12,
                R.drawable.pet11,
                R.drawable.pet10,
                R.drawable.pet9,
                R.drawable.pet8,
                R.drawable.pet7,
                R.drawable.pet6,
                R.drawable.pet2,
                R.drawable.pet1,
        };

        mUser_30_adoption = getResources().getStringArray(R.array.users_30_adoption);
        mAddress_30_adoption = getResources().getStringArray(R.array.address_30_adoption);
        mComment_30_adoption = getResources().getStringArray(R.array.comment_30_adoption);

        switch (mValue) {
            case ARRAY0:
                for (int i = 0; i < position_10.length; i++) {
                    object_list.add(new UserPetObject(mUser_10_adoption[position_10[i]], mAddress_10_adoption[position_10[i]], imagArray_10_adoption[position_10[i]], mComment_10_adoption[position_10[i]]));
                }
                break;
            case ARRAY1:
                for (int i = 0; i < position_20.length; i++) {
                    object_list.add(new UserPetObject(mUser_20_adoption[position_20[i]], mAddress_20_adoption[position_20[i]], imagArray_20_adoption[position_20[i]], mComment_20_adoption[position_20[i]]));
                }
                break;
            case ARRAY2:
                for (int i = 0; i < position_30.length; i++) {
                    object_list.add(new UserPetObject(mUser_30_adoption[position_30[i]], mAddress_30_adoption[position_30[i]], imagArray_30_adoption[position_30[i]], mComment_30_adoption[position_30[i]]));
                }
                break;
            default:
                for (int i = 0; i < position_10.length; i++) {
                    object_list.add(new UserPetObject(mUser_10_adoption[position_10[i]], mAddress_10_adoption[position_10[i]], imagArray_10_adoption[position_10[i]], mComment_10_adoption[position_10[i]]));
                }
                break;
        }

        wallAdapter = new WallAdapter(getContext(), object_list);
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
            Intent intent = new Intent(getContext(), NewPetActivity.class);
            startActivity(intent);
        }
    };

    public static AdoptionFragment newInstance(int position, int value) {
        AdoptionFragment fragment = new AdoptionFragment();
        Bundle arg = new Bundle();
        arg.putInt(ARG_VALUE, value);
        arg.putInt(ARG_POSITION, position);
        fragment.setArguments(arg);
        return fragment;
    }


}
