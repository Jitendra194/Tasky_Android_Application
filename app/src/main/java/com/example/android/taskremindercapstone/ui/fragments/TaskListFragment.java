package com.example.android.taskremindercapstone.ui.fragments;


import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.taskremindercapstone.R;
import com.example.android.taskremindercapstone.databinding.FragmentTaskListBinding;
import com.example.android.taskremindercapstone.db.DataIOFunctions;
import com.example.android.taskremindercapstone.model.TaskEntity;
import com.example.android.taskremindercapstone.ui.activities.MainListActivity;
import com.example.android.taskremindercapstone.ui.adapters.TaskListAdapter;
import com.example.android.taskremindercapstone.ui.callbacks.AddNewTaskClickCallback;
import com.example.android.taskremindercapstone.ui.utilities.SwipeToDelete;
import com.example.android.taskremindercapstone.ui.utilities.Utils;
import com.example.android.taskremindercapstone.viewmodel.TaskViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = TaskListFragment.class.getSimpleName();

    private FragmentTaskListBinding mBinding;

    private TaskViewModel viewModel;

    private TaskListAdapter taskListAdapter;

    private List<TaskEntity> entityList;

    private StaggeredGridLayoutManager layoutManager;

    public TaskListFragment() {
        // Required empty public constructor
    }

    public static TaskListFragment instantiate() {
        return new TaskListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_task_list,
                container, false);
        if (((MainListActivity) getActivity()) != null) {
            ((MainListActivity) getActivity()).setSupportActionBar(mBinding.taskListToolbar);
        }
        mBinding.setFabClick(clickCallback);
        layoutManager = new StaggeredGridLayoutManager(Utils
                .calculateNoOfColumns(getContext()), StaggeredGridLayoutManager.VERTICAL);
        taskListAdapter = new TaskListAdapter((MainListActivity) getActivity());
        mBinding.taskListRecyclerView.setAdapter(taskListAdapter);
        mBinding.taskListRecyclerView.setLayoutManager(layoutManager);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new TaskViewModel(getActivity().getApplication());
        getLoaderManager().initLoader(0, null, this).forceLoad();
    }

    private void setListData(List<TaskEntity> taskEntities) {
        this.entityList = taskEntities;
        taskListAdapter.setTaskList(entityList);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(mBinding.taskListRecyclerView);
    }

    private final AddNewTaskClickCallback clickCallback = () -> {
        if (((MainListActivity) getActivity()) != null) {
            ((MainListActivity) getActivity()).start();
        }
    };

    private ItemTouchHelper.SimpleCallback createHelperCallback() {
        return new SwipeToDelete() {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                viewModel.deleteTask(entityList.get(position).getTaskCreation());
                entityList.remove(position);
                taskListAdapter.notifyItemRemoved(position);
            }
        };
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), DataIOFunctions.taskUri,
                DataIOFunctions.TASK_PROJECTION,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            List<TaskEntity> taskEntities = viewModel.loadTasks(data);
            setListData(taskEntities);
//            viewModel.getBackgroundTaskUtils().initializeFJD(taskEntities);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
