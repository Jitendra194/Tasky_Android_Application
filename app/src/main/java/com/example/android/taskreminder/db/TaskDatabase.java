package com.example.android.taskreminder.db;

import android.content.Context;
import android.database.Cursor;

import com.example.android.taskreminder.AppExecutors;
import com.example.android.taskreminder.model.TaskEntity;

import java.util.List;

/**
 * Created by jiten on 1/25/2018.
 */

public class TaskDatabase {

    private Context context;

    public TaskDatabase(Context context) {
        this.context = context;
    }


    public void insertTask(TaskEntity taskEntity, AppExecutors executors) {
        executors.diskIO().execute(() -> DataIOFunctions.insertTask(context, taskEntity));
    }

    public void deleteTask(long taskId, AppExecutors appExecutors) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                DataIOFunctions.deleteTask(context, taskId);
            }
        });
    }

    public List<TaskEntity> loadTasks(Cursor cursor) {
        return DataIOFunctions.getTasks(cursor);
    }

    public TaskEntity loadTask(long taskId, AppExecutors executors) {
        return DataIOFunctions.getTask(context, taskId);
    }

    public void updateTask(TaskEntity taskEntity, AppExecutors appExecutors) {
        appExecutors.diskIO().execute(() -> DataIOFunctions.updateTask(context, taskEntity));
    }
}
