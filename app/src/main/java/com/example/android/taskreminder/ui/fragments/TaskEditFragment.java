package com.example.android.taskreminder.ui.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.taskreminder.R;
import com.example.android.taskreminder.databinding.FragmentTaskEditBinding;
import com.example.android.taskreminder.db.TaskContract.TaskEntry;
import com.example.android.taskreminder.model.TaskEntity;
import com.example.android.taskreminder.ui.activities.TaskEditActivity;
import com.example.android.taskreminder.ui.callbacks.AddNewTaskClickCallback;
import com.example.android.taskreminder.ui.callbacks.SetTimeCallback;
import com.example.android.taskreminder.ui.utilities.DateAndTimeUtils;
import com.example.android.taskreminder.ui.utilities.TaskLoader;
import com.example.android.taskreminder.ui.utilities.Utils;
import com.example.android.taskreminder.viewmodel.TaskViewModel;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskEditFragment extends Fragment implements LoaderManager.LoaderCallbacks<TaskEntity> {

    public static final String TAG = TaskEditFragment.class.getSimpleName();
    public static final int LOADER_ID_ADD = 1;
    public static final int LOADER_ID_EDIT = 2;
    private FragmentTaskEditBinding mBinding;
    private String mDate;
    private String mTime;
    private TaskViewModel viewModel;

    public TaskEditFragment() {
        // Empty public constructor
    }

    private final AddNewTaskClickCallback clickCallback =
            new AddNewTaskClickCallback() {
                @Override
                public void onFabClick() {
                    if (mTime == null) {
                        Log.v("DATE", "Date is null!");
                        Snackbar.make(mBinding.fragmentTaskEditCoordinatorLayout,
                                "Please select time to remind",
                                Snackbar.LENGTH_LONG).show();
                        return;
                    }
                    if (mDate == null) {
                        Snackbar.make(mBinding.fragmentTaskEditCoordinatorLayout,
                                "Please select the date to remind",
                                Snackbar.LENGTH_LONG).show();
                        return;
                    }
                    if (Utils.validateReminder(DateAndTimeUtils
                            .convertDateAndTimeToMillis(mDate, mTime))) {
                        saveTask();
                    } else {
                        Snackbar.make(mBinding.fragmentTaskEditCoordinatorLayout,
                                "Reminder cannot be set in the past!",
                                Snackbar.LENGTH_LONG).show();
                    }
                }
            };

    private SetTimeCallback setTimeCallback =
            new SetTimeCallback() {
                @Override
                public void itemOneClick() {
                    TimePickerDialog.OnTimeSetListener onTimeSetListener =
                            (view, hourOfDay, minute) -> {
                                mTime = DateAndTimeUtils
                                        .getFormattedTime(DateAndTimeUtils.getTime(hourOfDay, minute));
                                mBinding.extraDetails.fesi1Subtitle
                                        .setText(mTime);
                                Log.v("TIME", mTime);
//                                setNextReminderAppbarTitle();
                            };
                    DateAndTimeUtils.showTime(getContext(), onTimeSetListener);
                }

                @Override
                public void itemTwoClick() {
                    DatePickerDialog.OnDateSetListener onDateSetListener =
                            (view, year, month, dayOfMonth) -> {
                                mDate = DateAndTimeUtils.getDate(year, month + 1, dayOfMonth);
                                mBinding.extraDetails.fesi2Subtitle
                                        .setText(DateAndTimeUtils.getFormattedDate(mDate));
//                                setNextReminderAppbarTitle();
                                Log.v(TAG, mDate);
                            };
                    DateAndTimeUtils.showCalendar(getContext(), onDateSetListener);
                }

                @Override
                public void itemThreeClick() {
                    Toast.makeText(getContext(), "FREQUENCY", Toast.LENGTH_SHORT).show();
                }
            };

    public static TaskEditFragment instantiate(String ACTION_MODE, long taskId) {
        Bundle bundle = new Bundle();
        if (taskId != TaskEditActivity.DEFAULT_TASK_ID) {
            bundle.putLong(TaskEntry.COLUMN_TASK_CREATION_DATE, taskId);
        }
        bundle.putString(TaskEditActivity.GET_ACTION, ACTION_MODE);
        TaskEditFragment fragment = new TaskEditFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_edit,
                container, false);

        setActionbar();

        initializeCallbacks();

        return mBinding.getRoot();
    }

    private void initializeViewModel() {
        viewModel = new TaskViewModel(getActivity().getApplication());
    }

    private void setActionbar() {
        if (((TaskEditActivity) getActivity()) != null) {
            ((TaskEditActivity) getActivity()).setSupportActionBar(mBinding.fragmentTaskEditToolbar);
            Objects.requireNonNull(((TaskEditActivity) getActivity())
                    .getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(((TaskEditActivity) getActivity())
                    .getSupportActionBar()).setTitle("");
        }
    }

    private void initializeCallbacks() {
        mBinding.setFabClick(clickCallback);
        mBinding.extraDetails.setItemClick(setTimeCallback);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if ((TaskEditActivity) getActivity() != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                    return true;
                }
        }
        return false;
    }

//    private void setNextReminderAppbarTitle() {
//        mBinding.appbarTitleLayout.fragmentTaskEditNextReminderTitle1.setText("Next Reminder");
//        if (mDate == null) {
//            setReminderAppbarTitle(DateAndTimeUtils.getFormattedTime(mTime));
//        } else if (mTime == null) {
//            setReminderAppbarTitle(DateAndTimeUtils.getFormattedDate(mDate));
//        } else {
//            setReminderAppbarTitle(DateAndTimeUtils.getFormattedTime(mTime) + " "
//                    + DateAndTimeUtils.getFormattedDate(mDate));
//        }
//    }

//    private void setReminderAppbarTitle(String timeAndDate) {
//        mBinding.appbarTitleLayout
//                .fragmentTaskEditNextReminderTitleNextReminderDetails
//                .setText(timeAndDate);
//    }

    private void saveTask() {

        if (!TextUtils.isEmpty(mBinding.appbarTitleLayout.fragmentTaskEditTaskTitleEditText.getText())) {

            TaskEntity taskEntity = new TaskEntity(
                    mBinding.appbarTitleLayout.fragmentTaskEditTaskTitleEditText.getText().toString(),
                    mBinding.extraDetails.fragmentTaskEditDescriptionTitleEditText.getText().toString(),
                    DateAndTimeUtils.convertDateAndTimeToMillis(mDate, mTime),
                    DateAndTimeUtils.getCurrentMillis());
            viewModel.insertTask(taskEntity);
            getActivity().finish();
        } else {
            Toast.makeText(getContext(), "EMPTY", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeViewModel();

        Bundle bundle = getArguments();
        validateBundleAction(bundle);
    }

    private void validateBundleAction(Bundle bundle) {
        if (bundle != null) {
            if (Objects.equals(bundle
                    .getString(TaskEditActivity.GET_ACTION), TaskEditActivity.ACTION_ADD)) {
                callLoader(LOADER_ID_ADD, bundle);

            } else if (Objects.equals(bundle
                    .getString(TaskEditActivity.GET_ACTION), TaskEditActivity.ACTION_EDIT)) {
                callLoader(LOADER_ID_EDIT, bundle);
            }
        }
    }

    private void callLoader(int id, Bundle bundle) {
        getLoaderManager().initLoader(id, bundle, this).forceLoad();
    }

    @NonNull
    @Override
    public Loader<TaskEntity> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == LOADER_ID_ADD) {
            return new TaskLoader(Objects.requireNonNull(getContext()), id);
        } else {
            assert args != null;
            return new TaskLoader(Objects.requireNonNull(getContext()), id, args, viewModel);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<TaskEntity> loader, TaskEntity data) {
        if (data == null) {
            setFragmentLayoutAdd();
        } else {
            setFragmentLayoutEdit(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<TaskEntity> loader) {

    }

    private void setFragmentLayoutEdit(TaskEntity taskEntity) {

        if (mBinding.appbarTitleLayout.fragmentTaskEditNextReminderTitle1.getVisibility() == View.GONE) {
            mBinding.appbarTitleLayout.fragmentTaskEditNextReminderTitle1.setVisibility(View.VISIBLE);
            mBinding.appbarTitleLayout.fragmentTaskEditNextReminderTitleNextReminderDetails.setVisibility(View.VISIBLE);
        }

        String[] reminder = new String[2];
        if (taskEntity.getTaskTimeAndDate() != 0) {
            reminder = DateAndTimeUtils
                    .getFormattedDateAndTimeFromMillisSeparately(taskEntity.getTaskTimeAndDate());
        } else {
            reminder[0] = "Not Set";
            reminder[1] = "Not Set";
        }

        String[] creation = DateAndTimeUtils
                .getFormattedDateAndTimeFromMillisSeparately(taskEntity.getTaskCreation());

        setFragmentLayout(reminder[0],
                reminder[1],
                creation[0] + " " + creation[1],
                taskEntity.getTaskName(),
                taskEntity.getTaskDescription());
    }

    private void setFragmentLayoutAdd() {
        if (mBinding.appbarTitleLayout.fragmentTaskEditNextReminderTitle1.getVisibility() == View.VISIBLE) {
            mBinding.appbarTitleLayout.fragmentTaskEditNextReminderTitle1.setVisibility(View.GONE);
            mBinding.appbarTitleLayout.fragmentTaskEditNextReminderTitleNextReminderDetails.setVisibility(View.GONE);
        }
        setFragmentLayout(
                getString(R.string.edit_screen_val_not_set),
                getString(R.string.edit_screen_val_not_set),
                getString(R.string.edit_screen_val_not_set), null, null);
    }

    private void setFragmentLayout(String timeSubtitle,
                                   String dateSubtitle, String appBarReminder, String taskName, String taskDescription) {

        mBinding.extraDetails.fesi1Subtitle.setText(timeSubtitle);
        mBinding.extraDetails.fesi2Subtitle.setText(dateSubtitle);

        if (taskName != null) {
            mBinding.appbarTitleLayout.fragmentTaskEditTaskTitleEditText.setText(taskName);
        }
        if (taskDescription != null) {
            mBinding.extraDetails.fragmentTaskEditDescriptionTitleEditText.setText(taskDescription);
        }

        mBinding.appbarTitleLayout
                .fragmentTaskEditNextReminderTitleNextReminderDetails.setText(appBarReminder);
    }
}