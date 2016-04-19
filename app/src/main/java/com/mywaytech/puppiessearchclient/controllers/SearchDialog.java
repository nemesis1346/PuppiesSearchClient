package com.mywaytech.puppiessearchclient.controllers;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.adapters.SearchSpinnerAdapter;
import com.mywaytech.puppiessearchclient.models.SearchRangeObject;
import com.mywaytech.puppiessearchclient.models.UserPetObject;

import java.util.ArrayList;

/**
 * Created by marco on 4/17/2016.
 */
public class SearchDialog extends DialogFragment {
    private SearchSpinnerAdapter searchSpinnerAdapter;
    private Spinner spinnerAdapter;
    private String[] mRangeArray;
    private int[] mValueArray;

    private ArrayList<SearchRangeObject> object_list;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NO_TITLE;
        int theme = android.R.style.Theme_Holo_Light_Dialog;
        mRangeArray = new String[]{"10km", "20km", "30km"};
        mValueArray = new int[]{0, 1, 2};
        object_list = new ArrayList<>();
        for (int i = 0; i < mRangeArray.length; i++) {
            object_list.add(new SearchRangeObject(mRangeArray[i], mValueArray[i]));
        }
        setStyle(style, theme);
    }

    public static SearchDialog newInstance() {
        SearchDialog f = new SearchDialog();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_search_layout, container, false);
        spinnerAdapter = (Spinner) view.findViewById(R.id.main_spinner);
        searchSpinnerAdapter = new SearchSpinnerAdapter(getContext(), R.layout.spinner_search, object_list);
        spinnerAdapter.setAdapter(searchSpinnerAdapter);
        return view;
    }
}
