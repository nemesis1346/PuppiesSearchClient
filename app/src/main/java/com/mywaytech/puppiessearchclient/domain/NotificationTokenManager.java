package com.mywaytech.puppiessearchclient.domain;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Marco on 28/9/2016.
 */
public class NotificationTokenManager {
    /**
     * Constant to save notification token preferences
     */
    private static final String PREFERENCES_NOTIFICATION_TOKEN = "com.bayteq.digitalbankingmultiowner.preferences.notification_token";

    /**
     * Constant that serves as the key for the notification token
     */
    private static final String KEY_NOTIFICATION_TOKEN = "com.bayteq.digitalbankingmultiowner.preferences.keys.notification_token";


    /**
     * Instance of this singleton
     */
    private static NotificationTokenManager ourInstance;

    /**
     * Application context used to access the shared preferences
     */
    private Context mContext;

    /**
     * This method returns the only instance of this singleton
     * @param context this is the context accessing this singleton
     * @return Singleton instance
     */
    public static NotificationTokenManager getInstance(Context context) {
        if(ourInstance == null) {
            ourInstance = new NotificationTokenManager(context);
        }
        return ourInstance;
    }

    /**
     * This is the private constructor of this class. It is private to avoid multiple instances
     * @param context This is the context of the calling class. It is used to get application context
     */
    private NotificationTokenManager(Context context){
        mContext = context.getApplicationContext();
    }

    public void saveNotificationToken(@NonNull String token){
        SharedPreferences sharedPref = mContext.getSharedPreferences(PREFERENCES_NOTIFICATION_TOKEN,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_NOTIFICATION_TOKEN, token);
        editor.commit();
    }

    public @Nullable
    String getNotificationToken(){
        SharedPreferences sharedPref = mContext.getSharedPreferences(PREFERENCES_NOTIFICATION_TOKEN,Context.MODE_PRIVATE);
        return sharedPref.getString(KEY_NOTIFICATION_TOKEN,null);
    }


    public void deleteNotificationToken(){
        SharedPreferences sharedPref = mContext.getSharedPreferences(PREFERENCES_NOTIFICATION_TOKEN,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }
}
