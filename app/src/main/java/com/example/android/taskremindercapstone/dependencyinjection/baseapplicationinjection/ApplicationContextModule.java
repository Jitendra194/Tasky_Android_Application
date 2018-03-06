package com.example.android.taskremindercapstone.dependencyinjection.baseapplicationinjection;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiten on 1/29/2018.
 */

@Module
public class ApplicationContextModule {

    private final Context context;

    public ApplicationContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @BaseApplicationScope
    public Context context() {
        return context;
    }
}
