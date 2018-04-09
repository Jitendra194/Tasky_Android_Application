package com.example.android.taskreminder.dependencyinjection.baseapplicationinjection;

import android.content.Context;

import com.example.android.taskreminder.AppExecutors;
import com.example.android.taskreminder.DataRepository;
import com.example.android.taskreminder.background.BackgroundTaskUtils;
import com.example.android.taskreminder.background.alarms.AlarmManagerClass;
import com.example.android.taskreminder.db.TaskDatabase;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiten on 1/29/2018.
 */

@Module(includes = ApplicationContextModule.class)
public class DataRepositoryModule {

    @Provides
    @BaseApplicationScope
    public TaskDatabase taskDatabase(Context context) {
        return new TaskDatabase(context);
    }

    @Provides
    @BaseApplicationScope
    public AppExecutors appExecutors() {
        return new AppExecutors();
    }

    @Provides
    @BaseApplicationScope
    public DataRepository dataRepository(TaskDatabase taskDatabase, AppExecutors appExecutors) {
        return new DataRepository(taskDatabase, appExecutors);
    }

    @Provides
    @BaseApplicationScope
    public BackgroundTaskUtils backgroundTaskUtils(Context context) {
        return new BackgroundTaskUtils(context);
    }

    @Provides
    @BaseApplicationScope
    public AlarmManagerClass alarmManagerClass(Context context) {
        return new AlarmManagerClass(context);
    }
}
