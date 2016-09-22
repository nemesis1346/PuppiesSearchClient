package com.mywaytech.puppiessearchclient.controllers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.fragments.PersonalInfoRegistrationFragment;
import com.mywaytech.puppiessearchclient.controllers.fragments.ProgressDialogFragment;
import com.mywaytech.puppiessearchclient.controllers.fragments.RegistrationBaseFragment;
import com.mywaytech.puppiessearchclient.controllers.fragments.UserPictureRegistrationFragment;
import com.mywaytech.puppiessearchclient.models.NewUserObject;
import com.mywaytech.puppiessearchclient.services.FireBaseHandler;
import com.mywaytech.puppiessearchclient.utils.AlertDialogUtils;
import com.mywaytech.puppiessearchclient.utils.ProgressDialogUtils;

/**
 * Created by m.maigua on 4/13/2016.
 */
public class RegistrationActivity extends BaseActivity implements RegistrationBaseFragment.PersonalInfoRegistrationCallback, FireBaseHandler.CallbackSign {

    protected int mCurrentPageNumber;

    private ProgressDialogFragment mProgressfragment;

    private NewUserObject mNewUserObject;

    public static final int PAGE_PERSONAL_INFO = 0;
    public static final int PAGE_USER_PICTURE = 1;

    public static final int NUM_PAGES = 2;

    private Button mBtnNext;
    private Button mBtnBack;

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mNewUserObject = new NewUserObject();

        mBtnBack = (Button) findViewById(R.id.btn_back);
        mBtnNext = (Button) findViewById(R.id.btn_foward);

        mBtnBack.setOnClickListener(onClickBack);
        mBtnNext.setOnClickListener(onClickNext);

        mCurrentPageNumber = 0;
        showPage(mCurrentPageNumber);

    }

    public void showPage(int pageNumber) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (pageNumber) {
            case PAGE_PERSONAL_INFO:
                ft.replace(R.id.fragment_container,
                        PersonalInfoRegistrationFragment.newInstance(mNewUserObject)).commit();
                break;
            case PAGE_USER_PICTURE:
                ft.replace(R.id.fragment_container,
                        UserPictureRegistrationFragment.newInstance(mNewUserObject)).commit();
                break;
            default:
                Log.d("Invalid Position: ", "" + pageNumber);
        }
    }

    public View.OnClickListener onClickBack = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mCurrentPageNumber--;
            if (mCurrentPageNumber >= 0) {
                showPage(mCurrentPageNumber);
            } else {
                onBackPressed();
            }
        }
    };

    public View.OnClickListener onClickNext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            boolean isFormReady = checkFormReady();

            if (!isFormReady) return;

            mCurrentPageNumber++;
            if (mCurrentPageNumber < getNumPages()) {
                showPage(mCurrentPageNumber);
            } else {
                mCurrentPageNumber--;
                mRegistrationIntent();
            }
        }
    };

    public boolean checkFormReady() {
        RegistrationBaseFragment fragment = (RegistrationBaseFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        return fragment != null && fragment.isFormReady();
    }

    public void mRegistrationIntent() {
        //TODO FIRST, VALIDATE THE EXISTENCE OF CURRENT USER
        //FIREBASE INTEND
        showProgress();
        FireBaseHandler.getInstance(this)
                .fireBaseSignIn(mNewUserObject.getmEmail(), mNewUserObject.getmPassword(), this, this);
    }

    public int getNumPages() {
        return NUM_PAGES;
    }

    @Override
    public void updateRegistrationModel(NewUserObject newUserObject) {
        mNewUserObject.setmName(newUserObject.getmName());
        mNewUserObject.setmEmail(newUserObject.getmEmail());
        mNewUserObject.setmPassword(newUserObject.getmPassword());
    }

    @Override
    public void onCompleteSigning(boolean isCreated) {
        boolean isSaved;
        if (isCreated) {
            //GET CURRENT USER INFO
            isSaved = FireBaseHandler.getInstance(this).saveUserObject(mNewUserObject);
            if (isSaved) {
                hideProgress();
                new AlertDialogUtils.Builder(this)
                        .setResourceMessage(R.string.registration_success)
                        .setPositiveText(R.string.btn_ok)
                        .setPositiveButtonListener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = MainActivity.newIntent(RegistrationActivity.this);
                                startActivity(intent);
                            }
                        })
                        .show();

            } else {
                new AlertDialogUtils.Builder(this)
                        .setResourceMessage(R.string.registration_failure)
                        .setPositiveText(R.string.btn_ok)
                        .show();
            }
        } else {
            new AlertDialogUtils.Builder(this)
                    .setResourceMessage(R.string.registration_failure)
                    .setPositiveText(R.string.btn_ok)
                    .show();
        }
    }

    private void showProgress() {
        View customView = LayoutInflater.from(this).inflate(R.layout.row_progress, null);
        TextView message = (TextView) customView.findViewById(R.id.text_progress_info);
        message.setText(R.string.registration_text);
        mProgressfragment = new ProgressDialogUtils.Builder(this)
                .setCustomView(customView)
                .show();

    }

    private void hideProgress() {
        if (mProgressfragment != null && mProgressfragment.isVisible()) {
            mProgressfragment.dismiss();
        }
    }
}
