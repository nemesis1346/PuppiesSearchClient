package com.mywaytech.puppiessearchclient.controllers;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mywaytech.puppiessearchclient.R;

/**
 * Created by marco on 4/17/2016.
 */
public class SearchDialog extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NO_TITLE;
        int theme = android.R.style.Theme_Holo_Light_Dialog;

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
        return view;
    }
}
