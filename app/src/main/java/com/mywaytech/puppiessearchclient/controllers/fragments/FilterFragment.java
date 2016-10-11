package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mywaytech.puppiessearchclient.R;

/**
 * Created by nemesis1346 on 10/11/2016.
 */
public class FilterFragment extends DialogFragment  {

    private FilterDialogCallbacks mFilterDialogCallbacks;
    
    public static FilterFragment newInstance(){
        FilterFragment fragment = new FilterFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFilterDialogCallbacks = (FilterDialogCallbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFilterDialogCallbacks=null;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    public interface FilterDialogCallbacks{
        void filterResult(String type);
    }

}
