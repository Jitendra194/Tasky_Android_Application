package com.example.android.taskremindercapstone.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.taskremindercapstone.R;
import com.example.android.taskremindercapstone.db.TaskContract;
import com.example.android.taskremindercapstone.ui.fragments.TaskListFragment;

public class
MainListActivity extends AppCompatActivity {

    private TaskListFragment taskListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            if (taskListFragment == null) {
                taskListFragment = TaskListFragment.instantiate();
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_task_list_container,
                            taskListFragment,
                            TaskListFragment.TAG)
                    .commit();
        }
    }

    public void start() {
        Intent intent = new Intent(this, TaskActivity.class);
//        intent.putExtra(TaskContract.TaskEntry.COLUMN_TASK_CREATION_DATE, taskId);
        startActivity(intent);
    }
}
