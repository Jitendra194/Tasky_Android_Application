package com.example.android.taskreminder.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.taskreminder.R;
import com.example.android.taskreminder.databinding.TaskItemBinding;
import com.example.android.taskreminder.model.TaskEntity;
import com.example.android.taskreminder.ui.activities.MainListActivity;
import com.example.android.taskreminder.ui.activities.TaskDialogActivity;
import com.example.android.taskreminder.ui.callbacks.TaskItemClickCallback;
import com.example.android.taskreminder.ui.utilities.DateAndTimeUtils;
import com.example.android.taskreminder.ui.utilities.Utils;

import java.util.List;

/**
 * Created by jiten on 1/25/2018.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private List<? extends TaskEntity> mTasks;
    private Context context;

    public TaskListAdapter(Context context) {
        this.context = context;
    }

    public void setTaskList(List<? extends TaskEntity> tasks) {
        if (mTasks == null) {
            mTasks = tasks;
            notifyItemRangeInserted(0, mTasks.size());
        } else {
            DiffUtil.DiffResult result = Utils.getDiffResult(mTasks, tasks);
            mTasks = tasks;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TaskItemBinding mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.task_item,
                parent, false);
        mBinding.setClick(clickCallback);
        return new TaskViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        setBindings(holder, position);
    }

    private void setBindings(TaskViewHolder holder, int position) {
        holder.mBinding.setTask(mTasks.get(position));
        if (mTasks.get(position).getTaskTimeAndDate() == 0) {
            setNextReminderDateAndTime(holder, R.string.creation_date_title,
                    mTasks.get(position).getTaskCreation());
        } else {
            setNextReminderDateAndTime(holder, R.string.next_reminder_title,
                    mTasks.get(position).getTaskTimeAndDate());
        }
        holder.mBinding.executePendingBindings();
    }

    private void setNextReminderDateAndTime(TaskViewHolder holder,
                                            int ReminderOrCreationTitle,
                                            long ReminderOrCreationDate) {
        holder.mBinding.remindNextTimeTitle.setText(ReminderOrCreationTitle);
        holder.mBinding.taskNextRemindTime.setText(DateAndTimeUtils
                .getNextReminderDateAndTime(ReminderOrCreationDate));
    }

    @Override
    public int getItemCount() {
        return mTasks == null ? 0 : mTasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {

        final private TaskItemBinding mBinding;

        public TaskViewHolder(TaskItemBinding taskItemBinding) {
            super(taskItemBinding.getRoot());
            mBinding = taskItemBinding;
        }
    }

    private final TaskItemClickCallback clickCallback =
            new TaskItemClickCallback() {
                @Override
                public void onTaskItemClick(View view, TaskEntity taskEntity) {
                    TaskDialogActivity.initialize((MainListActivity) context, view, taskEntity);
                }

                @Override
                public boolean onTaskItemLongClick(View view, TaskEntity taskEntity) {
                    return true;
                }
            };
}
