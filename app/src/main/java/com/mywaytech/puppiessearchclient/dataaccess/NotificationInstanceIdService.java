package com.mywaytech.puppiessearchclient.dataaccess;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Marco on 28/9/2016.
 */
public class NotificationInstanceIdService extends FirebaseInstanceIdService{

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Refreshed token: " ,""+ refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
     //TODO ADD TO THE DATABASE THE TOKEN
    }
}
