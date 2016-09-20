package com.mywaytech.puppiessearchclient.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.fragments.AlertDialogFragment;

/**
 * Created by Marco on 20/9/2016.
 */
public class AlertDialogUtils {
    public static class Builder {

        public static final String DIALOG_TAG = "com.bayteq.digitalbankingmultiowner.controllers.fragments.alertdialogfragment";

        private Context mContext;
        private int mTitle;
        private int mResourceMessage;
        private String mStringMessage;
        private int mPositiveText;
        private int mNegativeText;
        private int mNeutralText;
        private boolean mIsCancelable;
        private DialogInterface.OnClickListener mPositiveListener;
        private DialogInterface.OnClickListener mNegativeListener;
        private DialogInterface.OnClickListener mNeutralListener;
        private View mCustomView;

        public Builder(Context context) {
            mContext = context;
        }

        @StringRes
        public Builder setTitle(int title) {
            mTitle = title;
            return this;
        }

        @StringRes
        public Builder setResourceMessage(int resourceMessage) {
            mResourceMessage = resourceMessage;
            return this;
        }

        public Builder setStringMessage(String stringMessage){
            mStringMessage = stringMessage;
            return this;
        }

        @StringRes
        public Builder setPositiveText(int positiveText) {
            mPositiveText = positiveText;
            return this;
        }

        @StringRes
        public Builder setNegativeText(int negativeText) {
            mNegativeText = negativeText;
            return this;
        }

        @StringRes
        public Builder setNeutralText(int neutralText) {
            mNeutralText = neutralText;
            return this;
        }

        public Builder setIsCancelable(boolean isCancelable) {
            mIsCancelable = isCancelable;
            return this;
        }

        public Builder setPositiveButtonListener(DialogInterface.OnClickListener positiveButtonListener) {
            mPositiveListener = positiveButtonListener;
            return this;
        }

        public Builder setNegativeButtonListener(DialogInterface.OnClickListener negativeButtonListener) {
            mNegativeListener = negativeButtonListener;
            return this;
        }

        public Builder setNeutralButtonListener(DialogInterface.OnClickListener neutralButtonListener) {
            mNeutralListener = neutralButtonListener;
            return this;
        }

        public Builder setCustomView(View customView){
            mCustomView = customView;
            return this;
        }

        @Nullable
        public AlertDialogFragment show(){
            AlertDialogFragment dialogFragment = null;
            try {
                FragmentTransaction ft = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();
                Fragment prev = ((AppCompatActivity) mContext).getSupportFragmentManager().findFragmentByTag(DIALOG_TAG);
                if (prev != null) {
                    ft.remove(prev);
                }

                dialogFragment = AlertDialogFragment.newInstance(mTitle, mResourceMessage, mPositiveText, mNegativeText, mNeutralText, mIsCancelable, mStringMessage);
                dialogFragment.setPositiveListener(mPositiveListener);
                dialogFragment.setNegativeListener(mNegativeListener);
                dialogFragment.setNeutralListener(mNeutralListener);
                dialogFragment.setCustomView(mCustomView);
                dialogFragment.setStyle(R.style.AlertDialogTheme);
                dialogFragment.show(ft, DIALOG_TAG);
            }catch (IllegalStateException ex){
                Log.d("Error showing dialog","");
            }
            return dialogFragment;
        }

        @Nullable
        public AlertDialogFragment showError(){
            AlertDialogFragment dialogFragment = null;
            try {
                FragmentTransaction ft = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();
                Fragment prev = ((AppCompatActivity) mContext).getSupportFragmentManager().findFragmentByTag(DIALOG_TAG);
                if (prev != null) {
                    ft.remove(prev);
                }

                dialogFragment = AlertDialogFragment.newInstance(mTitle, mResourceMessage, mPositiveText, mNegativeText, mNeutralText, mIsCancelable, mStringMessage);
                dialogFragment.setPositiveListener(mPositiveListener);
                dialogFragment.setNegativeListener(mNegativeListener);
                dialogFragment.setNeutralListener(mNeutralListener);
                dialogFragment.setCustomView(mCustomView);
                dialogFragment.setStyle(R.style.ErrorDialogTheme);
                dialogFragment.show(ft, DIALOG_TAG);
            }catch (IllegalStateException ex){
                Log.e("Error showing dialog","");
            }
            return dialogFragment;
        }
    }
}
