package com.example.android.taskremindercapstone.background;

import android.content.Context;
import android.util.Log;

import com.example.android.taskremindercapstone.model.TaskEntity;
import com.example.android.taskremindercapstone.ui.utilities.DateAndTimeUtils;

import java.util.List;

/**
 * Created by jiten on 2/3/2018.
 */

public class BackgroundTaskUtils {

    private Context context;

    public BackgroundTaskUtils(Context context) {
        this.context = context;
    }

    public void initializeFJD(List<TaskEntity> data) {
//        Log.v("FJD", "Size of List:" + String.valueOf(data.size()));
//
//        for (int i = 0; i < data.size(); i++) {
//            if (data.get(i).getTaskTime() != null || data.get(i).getTaskDate() != null) {
//                Log.v("FJD-TIME",
//                        data.get(i).getTaskTime() + " " + data.get(i).getTaskDate());
//                FirebaseDispatcher.scheduleFirebaseJobDispatcher(context,
//                        DateAndTimeUtils.getRemainingTime(data.get(i).getTaskTime(),
//                                data.get(i).getTaskDate()), data.get(i).getTId());
//            }
//            Log.v("FJD", data.get(i).getTaskName());
//        }
    }

    public static void syncDatabase(Context context, int taskId) {
        Log.v("NOTIFICATION", "ASYNC_TASK_STARTED: " + taskId);
//        TaskEntity taskEntity = ((BaseApplication) context).getRepository().getTask(taskId);
//
//        NotificationClass.setNotifications(context, taskEntity);
    }
}
