package com.mywaytech.puppiessearchclient.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.fragments.AccountFragment;
import com.mywaytech.puppiessearchclient.controllers.fragments.PersonalInfoRegistrationFragment;
import com.mywaytech.puppiessearchclient.controllers.fragments.ProgressDialogFragment;
import com.mywaytech.puppiessearchclient.controllers.fragments.RegistrationBaseFragment;
import com.mywaytech.puppiessearchclient.controllers.fragments.UserPictureRegistrationFragment;
import com.mywaytech.puppiessearchclient.domain.UserSessionManager;
import com.mywaytech.puppiessearchclient.models.NewUserModel;
import com.mywaytech.puppiessearchclient.dataaccess.FireBaseHandler;
import com.mywaytech.puppiessearchclient.utils.AlertDialogUtils;
import com.mywaytech.puppiessearchclient.utils.PhotoUtils;
import com.mywaytech.puppiessearchclient.utils.ProgressDialogUtils;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by m.maigua on 4/13/2016.
 */
public class RegistrationActivity extends BaseActivity implements
        RegistrationBaseFragment.PersonalInfoRegistrationCallback,
        FireBaseHandler.CallbackSign,
        UserPictureRegistrationFragment.UserPictureImageCallback {


    public static final String EXTRA_IS_EDITING = "extra_is_editing";
    protected int mCurrentPageNumber;

    private ProgressDialogFragment mProgressfragment;

    private NewUserModel mNewUserObject;

    public static final int PAGE_PERSONAL_INFO = 0;
    public static final int PAGE_USER_PICTURE = 1;

    private Bitmap mUserPicture;

    public static final int NUM_PAGES = 2;

    private StorageReference mStorageRef;

    private Button mBtnNext;
    private Button mBtnBack;

    private boolean mObjectflag;
    private boolean mUserPictureFlag;

    private boolean mIsEditingFlag;

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mIsEditingFlag = getIntent().getBooleanExtra(EXTRA_IS_EDITING, false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.registration_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (mIsEditingFlag) {
            mNewUserObject = UserSessionManager.getInstance(this).getLocalUser();
        } else {
            mNewUserObject = new NewUserModel();
        }

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
        showProgress();
        if (mIsEditingFlag) {
            updateInfo(mNewUserObject);
        } else {
            FireBaseHandler.getInstance(this)
                    .fireBaseSignIn(mNewUserObject.getmEmail(), mNewUserObject.getmPassword(), this, this);
        }
    }

    public void updateInfo(NewUserModel model) {
        mObjectflag =true;
        //TODO PASS THIS TO FIREBASE HANLER
        String test = "/" + FireBaseHandler.OBJECT_USERS_NAME + "/" + FireBaseHandler.getInstance(this).getUserKey();
        Map<String, Object> updates = new HashMap<>();
        updates.put("mUid", model.getmUid());
        updates.put("mName", model.getmName());
        updates.put("mEmail", model.getmEmail());
        updates.put("mPassword", model.getmPassword());
        updates.put("mUserImagePath", model.getmUserImagePath());
        updates.put("mAddress", model.getmAddress());

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(test, updates);

        FireBaseHandler.getInstance(this)
                .getFirebaseDatabaseReference()
                .updateChildren(childUpdates).addOnCompleteListener(mOnCompleteListenerEditing)
                .addOnFailureListener(mOnFailureListener);

    }

    private OnCompleteListener<Void> mOnCompleteListenerEditing = new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            saveImageInFireBase();

            FireBaseHandler.getInstance(RegistrationActivity.this)
                    .getUserObjectFirebaseStorageReference(mNewUserObject.getmUserImagePath())
                    .getBytes(UserSessionManager.ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    hideProgress();

                    UserSessionManager.getInstance(RegistrationActivity.this).clearLocalUser();
                    UserSessionManager.getInstance(RegistrationActivity.this).saveLocalUser(mNewUserObject);
                    UserSessionManager.getInstance(RegistrationActivity.this).setUserImage(bytes);

                    new AlertDialogUtils.Builder(RegistrationActivity.this)
                            .setResourceMessage(R.string.registration_success)
                            .setPositiveText(R.string.btn_ok)
                            .setPositiveButtonListener(new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intentForResult = getIntent();
                                    intentForResult.putExtra(AccountFragment.EXTRA_EDIT_USER_OBJECT, mNewUserObject);
                                    setResult(RESULT_OK, intentForResult);
                                    finish();
                                }
                            })
                            .show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("errorSaveImage: ", exception.getMessage());
                }
            });

        }
    };

    private OnFailureListener mOnFailureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.e("errorEditing: "," "+e.getMessage());
            new AlertDialogUtils.Builder(RegistrationActivity.this)
                    .setResourceMessage(R.string.registration_failure)
                    .setPositiveText(R.string.btn_ok)
                    .setPositiveButtonListener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .show();
        }
    };

    public int getNumPages() {
        return NUM_PAGES;
    }

    @Override
    public void updateRegistrationModel(NewUserModel newUserObject) {
        mNewUserObject.setmName(newUserObject.getmName());
        mNewUserObject.setmEmail(newUserObject.getmEmail());
        mNewUserObject.setmPassword(newUserObject.getmPassword());
        mNewUserObject.setUserImagePath(newUserObject.getmUserImagePath());
        mNewUserObject.setAddress(newUserObject.getmAddress());
    }

    @Override
    public void onCompleteSigning(boolean isCreated) {
        if (isCreated) {
            //GET CURRENT USER INFO
            //TODO SAVE IMAGE
            saveImageInFireBase();
            FireBaseHandler.getInstance(this).saveUserObject(mNewUserObject);
            mObjectflag =true;
        } else {
            new AlertDialogUtils.Builder(this)
                    .setResourceMessage(R.string.registration_failure)
                    .setPositiveText(R.string.btn_ok)
                    .show();
        }
    }

    public void saveImageInFireBase() {

        mStorageRef = FireBaseHandler.getInstance(this).getUserObjectFirebaseStorageReference(mNewUserObject.getmUserImagePath());

        byte[] imageByte = PhotoUtils.processImagePet(mUserPicture);

        UploadTask uploadTask = mStorageRef.putBytes(imageByte);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mUserPictureFlag = false;
                validateRegistration();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mUserPictureFlag = true;
                validateRegistration();
            }
        });
    }

    public void validateRegistration() {
        if (mObjectflag && mUserPictureFlag) {
            hideProgress();
            UserSessionManager.getInstance(this).saveLocalUser(mNewUserObject);

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
            hideProgress();
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

    @Override
    public void userPictureImageResult(Bitmap userPicture) {
        mUserPicture = userPicture;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
