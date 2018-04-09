package com.example.android.taskreminder.ui.fragments;


import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.example.android.taskreminder.R;
import com.example.android.taskreminder.databinding.FragmentTaskListBinding;
import com.example.android.taskreminder.db.DataIOFunctions;
import com.example.android.taskreminder.model.TaskEntity;
import com.example.android.taskreminder.ui.activities.MainListActivity;
import com.example.android.taskreminder.ui.adapters.TaskListAdapter;
import com.example.android.taskreminder.ui.callbacks.AddNewTaskClickCallback;
import com.example.android.taskreminder.ui.utilities.SwipeToDelete;
import com.example.android.taskreminder.ui.utilities.Utils;
import com.example.android.taskreminder.viewmodel.TaskViewModel;

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

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), DataIOFunctions.taskUri,
                DataIOFunctions.TASK_PROJECTION,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            List<TaskEntity> taskEntities = viewModel.loadTasks(data);
            setListData(taskEntities);
//            viewModel.getBackgroundTaskUtils().initializeFJD(taskEntities);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
