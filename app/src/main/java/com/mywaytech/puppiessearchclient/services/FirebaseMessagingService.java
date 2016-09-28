package com.mywaytech.puppiessearchclient.services;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.mywaytech.puppiessearchclient.R;
import com.mywaytech.puppiessearchclient.controllers.MainActivity;

import java.util.Map;
import java.util.UUID;

/**
 * Created by Marco on 28/9/2016.
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static int mCounter=0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            Log.d("Notification Body: ","" + remoteMessage.getNotification().getBody());
            sendNotificationToSystem(remoteMessage.getNotification());
        }
    }

    private void sendNotificationToSystem(RemoteMessage.Notification notification){
        try{

            Intent mainActivityIntent = MainActivity.newIntent(this);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, mainActivityIntent,
                    PendingIntent.FLAG_ONE_SHOT);

            if(notification!=null){
                NotificationCompat.Builder notificationsBuilder =  new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_dog_clue)
                        .setContentTitle(notification.getTitle())
                        .setContentText(notification.getBody())
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(notification.getBody()))
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);


                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mCounter++;
                notificationManager.notify(mCounter/* ID of notification */
                        , notificationsBuilder.build());

            }
        }catch(Exception ex){
            Log.e("sendNotification:",""+ex);

        }
    }

}
