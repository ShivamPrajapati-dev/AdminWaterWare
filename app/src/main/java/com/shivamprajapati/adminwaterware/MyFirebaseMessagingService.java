package com.shivamprajapati.adminwaterware;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;

import android.support.v4.app.NotificationCompat;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    int currentNotificationID=0;
    List<NotificationList> notificationLists;
    NotificationList list;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if(remoteMessage.getData().size()>0){
            Map<String,String> payload=remoteMessage.getData();
            showNotification(payload);
        }
    }

    private void showNotification(Map<String, String> payload) {

        Intent intent=new Intent(this,MainActivity.class);



        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);



        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_notifications_black_24dp))
                .setContentTitle(payload.get("title"))
                .setContentText(payload.get("body"))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(payload.get("body")))
                .setLights(Notification.DEFAULT_LIGHTS,2000,2000);
        builder.setContentIntent(pendingIntent);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel("id","name",NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.enableVibration(true);
            builder.setChannelId("id");
            notificationManager.createNotificationChannel(channel);
            Notification notification=builder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            currentNotificationID++;
            int notificationId = currentNotificationID;
            if (notificationId == Integer.MAX_VALUE - 1)
                notificationId = 0;

            notificationManager.notify(notificationId, notification);
        }else{

            Notification notification=builder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            currentNotificationID++;
            int notificationId = currentNotificationID;
            if (notificationId == Integer.MAX_VALUE - 1)
                notificationId = 0;

            notificationManager.notify(notificationId, notification);
        }



        Calendar calendar=Calendar.getInstance();
        Date date=calendar.getTime();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE, MMM d hh:mm aaa");
        String d=simpleDateFormat.format(date);

        list=new NotificationList();

        list.setTitle(payload.get("title"));
        list.setBody(payload.get("body"));
        list.setDate(d);


        Gson gson=new Gson();
        Type type=new TypeToken<ArrayList<NotificationList>>(){}.getType();

        if(SharedPref.readSavedSettingsNotificationList(getApplicationContext(),"nl",null)!=null){
            notificationLists=gson.fromJson(SharedPref.readSavedSettingsNotificationList(getApplicationContext(),"nl",null),type);
        }
        else{
            notificationLists=new ArrayList<>();
        }


        if(payload.get("title").equals("Request Cancelled")){

            list.setPhone(payload.get("mobile"));
            list.setAddress(payload.get("residence"));
            list.setExpandable(true);
        }
        else{
            list.setExpandable(false);
        }
        notificationLists.add(list);
        String json = gson.toJson(notificationLists);
        SharedPref.saveSharedSettingsNotificationList(getApplicationContext(),"nl",json);




    }
}
