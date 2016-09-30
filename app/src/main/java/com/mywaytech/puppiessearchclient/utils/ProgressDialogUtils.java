package com.mywaytech.puppiessearchclient.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.fragments.ProgressDialogFragment;

/**
 * Created by Marco on 21/9/2016.
 */
public class ProgressDialogUtils {

    public static class Builder {

        public static final String DIALOG_TAG = "com.mywaytech.puppiessearchclient.controllers.fragments.ProgressDialogFragment";

        private Context mContext;
        private int mProgressMessage;
        private View mCustomView;

        public Builder(Context context) {
            mContext = context;
        }

        @StringRes
        public Builder setMessage(int message) {
            mProgressMessage = message;
            return this;
        }

        public Builder setCustomView(View customView) {
            mCustomView = customView;
            return this;
        }



        public ProgressDialogFragment show() {
            ProgressDialogFragment progressFragment = null;
            try {
                FragmentTransaction ft = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();
                Fragment prev = ((AppCompatActivity) mContext).getSupportFragmentManager().findFragmentByTag(DIALOG_TAG);
                if (prev != null) {
                    ft.remove(prev);
                }
                progressFragment = ProgressDialogFragment.newInstance(mProgressMessage);
                progressFragment.setCustomView(mCustomView);
                progressFragment.setCancelable(false);
                progressFragment.setStyle(R.style.AlertDialogTheme);
                progressFragment.show(ft, DIALOG_TAG);
            } catch (IllegalStateException ex) {
                Log.d("Error showing dialog", "");
            }
            return progressFragment;
        }

        public ProgressDialogFragment showError() {
            ProgressDialogFragment progressFragment = null;
            try {
                FragmentTransaction ft = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();
                Fragment prev = ((AppCompatActivity) mContext).getSupportFragmentManager().findFragmentByTag(DIALOG_TAG);
                if (prev != null) {
                    ft.remove(prev);
                }
                progressFragment = ProgressDialogFragment.newInstance(mProgressMessage);
                progressFragment.setCustomView(mCustomView);
                progressFragment.setStyle(R.style.AlertDialogTheme);
                progressFragment.show(ft, DIALOG_TAG);
            } catch (IllegalStateException ex) {
                Log.d("Error showing dialog", "");
            }
            return progressFragment;
        }
    }


}
