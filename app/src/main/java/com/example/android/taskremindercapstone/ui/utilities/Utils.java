package com.example.android.taskremindercapstone.ui.utilities;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.util.DisplayMetrics;

import com.example.android.taskremindercapstone.model.TaskEntity;

import java.util.List;
import java.util.Objects;

/**
 * Created by jiten on 2/3/2018.
 */

public class Utils {

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        return (int) (dpWidth / scalingFactor);
    }

//    public static int calculateNextItemId(List<TaskEntity> taskEntities) {
//        if (taskEntities.size() == 0) {
//            return taskEntities.size();
//        } else {
//            taskEntities.sort(Comparator.comparing(TaskEntity::getTaskCreation).reversed());
//            for (int i = 0; i < taskEntities.size(); i++) {
//                if (i != taskEntities.get(taskEntities.size() - (i + 1)).getTaskCreation()) {
//                    return i;
//                }
//            }
//        }
//        return taskEntities.size();
//    }

    public static DiffUtil.DiffResult getDiffResult(List<? extends TaskEntity> mTasks,
                                                    List<? extends TaskEntity> tasks) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return mTasks.size();
            }

            @Override
            public int getNewListSize() {
                return tasks.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return mTasks.get(oldItemPosition).getTaskCreation() ==
                        tasks.get(newItemPosition).getTaskCreation();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                TaskEntity newTask = tasks.get(newItemPosition);
                TaskEntity oldTask = mTasks.get(oldItemPosition);
                return newTask.getTaskCreation() == oldTask.getTaskCreation()
                        && Objects.equals(newTask.getTaskName(), oldTask.getTaskName())
                        && Objects.equals(newTask.getTaskDescription(), oldTask.getTaskDescription());
            }
        });
        return result;
    }

    public static boolean validateReminder(long chosenReminder) {
        return chosenReminder >= DateAndTimeUtils.getCurrentMillis();
    }
}
