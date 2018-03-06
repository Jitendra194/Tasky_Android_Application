package com.example.android.taskremindercapstone.ui.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.taskremindercapstone.R;
import com.example.android.taskremindercapstone.model.TaskEntity;
import com.example.android.taskremindercapstone.ui.activities.MainListActivity;

/**
 * Created by jiten on 2/27/2018.
 */

public class TaskDialogFragment extends DialogFragment {

//    private TaskFragmentDialogBinding mBinding;

    public static TaskDialogFragment getInstance(TaskEntity taskEntity) {
        return new TaskDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        mBinding = DataBindingUtil.inflate(inflater, R.layout.task_dialog,
//                container, false);
//        return mBinding.getRoot();
        return null;
    }

//    public static void showTaskDialog(Context context, TaskEntity taskEntity) {
//        FragmentTransaction transaction = ((MainListActivity) context)
//                .getSupportFragmentManager().beginTransaction();
//        Fragment previous = ((MainListActivity) context).getSupportFragmentManager()
//                .findFragmentById(R.id.task_dialog);
//        if (previous != null) {
//            transaction.remove(previous);
//        }
//        transaction.addToBackStack(null);
//
//        DialogFragment newFragment = TaskDialogFragment.getInstance(taskEntity);
//        newFragment.show(transaction, context.getString(R.string.tag_dialog));
//    }
}
