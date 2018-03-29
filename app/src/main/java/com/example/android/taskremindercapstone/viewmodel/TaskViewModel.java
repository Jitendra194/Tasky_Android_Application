package com.example.android.taskremindercapstone.viewmodel;

import android.app.Application;
import android.database.Cursor;

import com.example.android.taskremindercapstone.BaseApplication;
import com.example.android.taskremindercapstone.DataRepository;
import com.example.android.taskremindercapstone.background.BackgroundTaskUtils;
import com.example.android.taskremindercapstone.background.alarms.AlarmManagerClass;
import com.example.android.taskremindercapstone.model.TaskEntity;

import java.util.List;

/**
 * Created by jiten on 1/24/2018.
 */

public class TaskViewModel {

    private DataRepository dataRepository;
    private BackgroundTaskUtils backgroundTaskUtils;
    private AlarmManagerClass alarmManagerClass;

    public TaskViewModel(Application application) {

        dataRepository = ((BaseApplication) application).getRepository();
        backgroundTaskUtils = ((BaseApplication) application).getBackgroundTaskUtils();
        alarmManagerClass = ((BaseApplication) application).getAlarmManagerClass();
    }

    public List<TaskEntity> loadTasks(Cursor cursor) {
        return dataRepository.getTasks(cursor);
    }

    public TaskEntity loadTask(long taskId) {
        return dataRepository.getTask(taskId);
    }

    public void insertTask(TaskEntity taskEntity) {
        alarmManagerClass.setAlarmService(taskEntity);
        dataRepository.insertTask(taskEntity);
    }

    public void deleteTask(long taskTId) {
        dataRepository.deleteTask(taskTId);
    }

    public BackgroundTaskUtils getBackgroundTaskUtils() {
        return backgroundTaskUtils;
    }
}
