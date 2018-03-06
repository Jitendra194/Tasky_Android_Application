package com.example.android.taskremindercapstone.dependencyinjection.baseapplicationinjection;

import android.content.Context;

import com.example.android.taskremindercapstone.AppExecutors;
import com.example.android.taskremindercapstone.DataRepository;
import com.example.android.taskremindercapstone.background.BackgroundTaskUtils;
import com.example.android.taskremindercapstone.background.alarms.AlarmManagerClass;
import com.example.android.taskremindercapstone.db.TaskDatabase;

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
