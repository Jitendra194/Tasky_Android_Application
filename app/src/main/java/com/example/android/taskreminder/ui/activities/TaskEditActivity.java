package com.example.android.taskreminder.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.taskreminder.R;
import com.example.android.taskreminder.db.TaskContract;
import com.example.android.taskreminder.ui.fragments.TaskEditFragment;

public class TaskEditActivity extends AppCompatActivity {

    private TaskEditFragment taskEditFragment;

    public static final String ACTION_ADD = "ACTION_ADD";
    public static final String ACTION_EDIT = "ACTION_EDIT";
    public static final String GET_ACTION = "GET_ACTION";

    public static final long DEFAULT_TASK_ID = 0;

    private static final String TAG = TaskEditActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);

        Intent intent = getIntent();
        if (ACTION_ADD.equals(intent.getAction())) {
            setFragment(ACTION_ADD, savedInstanceState, DEFAULT_TASK_ID);
        } else if (ACTION_EDIT.equals(intent.getAction())) {
            if (intent.getExtras() != null) {
                setFragment(ACTION_EDIT, savedInstanceState, intent.getExtras()
                        .getLong(TaskContract.TaskEntry.COLUMN_TASK_CREATION_DATE));
            }
        }
    }

    private void setFragment(String action, Bundle savedInstanceState, long taskId) {
        if (savedInstanceState == null) {
            if (taskEditFragment == null) {
                taskEditFragment = TaskEditFragment.instantiate(action, taskId);
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_task_edit_container,
                            taskEditFragment,
                            TaskEditFragment.TAG)
                    .commit();
        }
    }
}