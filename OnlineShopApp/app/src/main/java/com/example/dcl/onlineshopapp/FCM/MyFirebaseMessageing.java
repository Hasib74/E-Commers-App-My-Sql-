package com.example.dcl.onlineshopapp.FCM;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.example.dcl.onlineshopapp.R;
import com.example.dcl.onlineshopapp.Utils.NotificatiopnHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class MyFirebaseMessageing extends FirebaseMessagingService {
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData()!=null){
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                sendNotificationAPI26(remoteMessage);
            }else {
                sendNotification(remoteMessage);
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotificationAPI26(RemoteMessage remoteMessage) {

        Map<String,String>data=remoteMessage.getData();
        String title=data.get("title");
        String message=data.get("message");

        NotificatiopnHelper helper;
        Notification.Builder builder;
        Uri defaultSoundUri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        helper=new NotificatiopnHelper(this);

        builder=helper.getOnlineShopNotification(title,message,defaultSoundUri);

        helper.getManager().notify(new Random().nextInt(),builder.build());


    }
    private void sendNotification(RemoteMessage remoteMessage) {

        Map<String,String>data=remoteMessage.getData();
        String title=data.get("title");
        String message=data.get("message");
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);

        NotificationManager noti=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        noti.notify(new Random().nextInt(),builder.build());
    }
}
