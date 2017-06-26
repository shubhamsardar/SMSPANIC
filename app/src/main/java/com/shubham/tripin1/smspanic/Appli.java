package com.shubham.tripin1.smspanic;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Tripin1 on 6/22/2017.
 */

public class Appli extends Application {

    private TripInService mSensorService;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i(ServiceRestarter.class.getSimpleName(), "onTerminate Appli.........................");
        getApplicationContext().startService(new Intent(getApplicationContext(), mSensorService.getClass()));
    }


    @Override
    public ComponentName startService(Intent service) {
        return super.startService(service);
    }
}
