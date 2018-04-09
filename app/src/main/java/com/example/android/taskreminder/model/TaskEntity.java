package com.example.android.taskreminder.model;

import android.support.annotation.NonNull;

/**
 * Created by jiten on 1/24/2018.
 */

public class TaskEntity {

    private String taskName;
    private String taskDescription;
    private long taskTimeAndDate;
    private long taskCreation;

    public TaskEntity() {
    }

    public TaskEntity(String taskName, String taskDescription
            , long taskTimeAndDate, long taskCreation) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskTimeAndDate = taskTimeAndDate;
        this.taskCreation = taskCreation;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public long getTaskTimeAndDate() {
        return taskTimeAndDate;
    }

    public long getTaskCreation() {
        return taskCreation;
    }

    public void setTaskName(@NonNull String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setTaskTimeAndDate(long taskTimeAndDate) {
        this.taskTimeAndDate = taskTimeAndDate;
    }

    public void setTaskCreation(long taskCreation) {
        this.taskCreation = taskCreation;
    }
}
