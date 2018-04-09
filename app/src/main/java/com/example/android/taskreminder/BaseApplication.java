package com.example.android.taskreminder;

import android.app.Application;

import com.example.android.taskreminder.background.BackgroundTaskUtils;
import com.example.android.taskreminder.background.alarms.AlarmManagerClass;
import com.example.android.taskreminder.dependencyinjection.baseapplicationinjection.ApplicationContextModule;
import com.example.android.taskreminder.dependencyinjection.baseapplicationinjection.BaseApplicationComponent;
import com.example.android.taskreminder.dependencyinjection.baseapplicationinjection.DaggerBaseApplicationComponent;

/**
 * Created by jiten on 1/26/2018.
 */

public class BaseApplication extends Application {

    private DataRepository dataRepository;
    private BackgroundTaskUtils backgroundTaskUtils;
    private AlarmManagerClass alarmManagerClass;

    @Override
    public void onCreate() {
        super.onCreate();

        BaseApplicationComponent component = DaggerBaseApplicationComponent.builder()
                .applicationContextModule(new ApplicationContextModule(this))
                .build();

        dataRepository = component.dataRepository();
        backgroundTaskUtils = component.backgroundTaskUtils();
        alarmManagerClass = component.alarmManagerClass();
    }

    public DataRepository getRepository() {
        return dataRepository;
    }

    public BackgroundTaskUtils getBackgroundTaskUtils() {
        return backgroundTaskUtils;
    }

    public AlarmManagerClass getAlarmManagerClass() {
        return alarmManagerClass;
    }
}