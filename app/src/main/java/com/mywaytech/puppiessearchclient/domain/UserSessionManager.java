package com.mywaytech.puppiessearchclient.domain;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.mywaytech.puppiessearchclient.dataaccess.FireBaseHandler;
import com.mywaytech.puppiessearchclient.models.NewUserModel;

import java.io.ByteArrayInputStream;

/**
 * Created by Marco on 26/9/2016.
 */
public class UserSessionManager {

    private static final String PREFERENCES_USER = "com.mywaytech.puppiessearchclient.preferences.user";

    private static final String KEY_USER = "com.mywaytech.puppiessearchclient.preferences.keys.user";

    private Context mContext;
    private NewUserModel mNewUserObject;
    private static UserSessionManager sInstance;

    private Bitmap mUserImage;

    public static UserSessionManager getInstance(Context context){
        if(sInstance==null){
            sInstance =new UserSessionManager(context);
        }
        return sInstance;
    }

    private UserSessionManager(Context context){
        mContext = context;
    }

    public void logged(NewUserModel newUserObject, boolean saveUserLocally){
        if(newUserObject == null){
            return;
        }
        if(saveUserLocally){
            saveLocalUser(newUserObject);
        }else{
            clearLocalUser();
        }
        mNewUserObject = newUserObject;
        FireBaseHandler.getInstance(mContext).getUserObjectFirebaseDatabaseReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        NewUserModel mNewUserObject = dataSnapshot.getValue(NewUserModel.class);

                        final long ONE_MEGABYTE = 1024 * 1024;

                        FireBaseHandler.getInstance(mContext)
                                .getUserObjectFirebaseStorageReference(mNewUserObject.getUserImagePath())
                                .getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                UserSessionManager.getInstance(mContext).setUserImage(bitmap);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.e("errorSaveImage: ",exception.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
    public void saveLocalUser(NewUserModel newUserObject) {
        Gson gson = new Gson();
        String userString = gson.toJson(newUserObject);
        SharedPreferences sharedPref = mContext.getSharedPreferences(PREFERENCES_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_USER, userString);
        editor.commit();

        Log.d("UserSessionManager: "," se guardo el usuario en SessionManager");
    }

    public Bitmap getUserImage() {
        return mUserImage;
    }

    public void setUserImage(Bitmap userImage) {
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
        Log.d("UserSessionManager: "," se borro el usuario en SessionManager");

    }

    /**
     * This method retrieves the user saved in preferences
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
}
