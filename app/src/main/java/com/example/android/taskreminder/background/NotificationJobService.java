package com.example.android.taskreminder.background;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by jiten on 2/1/2018.
 */

public class NotificationJobService extends JobService {

    private AsyncTask<Void, Void, Void> mTask;

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(JobParameters job) {
        mTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Context context = getApplicationContext();
                int taskId = 0;
                if (job.getExtras() != null) {
                    taskId = job.getExtras().getInt(FirebaseDispatcher.TASK_ID);
                }
                BackgroundTaskUtils.syncDatabase(context, taskId);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
//                Log.v("********NOTIFICATION***********", "POST_EXECUTE");
                jobFinished(job, false);
            }
        };
        mTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (mTask != null) {
            mTask.cancel(true);
        }
//        Log.v("********NOTIFICATION***********", "JOB_FINISHED");
        return false;
    }
}
