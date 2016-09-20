package com.mywaytech.puppiessearchclient.controllers.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Created by Marco on 20/9/2016.
 */
public class AlertDialogFragment extends DialogFragment {
    private static final String ARG_TITLE = "arg_title";
    private static final String ARG_RESOURCE_MESSAGE = "arg_resource_message";
    private static final String ARG_STRING_MESSAGE = "arg_string_message";
    private static final String ARG_POSITIVE_TEXT = "arg_positive_text";
    private static final String ARG_NEGATIVE_TEXT = "arg_negative_text";
    private static final String ARG_NEUTRAL_TEXT = "arg_neutral_text";
    private static final String ARG_IS_CANCELABLE = "arg_is_cancelable";

    private DialogInterface.OnClickListener mPositiveListener;
    private DialogInterface.OnClickListener mNeutralListener;
    private DialogInterface.OnClickListener mNegativeListener;
    private View mCustomView;
    private int mStyle;

    public static AlertDialogFragment newInstance(int title,int resourceMessage, int positiveText, int negativeText, int neutralText, boolean isCancelable, String stringMessage) {
        Bundle args = new Bundle();
        args.putInt(ARG_TITLE,title);
        args.putInt(ARG_RESOURCE_MESSAGE,resourceMessage);
        args.putInt(ARG_POSITIVE_TEXT,positiveText);
        args.putInt(ARG_NEGATIVE_TEXT,negativeText);
        args.putInt(ARG_NEUTRAL_TEXT,neutralText);
        args.putString(ARG_STRING_MESSAGE,stringMessage);
        args.putBoolean(ARG_IS_CANCELABLE,isCancelable);
        AlertDialogFragment fragment = new AlertDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt(ARG_TITLE);
        int resourceMessage = getArguments().getInt(ARG_RESOURCE_MESSAGE);
        String stringMessage = getArguments().getString(ARG_STRING_MESSAGE);
        int positiveText = getArguments().getInt(ARG_POSITIVE_TEXT);
        int negativeText = getArguments().getInt(ARG_NEGATIVE_TEXT);
        int neutralText = getArguments().getInt(ARG_NEUTRAL_TEXT);
        boolean isCancelable = getArguments().getBoolean(ARG_IS_CANCELABLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),mStyle);

        if(title != 0){
            builder.setTitle(title);
        }
        if(stringMessage != null){
            builder.setMessage(stringMessage);
        }
        if(resourceMessage != 0){
            builder.setMessage(resourceMessage);
        }
        if(positiveText != 0){
            builder.setPositiveButton(positiveText,mPositiveListener);
        }
        if(negativeText != 0){
            builder.setNegativeButton(negativeText,mNegativeListener);
        }
        if(neutralText != 0){
            builder.setNeutralButton(neutralText,mNeutralListener);
        }
        if (mCustomView != null) {
            builder.setView(mCustomView);
        }
        builder.setCancelable(isCancelable);
        return builder.create();
    }

    public void setPositiveListener(DialogInterface.OnClickListener positiveListener) {
        mPositiveListener = positiveListener;
    }

    public void setNeutralListener(DialogInterface.OnClickListener neutralListener) {
        mNeutralListener = neutralListener;
    }

    public void setNegativeListener(DialogInterface.OnClickListener negativeListener) {
        mNegativeListener = negativeListener;
    }

    public void setCustomView(View customView){
        mCustomView = customView;
    }

    public void setStyle(@StyleRes int style){
        mStyle = style;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPositiveListener = null;
        mNeutralListener = null;
        mNegativeListener = null;
        mCustomView = null;
    }
}
