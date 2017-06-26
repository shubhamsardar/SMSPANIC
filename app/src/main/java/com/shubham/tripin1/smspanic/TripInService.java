package com.shubham.tripin1.smspanic;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tripin1 on 6/22/2017.
 */

public class TripInService extends Service {

    private static final String LOG_TAG = "ForegroundService";

    private boolean mRunning;
    public int counter = 0;
    public static final int SERVICE_ID = 1;

    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
    private BroadcastReceiver yourReceiver;

    public TripInService(Context ctx) {
        super();
        Log.i("HERE", "here I am!");
    }

    public TripInService() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!mRunning) {
            mRunning = true;
            // do something
            final IntentFilter theFilter = new IntentFilter();
            theFilter.addAction(ACTION);
            this.yourReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {
                    // Do whatever you need it to do when it receives the broadcast
                    // Example show a Toast message...
                    showSuccessfulBroadcast();
                }
            };
            // Registers the receiver so that your service will listen for
            // broadcasts
            this.registerReceiver(this.yourReceiver, theFilter);
        }
        //**Your code **
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        //startTimer();
        int version = Build.VERSION.SDK_INT;
        NotificationCompat.Builder notification;


        if(version>=23){
            notification = new NotificationCompat.Builder(this)
                    .setContentTitle("Tripin Foreground Service")
                    .setTicker("This depends on your service")
                    .setContentText("I am not killed :) m above 6.0 bitch! ")
                    .setSmallIcon(R.mipmap.ic_launcher);
        }else {
            notification = new NotificationCompat.Builder(this)
                    .setContentTitle("Tripin Foreground Service")
                    .setTicker("This depends on your service")
                    .setContentText("I am killed if my app is killed :( m below 6.0 ")
                    .setSmallIcon(R.mipmap.ic_launcher);
        }


        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
                notification.build());

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mRunning = false;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(this.yourReceiver);

        Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent("uk.ac.shef.oak.ActivityRecognition.RestartSensor");
        sendBroadcast(broadcastIntent);
        stoptimertask();

    }

    private void showSuccessfulBroadcast() {
        Log.d("Broadcast called", "idn");
        Toast.makeText(this, "Broadcast Successful!!!", Toast.LENGTH_LONG)
                .show();

        Intent dialogIntent = new Intent(this, Main2Activity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);
    }

    public void onTaskRemoved(Intent intent) {
        Log.i(" My Service", "on Task Removed Called");
        Intent restartService = new Intent(getApplicationContext(), this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +1000, restartServicePI);

    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime = 0;

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  " + (counter++));
            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


}
