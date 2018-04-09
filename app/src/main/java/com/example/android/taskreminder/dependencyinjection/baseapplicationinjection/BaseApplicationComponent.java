package com.example.android.taskreminder.dependencyinjection.baseapplicationinjection;

import com.example.android.taskreminder.DataRepository;
import com.example.android.taskreminder.background.BackgroundTaskUtils;
import com.example.android.taskreminder.background.alarms.AlarmManagerClass;

import dagger.Component;

/**
 * Created by jiten on 1/29/2018.
 */

@BaseApplicationScope
@Component(modules = {DataRepositoryModule.class})
public interface BaseApplicationComponent {
    DataRepository dataRepository();
    BackgroundTaskUtils backgroundTaskUtils();
    AlarmManagerClass alarmManagerClass();
}
