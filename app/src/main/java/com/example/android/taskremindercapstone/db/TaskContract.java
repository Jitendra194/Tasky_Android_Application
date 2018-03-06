package com.example.android.taskremindercapstone.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jiten on 2/1/2018.
 */

public class TaskContract {

    public static final String CONTENT_AUTHORITY =
            "com.example.android.taskremindercapstone";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_TASK_TABLE = "task";

    public static final class TaskEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_TASK_TABLE)
                .build();

        public static final String TABLE_NAME = "Tasks";

        public static final String COLUMN_TASK_NAME = "taskName";

        public static final String COLUMN_TASK_DESCRIPTION = "taskDescription";

        public static final String COLUMN_TASK_TIME_AND_DATE = "taskTimeAndDate";

        public static final String COLUMN_TASK_CREATION_DATE = "taskCreationDate";

        public static Uri getTaskIdUri(String taskId) {
            return CONTENT_URI.buildUpon()
                    .appendPath(taskId)
                    .build();
        }
    }
}
