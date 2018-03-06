package com.example.android.taskremindercapstone.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.taskremindercapstone.R;
import com.example.android.taskremindercapstone.db.TaskContract;
import com.example.android.taskremindercapstone.ui.fragments.TaskFragment;

public class TaskActivity extends AppCompatActivity {

    private TaskFragment taskFragment;
    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        if (savedInstanceState == null) {
            if (taskFragment == null) {
                taskFragment = TaskFragment.instantiate();
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_task_container,
                            taskFragment,
                            TaskFragment.TAG)
                    .commit();
        }
    }
}
