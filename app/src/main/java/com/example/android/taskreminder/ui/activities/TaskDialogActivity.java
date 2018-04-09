package com.example.android.taskreminder.ui.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.android.taskreminder.R;
import com.example.android.taskreminder.databinding.TaskDialogBinding;
import com.example.android.taskreminder.db.TaskContract;
import com.example.android.taskreminder.model.TaskEntity;
import com.example.android.taskreminder.ui.callbacks.EditTaskClickCallback;
import com.example.android.taskreminder.ui.transitions.TaskItemTransform;
import com.example.android.taskreminder.ui.utilities.DateAndTimeUtils;

/**
 * Created by jiten on 2/28/2018.
 */

public class TaskDialogActivity extends AppCompatActivity {

    private TaskDialogBinding mBinding;
    private long mTaskCreationId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.task_dialog);
        mBinding.setEditTask(clickCallback);

        //**OLD Animation**//
//        TaskItemTransform.setUp(this, mBinding.container1, mBinding.container2);

        //**NEW CARD TRANSITION**//
//        FadeInDialogTransition.setUp(this,mBinding.container2);

        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            setDialogBindings(intent);
        }
    }

    private void setDialogBindings(Intent intent) {
        mBinding.taskTitle.setText(intent.getExtras().getString(TaskContract.TaskEntry.COLUMN_TASK_NAME));
        mBinding.taskDescription.setText(intent.getExtras().getString(TaskContract.TaskEntry.COLUMN_TASK_DESCRIPTION));
        mTaskCreationId = intent.getExtras().getLong(TaskContract.TaskEntry.COLUMN_TASK_CREATION_DATE);
        mBinding.taskCreatedOn.setText("Created on " +
                DateAndTimeUtils.getNextReminderDateAndTime(mTaskCreationId));
        mBinding.taskReminderTime.setText("Next Reminder on " +
                DateAndTimeUtils.getNextReminderDateAndTime(
                        intent.getExtras().getLong(TaskContract.TaskEntry.COLUMN_TASK_TIME_AND_DATE))
        );
    }

    private final EditTaskClickCallback clickCallback =
            new EditTaskClickCallback() {
                @Override
                public void onEditClick() {
                    Intent intent = new Intent(TaskDialogActivity.this,
                            TaskEditActivity.class);
                    intent.putExtra(TaskContract.TaskEntry.COLUMN_TASK_CREATION_DATE, mTaskCreationId);
                    intent.setAction(TaskEditActivity.ACTION_EDIT);
                    startActivity(intent);
                    finish();
                }
            };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static void initialize(Context context, View view, TaskEntity taskEntity) {
        Intent intent = getTaskDialogActivityIntent(context, taskEntity);

        TaskItemTransform.addExtras(intent, ContextCompat.getColor(
                context, android.R.color.white
        ), taskEntity.getTaskName(), taskEntity.getTaskDescription());

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                (MainListActivity) context, view, context.getString(R.string.dialog_transition));
        ActivityCompat.startActivityForResult((MainListActivity) context,
                intent, 1, options.toBundle());
    }

    public static Intent getTaskDialogActivityIntent(Context context, TaskEntity taskEntity) {
        Intent intent = new Intent((MainListActivity) context, TaskDialogActivity.class);
        intent.putExtra(TaskContract.TaskEntry.COLUMN_TASK_NAME, taskEntity.getTaskName());
        intent.putExtra(TaskContract.TaskEntry.COLUMN_TASK_DESCRIPTION, taskEntity.getTaskDescription());
        intent.putExtra(TaskContract.TaskEntry.COLUMN_TASK_CREATION_DATE, taskEntity.getTaskCreation());
        intent.putExtra(TaskContract.TaskEntry.COLUMN_TASK_TIME_AND_DATE, taskEntity.getTaskTimeAndDate());
        return intent;
    }
}
