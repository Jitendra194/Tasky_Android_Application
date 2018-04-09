package com.example.android.taskreminder.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.android.taskreminder.R;
import com.example.android.taskreminder.background.alarms.AlarmManagerClass;
import com.example.android.taskreminder.background.alarms.AlarmReceiver;
import com.example.android.taskreminder.ui.utilities.DateAndTimeUtils;

/**
 * Created by jiten on 2/5/2018.
 */

public class NotificationClass {

    public static NotificationManager notificationManager;

    public static void setNotifications(Context context, long taskId,
                                        String taskName, String taskDescription) {
        notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        initChannels(notificationManager);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "default")
                        .setContentTitle(taskName)
                        .setChannelId("default")
                        .setContentText(taskDescription)
                        .setColor(Color.BLUE)
                        .setSmallIcon(R.drawable.ic_done)
                        .setPriority(NotificationManager.IMPORTANCE_HIGH)
                        .addAction(R.drawable.ic_done, "DONE",
                                setDonePendingIntent(context, taskId, taskName, taskDescription))
                        .addAction(R.drawable.ic_snooze, "SNOOZE",
                                setSnoozePendingIntent(context, taskId, taskName, taskDescription))
                        .setAutoCancel(true);

        if (notificationManager != null) {
            notificationManager.notify((int) taskId, builder.build());
        }
    }

    private static void initChannels(NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationChannel channel = new NotificationChannel("default",
                "Task Reminders",
                NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Reminds you of tasks");
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }

    private static PendingIntent setSnoozePendingIntent(Context context,
                                                        long taskId, String taskName,
                                                        String taskDescription) {

        return PendingIntent.getBroadcast(context, (int) taskId,
                getIntent(context, taskId, taskName, taskDescription,
                        DateAndTimeUtils.getSnoozeWindow(), AlarmReceiver.ACTION_SNOOZE),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static PendingIntent setDonePendingIntent(Context context,
                                                      long taskId, String taskName,
                                                      String taskDescription) {
        return PendingIntent.getBroadcast(context, (int) taskId,
                getIntent(context, taskId, taskName, taskDescription,
                        0, AlarmReceiver.ACTION_DONE),
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Intent getIntent(Context context,
                                    long taskId, String taskName,
                                    String taskDescription,
                                    long snoozeWindow,
                                    String action) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(AlarmManagerClass.TASK_ID, taskId);
        intent.putExtra(AlarmManagerClass.TASK_NAME, taskName);
        intent.putExtra(AlarmManagerClass.TASK_DESCRIPTION, taskDescription);
        intent.putExtra(AlarmManagerClass.TASK_SNOOZE_WINDOW, snoozeWindow);
        intent.setAction(action);
        return intent;
    }
}
