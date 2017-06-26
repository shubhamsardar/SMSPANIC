package com.shubham.tripin1.smspanic;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateFormat;
import android.util.Log;

/**
 * Created by Tripin1 on 6/23/2017.
 */

public class SimpleIntentService extends IntentService {

    public static final String PARAM_IN_MSG = "imsg";
    public static final String PARAM_OUT_MSG = "omsg";
    NotificationManager notificationManager;


    public SimpleIntentService() {
        super("SimpleIntentService");
        Log.i("Intent Setvice "," Cunstructor");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        int version = Build.VERSION.SDK_INT;
        NotificationCompat.Builder notification;
        if(version>=23){
            notification = new NotificationCompat.Builder(this)
                    .setContentTitle("Foreground IntentService")
                    .setTicker("This depends on your Intentservice")
                    .setContentText("m above 6.0 bitch! ")
                    .setSmallIcon(R.mipmap.ic_launcher);
        }else {
            notification = new NotificationCompat.Builder(this)
                    .setContentTitle("Foreground IntentService")
                    .setTicker("This depends on your Intentservice")
                    .setContentText("m below 6.0 ")
                    .setSmallIcon(R.mipmap.ic_launcher);
        }
        Log.i("Intent Setvice "," OnHandleIntent");

        notificationManager.notify(10, notification.build());


        startForeground(10,
                notification.build());

        while (true){
           // Log.i("While,,,"," the infinite while");

        }


    }
}