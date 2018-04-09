package com.example.android.taskreminder.ui.utilities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.taskreminder.db.TaskContract.TaskEntry;
import com.example.android.taskreminder.model.TaskEntity;
import com.example.android.taskreminder.ui.fragments.TaskEditFragment;
import com.example.android.taskreminder.viewmodel.TaskViewModel;

public class TaskLoader extends AsyncTaskLoader<TaskEntity> {

    private int id;
    private long taskId;
    private TaskViewModel viewModel;

    private TaskEntity taskEntity;

    public TaskLoader(@NonNull Context context, int id) {
        super(context);
        this.id = id;
    }

    public TaskLoader(@NonNull Context context, int id, Bundle bundle, TaskViewModel viewModel) {
        super(context);

        this.id = id;
        this.viewModel = viewModel;

        taskId = bundle.getLong(TaskEntry.COLUMN_TASK_CREATION_DATE);
    }

    @Nullable
    @Override
    public TaskEntity loadInBackground() {
        if (id == TaskEditFragment.LOADER_ID_ADD) {
            return taskEntity;
        } else if (id == TaskEditFragment.LOADER_ID_EDIT) {
            taskEntity = viewModel.loadTask(taskId);
            return taskEntity;
        }
        return taskEntity;
    }

    @Override
    public void deliverResult(@Nullable TaskEntity data) {
        taskEntity = data;
        super.deliverResult(data);
    }
}
