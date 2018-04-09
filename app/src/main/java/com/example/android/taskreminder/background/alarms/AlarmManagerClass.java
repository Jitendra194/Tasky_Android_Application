package com.example.android.taskreminder.background.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.android.taskreminder.model.TaskEntity;

import java.util.Calendar;

/**
 * Created by jiten on 2/13/2018.
 */

public class AlarmManagerClass {

    private AlarmManager alarmManager;
    private Context context;

    public static final String TASK_NAME = "TASK_NAME";
    public static final String TASK_DESCRIPTION = "TASK_DESCRIPTION";
    public static final String TASK_ID = "TASK_ID";
    public static final String TASK_SNOOZE_WINDOW = "TASK_SNOOZE_WINDOW";

    public AlarmManagerClass(Context context) {
        this.context = context;
    }

    public void setAlarmService(TaskEntity taskEntity) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(TASK_NAME, taskEntity.getTaskName());
        intent.putExtra(TASK_DESCRIPTION, taskEntity.getTaskDescription());
        intent.putExtra(TASK_ID, taskEntity.getTaskCreation());
        setAlarmManager((int) taskEntity.getTaskCreation(), taskEntity.getTaskTimeAndDate(), intent);
    }

    private void setAlarmManager(int pendingIntentID, long alarmManagerTime, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                pendingIntentID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    alarmManagerTime, pendingIntent);
        }
    }
}
