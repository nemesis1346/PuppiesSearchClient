package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Created by Marco on 21/9/2016.
 */
public class ProgressDialogFragment extends DialogFragment {

    private static final String ARG_TEXT = "arg_text";
    private int mStyle;
    private View mCustomView;

    public static ProgressDialogFragment newInstance(int text) {
        Bundle args = new Bundle();
        args.putInt(ARG_TEXT, text);
        ProgressDialogFragment fragment = new ProgressDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int text = getArguments().getInt(ARG_TEXT);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), mStyle);

        if (text != 0) {
            builder.setTitle(text);
        }
        if (mCustomView != null) {
            builder.setView(mCustomView);
        }
        return builder.create();

    }

    public void setStyle(@StyleRes int style) {
        mStyle = style;
    }

    public void setCustomView(View customView) {
        mCustomView = customView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCustomView = null;
    }
}
