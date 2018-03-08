package com.example.issic.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Issic on 3/8/2018.
 */

public class AlarmReceiver extends BroadcastReceiver{
    @Override

    //In this class we have our Broadcast, a broadcast is a background task that happens even if the
    //app is off. Broadcast are normally used for small fast tasks that last no longer than say 10
    //seconds, if they last any longer than the task is essentially cancelled. In our case
    //however we are calling the broadcast and then calling a service, that extends the lifecycle of
    //the broadcast. So that is all this class does, it creates as broadcasts and then calls our
    //service, which is BackgroundService.class.
    public void onReceive(Context context, Intent intent) {
        Intent background = new Intent(context, BackgroundService.class);
        context.startService(background);
    }
}
