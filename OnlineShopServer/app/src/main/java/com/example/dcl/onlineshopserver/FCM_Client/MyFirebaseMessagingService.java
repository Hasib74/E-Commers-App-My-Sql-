package com.example.dcl.onlineshopserver.FCM_Client;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.dcl.onlineshopserver.MainActivity;
import com.example.dcl.onlineshopserver.R;
import com.example.dcl.onlineshopserver.Utils.NotificatiopnHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessageService";

    Bitmap bitmap;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {



        if (remoteMessage.getData()!=null){
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                sendNotificationAPI26(remoteMessage);
            }else {
                sendNotification(remoteMessage);
            }

        }


/*

               Log.d(TAG, "From: " + remoteMessage.getFrom());


        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }


        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }


        String message = remoteMessage.getData().get("message");
        String imageUri = remoteMessage.getData().get("image");

        String TrueOrFlase = remoteMessage.getData().get("AnotherActivity");


        bitmap = getBitmapfromUrl(imageUri);

        sendNotification(message, bitmap, TrueOrFlase);
*/

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotificationAPI26(RemoteMessage remoteMessage) {

        Map<String,String> data=remoteMessage.getData();
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



   /* private void sendNotification(String messageBody, Bitmap image, String TrueOrFalse) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("AnotherActivity", TrueOrFalse);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 *//* Request code *//*, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(image)*//*Notification icon image*//*
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(messageBody)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(image))*//*Notification with Image*//*
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 *//* ID of notification *//*, notificationBuilder.build());
    }

    *//*
     *To get a Bitmap image from the URL received
     * *//*
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }*/
}
