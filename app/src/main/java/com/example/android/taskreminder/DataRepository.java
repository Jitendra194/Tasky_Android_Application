package com.example.android.taskreminder;

import android.content.Context;
import android.database.Cursor;

import com.example.android.taskreminder.db.TaskDatabase;
import com.example.android.taskreminder.model.TaskEntity;

import java.util.List;

/**
 * Created by jiten on 1/26/2018.
 */

public class DataRepository {

    private TaskDatabase taskDatabase;
    private AppExecutors executors;

    public DataRepository(TaskDatabase taskDatabase, AppExecutors executors) {
        this.taskDatabase = taskDatabase;
        this.executors = executors;
    }

    public List<TaskEntity> getTasks(Cursor cursor) {
        return taskDatabase.loadTasks(cursor);
    }

    public TaskEntity getTask(long taskId) {
        return taskDatabase.loadTask(taskId, executors);
    }

    public void insertTask(TaskEntity taskEntity) {
        taskDatabase.insertTask(taskEntity, executors);
    }

    public void deleteTask(long taskId) {
        taskDatabase.deleteTask(taskId, executors);
    }

    public void updateTask(Context context, TaskEntity taskEntity) {
        taskDatabase.updateTask(context, taskEntity);
    }
}
