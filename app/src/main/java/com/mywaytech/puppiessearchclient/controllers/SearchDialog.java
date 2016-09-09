package com.mywaytech.puppiessearchclient.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.adapters.SearchSpinnerAdapter;
import com.mywaytech.puppiessearchclient.models.SearchRangeObject;

import java.util.ArrayList;

/**
 * Created by marco on 4/17/2016.
 */
public class SearchDialog extends DialogFragment {
    private SearchSpinnerAdapter searchSpinnerAdapter;
    private Spinner spinnerAdapter;
    private String[] mRangeArray;
    private int[] mValueArray;
    private Button accept;
    PassDataFragment mCallback;


    int input;
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
        accept= (Button) view.findViewById(R.id.btn_ok);
        accept.setOnClickListener(backToActivity);
        searchSpinnerAdapter = new SearchSpinnerAdapter(getContext(), R.layout.spinner_simple, object_list);

        spinnerAdapter.setAdapter(searchSpinnerAdapter);
        return view;
    }

    public View.OnClickListener backToActivity=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            input = searchSpinnerAdapter.getMovementValue(spinnerAdapter.getSelectedItemPosition());
            getDialog().dismiss();
            mCallback.backData(input);

        }
    };

    public interface PassDataFragment{
        public void backData(int value);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (PassDataFragment) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


}
