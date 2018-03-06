package com.example.android.taskremindercapstone.ui.callbacks;

import android.view.View;

import com.example.android.taskremindercapstone.model.TaskEntity;

/**
 * Created by jiten on 1/26/2018.
 */

public interface TaskItemClickCallback {
    void onTaskItemClick(View view, TaskEntity taskEntity);
    boolean onTaskItemLongClick(View view, TaskEntity taskEntity);
}
