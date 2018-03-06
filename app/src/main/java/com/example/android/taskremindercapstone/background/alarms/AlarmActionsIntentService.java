package com.example.android.taskremindercapstone.background.alarms;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.example.android.taskremindercapstone.BaseApplication;
import com.example.android.taskremindercapstone.model.TaskEntity;

import java.util.Objects;

import static com.example.android.taskremindercapstone.background.alarms.AlarmReceiver.ACTION_DONE;
import static com.example.android.taskremindercapstone.background.alarms.AlarmReceiver.ACTION_SNOOZE;

/**
 * Created by jiten on 2/25/2018.
 */

public class AlarmActionsIntentService extends JobIntentService {

    static final int JOB_ID = 1000;
    private long taskId_creationDate;
    private long taskSnoozeWindow;
    private String taskName;
    private String taskDescription;

    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, AlarmActionsIntentService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        if (intent.getExtras() != null) {
            getExtrasFromIntent(intent);
        }

        TaskEntity taskEntity = new TaskEntity(
                taskName, taskDescription, taskSnoozeWindow, taskId_creationDate);

        if (Objects.equals(intent.getAction(), ACTION_SNOOZE)) {
            AlarmManagerClass alarmManagerClass = new AlarmManagerClass(getApplicationContext());
            alarmManagerClass.setAlarmService(taskEntity);
        }
        ((BaseApplication) getApplication())
                .getRepository()
                .updateTask(getApplicationContext(),taskEntity);
    }

    private void getExtrasFromIntent(Intent intent) {
        taskId_creationDate = intent.getExtras().getLong(AlarmManagerClass.TASK_ID);
        taskSnoozeWindow = intent.getExtras().getLong(AlarmManagerClass.TASK_SNOOZE_WINDOW);
        taskName = intent.getExtras().getString(AlarmManagerClass.TASK_NAME);
        taskDescription = intent.getExtras().getString(AlarmManagerClass.TASK_DESCRIPTION);
    }
}
