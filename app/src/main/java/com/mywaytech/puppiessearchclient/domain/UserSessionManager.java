package com.mywaytech.puppiessearchclient.domain;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.mywaytech.puppiessearchclient.models.NewUserObject;

/**
 * Created by Marco on 26/9/2016.
 */
public class UserSessionManager {

    private static final String PREFERENCES_USER = "com.mywaytech.puppiessearchclient.preferences.user";

    private static final String KEY_USER = "com.mywaytech.puppiessearchclient.preferences.keys.user";

    private Context mContext;
    private NewUserObject mNewUserObject;
    private static UserSessionManager sInstance;

    public static UserSessionManager getInstance(Context context){
        if(sInstance==null){
            sInstance =new UserSessionManager(context);
        }
        return sInstance;
    }

    private UserSessionManager(Context context){
        mContext = context;
    }

    public void logged(NewUserObject newUserObject, boolean saveUserLocally){
        if(newUserObject == null){
            return;
        }
        if(saveUserLocally){
            saveLocalUser(newUserObject);
        }else{
            clearLocalUser();
        }
        mNewUserObject = newUserObject;
    }
    public void saveLocalUser(NewUserObject newUserObject) {
        Gson gson = new Gson();
        String userString = gson.toJson(newUserObject);
        SharedPreferences sharedPref = mContext.getSharedPreferences(PREFERENCES_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_USER, userString);
        editor.commit();
        Log.d("UserSessionManager: "," se guardo el usuario en SessionManager");
    }

    /**
     * This method erases the user information saved in preferences
     */
    private void clearLocalUser() {
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
    public NewUserObject getLocalUser() {
        NewUserObject userModel = null;
        SharedPreferences sharedPref = mContext.getSharedPreferences(PREFERENCES_USER, Context.MODE_PRIVATE);
        String userString = sharedPref.getString(KEY_USER, null);

        if (userString != null) {
            Gson gson = new Gson();
            userModel = gson.fromJson(userString, NewUserObject.class);
        }
        return userModel;
    }
}
