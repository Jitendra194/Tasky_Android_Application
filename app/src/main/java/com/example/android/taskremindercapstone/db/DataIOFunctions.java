package com.example.android.taskremindercapstone.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.android.taskremindercapstone.db.TaskContract.TaskEntry;
import com.example.android.taskremindercapstone.model.TaskEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiten on 2/1/2018.
 */

public class DataIOFunctions {

    private static final String TAG = DataIOFunctions.class.getSimpleName();

    public static final Uri taskUri = TaskContract.TaskEntry.CONTENT_URI;

    public static final String[] TASK_PROJECTION = {
            TaskEntry.COLUMN_TASK_NAME,
            TaskEntry.COLUMN_TASK_DESCRIPTION,
            TaskEntry.COLUMN_TASK_TIME_AND_DATE,
            TaskEntry.COLUMN_TASK_CREATION_DATE};

    public static void insertTask(Context context, TaskEntity taskEntity) {
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_TASK_NAME, taskEntity.getTaskName());
        values.put(TaskEntry.COLUMN_TASK_DESCRIPTION, taskEntity.getTaskDescription());
        values.put(TaskEntry.COLUMN_TASK_TIME_AND_DATE, taskEntity.getTaskTimeAndDate());
        values.put(TaskEntry.COLUMN_TASK_CREATION_DATE, taskEntity.getTaskCreation());
        Uri insertedUri = context.getContentResolver().insert(taskUri, values);
    }

    public static List<TaskEntity> getTasks(Cursor cursor) {
        List<TaskEntity> taskEntities = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                taskEntities.add(setTaskData(cursor));
            } while (cursor.moveToNext());
        }
        return taskEntities;
    }

    public static TaskEntity getTask(Context context, long taskId) {
        Cursor cursor = context.getContentResolver().query(
                TaskEntry.getTaskIdUri(String.valueOf(taskId)),
                TASK_PROJECTION,
                null,
                null,
                null
        );
        if (cursor != null) {
            cursor.moveToFirst();
            return setTaskData(cursor);
        }
        return null;
    }

    public static void deleteTask(Context context, long taskId) {
        int deletedRows = context.getContentResolver()
                .delete(getSpecificTaskUri(taskId), null, null);
    }

    public static void updateTask(Context context, TaskEntity taskEntity) {
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_TASK_TIME_AND_DATE, taskEntity.getTaskTimeAndDate());
        int updatedRows = context.getContentResolver().update(
                getSpecificTaskUri(taskEntity.getTaskCreation()),
                values,
                null,
                null
        );
        Log.v("UPDATE", String.valueOf(updatedRows));
    }

    public static Uri getSpecificTaskUri(long taskId) {
        return taskUri.buildUpon()
                .appendPath(String.valueOf(taskId))
                .build();
    }

    private static TaskEntity setTaskData(Cursor cursor) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTaskName(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_NAME)));
        taskEntity.setTaskDescription(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_DESCRIPTION)));
        taskEntity.setTaskTimeAndDate(cursor.getLong(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_TIME_AND_DATE)));
        taskEntity.setTaskCreation(cursor.getLong(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_CREATION_DATE)));
        return taskEntity;
    }
}
