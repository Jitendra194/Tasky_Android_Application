package com.example.android.taskreminder.background.alarms;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.android.taskreminder.notifications.NotificationClass;

/**
 * Created by jiten on 2/10/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {

    public static final String TAG = AlarmReceiver.class.getSimpleName();

    private long taskID;
    private String taskName;
    private String taskDescription;

    public static final String ACTION_SNOOZE = "ACTION_SNOOZE";
    public static final String ACTION_DONE = "ACTION_DONE";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null) {
            action(context, intent);
        } else {
            getIntentExtras(intent);
            setNotification(context);
        }
    }

    private void getIntentExtras(Intent intent) {
        if (intent.getExtras() != null) {
            taskID = intent.getExtras().getLong(AlarmManagerClass.TASK_ID);
            taskName = intent.getExtras().getString(AlarmManagerClass.TASK_NAME);
            taskDescription = intent.getExtras().getString(AlarmManagerClass.TASK_DESCRIPTION);
        }
    }

    private void setNotification(Context context) {
        NotificationClass.setNotifications(context,
                taskID,
                taskName,
                taskDescription);
    }

    private NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private void action(Context context, Intent intent) {
        getNotificationManager(context)
                .cancel((int) intent.getExtras().getLong(AlarmManagerClass.TASK_ID));
        AlarmActionsIntentService.enqueueWork(context, intent);
    }
}