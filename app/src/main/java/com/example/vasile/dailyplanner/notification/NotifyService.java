package com.example.vasile.dailyplanner.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.vasile.dailyplanner.ListActivity;
import com.example.vasile.dailyplanner.R;

public class NotifyService extends Service {

    public class ServiceBinder extends Binder {
        NotifyService getService() {
            return NotifyService.this;
        }
    }

    public static final String INTENT_NOTIFY = "com.example.vasile.dailyplanner.notification.INTENT_NOTIFY";
    public static final String NS = "NotifyService";
    public static final String LS = "LocalService";

    private static final int NOTIFICATION = 123;
    private NotificationManager notificationManager;
    private final IBinder binder = new ServiceBinder();

    @Override
    public void onCreate() {
        Log.i(NS, "Created!");
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LS, "Recieved start id" + startId + ": " + intent);
        if (intent.getBooleanExtra(INTENT_NOTIFY, false)) {
            showNotification();
        }

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private void showNotification() {
        CharSequence title = "Alarm!!";
        CharSequence text = "Your notification.";
        int icon = R.drawable.ic_launcher_background;
        //long time = System.currentTimeMillis();

        Notification.Builder builder = new Notification.Builder(this);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, ListActivity.class), 0);
        builder.setSmallIcon(icon)
               .setContentTitle(title)
               .setContentText(text)
               .setContentIntent(contentIntent)
               .setAutoCancel(true)
               .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager.notify(NOTIFICATION, builder.build());
        stopSelf();
    }
}
