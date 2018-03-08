package com.example.issic.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Issic on 3/8/2018.
 */

    //Here is our service class. This is where it all happens. The service starts, we create a new
    //background thread. (The reason for the background thread is because services normally happen in
    //the main thread which is bad because in our case the phone would become noticeably slow.)
    //Our service then happens in the new background thread and we do what we need to do. Once the
    //task is done the thread gets destroyed. This prevents memory leaks and the AlarmManager resets.

public class BackgroundService extends Service {

    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
    }

    private Runnable myTask = new Runnable() {
        public void run() {
            // Do something here
            System.out.println("This is working. Yay!");
            createNotification(context, "Background Service is running", "15 Seconds Has Passed", "Alert");



            stopSelf();
        }
        public void createNotification(Context context, String msg, String msgText, String msgAlert){

            PendingIntent notificationIntent = PendingIntent.getActivity(context,0,new Intent(context,MainActivity.class),0);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(android.R.drawable.arrow_up_float)
                    .setContentTitle(msg)
                    .setTicker(msgAlert)
                    .setContentText(msgText);

            mBuilder.setContentIntent(notificationIntent);
            mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
            mBuilder.setAutoCancel(true);

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.notify(1, mBuilder.build());

        }
    };

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
        }
        return START_STICKY;
    }
}
