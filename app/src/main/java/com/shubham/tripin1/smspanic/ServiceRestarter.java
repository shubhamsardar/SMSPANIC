package com.shubham.tripin1.smspanic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Tripin1 on 6/22/2017.
 */

public class ServiceRestarter extends BroadcastReceiver {

    private TripInService mSensorService;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(ServiceRestarter.class.getSimpleName(), "Service Stops! Oooooooooooooppppssssss!!!!");
        mSensorService = new TripInService(context);
        context.startService(new Intent(context, mSensorService.getClass()));
    }
}
