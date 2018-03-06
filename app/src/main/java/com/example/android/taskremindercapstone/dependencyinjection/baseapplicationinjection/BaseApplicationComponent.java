package com.example.android.taskremindercapstone.dependencyinjection.baseapplicationinjection;

import com.example.android.taskremindercapstone.DataRepository;
import com.example.android.taskremindercapstone.background.BackgroundTaskUtils;
import com.example.android.taskremindercapstone.background.alarms.AlarmManagerClass;

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
