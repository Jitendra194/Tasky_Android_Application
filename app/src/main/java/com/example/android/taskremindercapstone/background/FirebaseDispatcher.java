package com.example.android.taskremindercapstone.background;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

/**
 * Created by jiten on 2/3/2018.
 */

public class FirebaseDispatcher {

    public static final String TASK_ID = "TASK_ID";

    static void scheduleFirebaseJobDispatcher(Context context, Long remainingTime, int tId) {

        int SYNC_INTERVAL_SECONDS = Math.toIntExact(remainingTime);
        int SYNC_FLEXTIME_SECONDS = 15;

        Bundle bundle = new Bundle();
        bundle.putInt(TASK_ID, tId);

        if (SYNC_INTERVAL_SECONDS < 0) {
            return;
        }

        Log.v("NOTIFICATION - SYNC_INTERVAL_SECONDS: ", String.valueOf(SYNC_INTERVAL_SECONDS));
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(driver);

        Job notificationJob = firebaseJobDispatcher.newJobBuilder()
                .setService(NotificationJobService.class)
                .setExtras(bundle)
                .setTag(String.valueOf(tId))
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(false)
                .setReplaceCurrent(false)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setTrigger(Trigger.executionWindow(SYNC_INTERVAL_SECONDS,
                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .build();
        firebaseJobDispatcher.schedule(notificationJob);
    }
}
