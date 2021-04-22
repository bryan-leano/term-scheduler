package com.example.term_scheduler_bryanleano;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class ReminderBroadcast extends BroadcastReceiver {

    static int notificationID;
    String channel_id = "test";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getStringExtra("key").equals("Course start date notification")) {
            System.out.println("Added course start notification");
            Toast.makeText(context, intent.getStringExtra("key"), Toast.LENGTH_LONG).show();
            createNotificationChannel(context, channel_id);
            Notification n = new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(intent.getStringExtra("key"))
                    .setContentTitle("Course starts today!").build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationID++, n);
        } else if (intent.getStringExtra("key").equals("Course end date notification")) {
            System.out.println("Added course end notification");
            Toast.makeText(context, intent.getStringExtra("key"), Toast.LENGTH_LONG).show();
            createNotificationChannel(context, channel_id);
            Notification n = new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(intent.getStringExtra("key"))
                    .setContentTitle("Course ends today!").build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationID++, n);
        } else if (intent.getStringExtra("key").equals("Assessment start date notification")) {
            System.out.println("Added assessment start notification");
            Toast.makeText(context, intent.getStringExtra("key"), Toast.LENGTH_LONG).show();
            createNotificationChannel(context, channel_id);
            Notification n = new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(intent.getStringExtra("key"))
                    .setContentTitle("Assessment starts today!").build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationID++, n);
        } else if (intent.getStringExtra("key").equals("Assessment end date notification")) {
            System.out.println("Added assessment end notification");
            Toast.makeText(context, intent.getStringExtra("key"), Toast.LENGTH_LONG).show();
            createNotificationChannel(context, channel_id);
            Notification n = new NotificationCompat.Builder(context, channel_id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(intent.getStringExtra("key"))
                    .setContentTitle("Assessment ends today!").build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationID++, n);
        }

    }


    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel";
            String description = "channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}