package com.example.android.taskremindercapstone.ui.activities;

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

import com.example.android.taskremindercapstone.R;
import com.example.android.taskremindercapstone.databinding.TaskDialogBinding;
import com.example.android.taskremindercapstone.model.TaskEntity;
import com.example.android.taskremindercapstone.ui.transitions.TaskItemTransform;

/**
 * Created by jiten on 2/28/2018.
 */

public class TaskDialogActivity extends AppCompatActivity {

    private TaskDialogBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.task_dialog);

        TaskItemTransform.setUp(this, mBinding.container1, mBinding.container2);
    }

    @Override
    public void onBackPressed() {
        if (mBinding.container1.getVisibility() == View.GONE) {
            mBinding.container2.setVisibility(View.GONE);
            mBinding.container1.setVisibility(View.VISIBLE);
            super.onBackPressed();
        }
    }

    public static void initialize(Context context, View view, TaskEntity taskEntity) {
        Intent intent = new Intent((MainListActivity) context, TaskDialogActivity.class);
        TaskItemTransform.addExtras(intent, ContextCompat.getColor(
                context, android.R.color.white
        ), taskEntity.getTaskName(), taskEntity.getTaskDescription());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                (MainListActivity) context, view, context.getString(R.string.dialog_transition));
        ActivityCompat.startActivityForResult((MainListActivity) context,
                intent, 1, options.toBundle());
    }
}
