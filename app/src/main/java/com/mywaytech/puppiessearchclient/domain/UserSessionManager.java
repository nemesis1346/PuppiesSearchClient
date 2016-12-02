package com.mywaytech.puppiessearchclient.domain;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.mywaytech.puppiessearchclient.dataaccess.FireBaseHandler;
import com.mywaytech.puppiessearchclient.models.NewUserModel;

/**
 * Created by Marco on 26/9/2016.
 */
public class UserSessionManager {

    private static final String PREFERENCES_USER = "com.mywaytech.puppiessearchclient.preferences.user";

    private static final String KEY_USER = "com.mywaytech.puppiessearchclient.preferences.keys.user";

    private Context mContext;
    private NewUserModel mNewUserObject;
    private static UserSessionManager sInstance;
    public static final long ONE_MEGABYTE = 1024 * 1024;
    private GoogleApiClient mGoogleApiClient;

    private byte[] mUserImage;

    public static UserSessionManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new UserSessionManager(context);
        }
        return sInstance;
    }

    private UserSessionManager(Context context) {
        mContext = context;
    }

    public void logged(NewUserModel newUserObject, boolean saveUserLocally) {
        if (newUserObject == null) {
            return;
        }
        if (saveUserLocally) {
            saveLocalUser(newUserObject);
        } else {
            clearLocalUser();
        }
        mNewUserObject = newUserObject;
        FireBaseHandler.getInstance(mContext).getUserObjectFirebaseDatabaseReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        NewUserModel mResultUser = (NewUserModel) dataSnapshot.getValue(NewUserModel.class);

                        FireBaseHandler.getInstance(mContext)
                                .getUserImageFirebaseStorageReference(mResultUser.getmUserImagePath())
                                .getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                UserSessionManager.getInstance(mContext).setUserImage(bytes);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.e("errorSaveImage: ", exception.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void saveLocalUser(NewUserModel newUserObject) {
        clearLocalUser();
        mNewUserObject = newUserObject;
        Gson gson = new Gson();
        String userString = gson.toJson(newUserObject);
        SharedPreferences sharedPref = mContext.getSharedPreferences(PREFERENCES_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_USER, userString);
        editor.commit();

        Log.d("UserSessionManager: ", " se guardo el usuario en SessionManager");
    }

    public byte[] getUserImage() {
        return mUserImage;
    }

    public void setUserImage(byte[] userImage) {
        mUserImage = userImage;
    }

    /**
     * This method erases the user information saved in preferences
     */
    public void clearLocalUser() {
        SharedPreferences sharedPref = mContext.getSharedPreferences(PREFERENCES_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
        Log.d("UserSessionManager: ", " se borro el usuario en SessionManager");

    }

    /**
     * This method retrieves the user saved in preferences
     *
     * @return the user model found in preferences, if it cant find it it returns null
     */
    @Nullable
    public NewUserModel getLocalUser() {
        NewUserModel userModel = null;
        SharedPreferences sharedPref = mContext.getSharedPreferences(PREFERENCES_USER, Context.MODE_PRIVATE);
        String userString = sharedPref.getString(KEY_USER, null);

        if (userString != null) {
            Gson gson = new Gson();
            userModel = gson.fromJson(userString, NewUserModel.class);
        }
        return userModel;
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        mGoogleApiClient = googleApiClient;
    }
}
