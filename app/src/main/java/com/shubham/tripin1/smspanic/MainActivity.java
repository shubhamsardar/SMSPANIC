package com.shubham.tripin1.smspanic;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Intent mServiceIntent;
    private TripInService mSensorService;
    Context ctx;
    public Context getCtx() {
        return ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_main);
        mSensorService = new TripInService(getCtx());
        mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

//        /** this gives us the time for the first trigger.  */
//        Calendar cal = Calendar.getInstance();
//        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
//        long interval = 1000 ; // 5 minutes in milliseconds
//        Intent serviceIntent = new Intent(ctx, TripInService.class);
//// make sure you **don't** use *PendingIntent.getBroadcast*, it wouldn't work
//        PendingIntent servicePendingIntent =
//                PendingIntent.getService(ctx,
//                        TripInService.SERVICE_ID, // integer constant used to identify the service
//                        serviceIntent,
//                        PendingIntent.FLAG_CANCEL_CURRENT);  // FLAG to avoid creating a second service if there's already one running
//// there are other options like setInexactRepeating, check the docs
//        am.setRepeating(
//                AlarmManager.RTC_WAKEUP,//type of alarm. This one will wake up the device when it goes off, but there are others, check the docs
//                cal.getTimeInMillis(),
//                interval,
//                servicePendingIntent
//        );

        Intent msgIntent = new Intent(this, SimpleIntentService.class);
        msgIntent.putExtra(SimpleIntentService.PARAM_IN_MSG, "fuck");
        startService(msgIntent);
        Log.i("Main Activity "," OnResume calling intent service");

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }


    @Override
    protected void onDestroy() {
        Log.i("MAINACT", "onDestroy!");
        /** this gives us the time for the first trigger.  */
        Intent restartService = new Intent(getApplicationContext(), TripInService.class);
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +1000, restartServicePI);
        super.onDestroy();

    }
}
