package com.example.dcl.onlineshopserver.Utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.dcl.onlineshopserver.R;
import com.example.dcl.onlineshopserver.R;

public class NotificatiopnHelper extends ContextWrapper {

    private  static  final String OnliShp_Channel_id="com.example.dcl.onlineshopserver.Utils";
    private  static  final String OnliShp_Channel_name="Online_Shop_Server";

    NotificationManager notificationManager;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificatiopnHelper(Context base) {
        super(base);
        createChannal();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannal() {
        NotificationChannel notificationChannel=new NotificationChannel(OnliShp_Channel_id,OnliShp_Channel_name, NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.enableLights(false);
        notificationChannel.enableVibration(true);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(notificationChannel);
    }

    public NotificationManager getManager() {
        if (notificationManager==null){
            notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public  Notification.Builder getOnlineShopNotification(String title,String message,Uri soundUri){


        return new Notification.Builder(getApplicationContext(),OnliShp_Channel_id)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(soundUri)
                .setAutoCancel(true);

    }




}
