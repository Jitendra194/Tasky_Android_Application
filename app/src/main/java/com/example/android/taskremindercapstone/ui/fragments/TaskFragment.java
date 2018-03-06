package com.example.android.taskremindercapstone.ui.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.taskremindercapstone.R;
import com.example.android.taskremindercapstone.databinding.FragmentTaskBinding;
import com.example.android.taskremindercapstone.model.TaskEntity;
import com.example.android.taskremindercapstone.ui.utilities.DateAndTimeUtils;
import com.example.android.taskremindercapstone.viewmodel.TaskViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {

    public static final String TAG = TaskFragment.class.getSimpleName();

    private FragmentTaskBinding mBinding;

    private TaskViewModel viewModel;

    private String mDate;

    private String mTime;

    public TaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_task, container, false);
        setHasOptionsMenu(true);
        setBottomNavigationListener();
        return mBinding.getRoot();
    }

    private void setBottomNavigationListener() {
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_time:
                    TimePickerDialog.OnTimeSetListener onTimeSetListener =
                            (view, hourOfDay, minute) -> {
                                mTime = DateAndTimeUtils.getTime(hourOfDay, minute);
                                Log.v(TAG, mTime);
                            };
                    DateAndTimeUtils.showTime(getContext(), onTimeSetListener);
                    break;
                case R.id.action_day:
                    DatePickerDialog.OnDateSetListener onDateSetListener =
                            (view, year, month, dayOfMonth) -> {
                                mDate = DateAndTimeUtils.getDate(year, month + 1, dayOfMonth);
                                Log.v(TAG, mDate);
                            };
                    DateAndTimeUtils.showCalendar(getContext(), onDateSetListener);
                    break;
                case R.id.action_frequency:
                    Toast.makeText(getContext(), "FREQUENCY", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    public static TaskFragment instantiate() {
        return new TaskFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new TaskViewModel(getActivity().getApplication());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_task_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveTask();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
        }
        return false;
    }

    private void saveTask() {
        Log.v(TAG, "IMPORTANT" + mTime + "  " + mDate);
        if (!TextUtils.isEmpty(mBinding.taskNameEditText.getText())) {
            TaskEntity taskEntity = new TaskEntity(
                    mBinding.taskNameEditText.getText().toString(),
                    mBinding.taskDescriptionEditText.getText().toString(),
                    DateAndTimeUtils.convertDateAndTimeToMillis(mDate, mTime),
                    DateAndTimeUtils.getCreationMillis());
            viewModel.insertTask(taskEntity);
            getActivity().finish();
        } else {
            Toast.makeText(getContext(), "EMPTY", Toast.LENGTH_SHORT).show();
        }
    }
}
