package com.example.android.taskreminder.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.taskreminder.db.TaskContract.TaskEntry;

/**
 * Created by jiten on 2/1/2018.
 */

public class TaskProvider extends ContentProvider {

    private static final String TAG = TaskProvider.class.getSimpleName();

    private static final int CODE_TASK = 100;
    private static final int CODE_TASK_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TaskContract.CONTENT_AUTHORITY;
//        uriMatcher.addURI(authority, ContractClass.PATH_RESTAURANT_TABLE + "/#", CODE_RESTAURANT_ID);

        uriMatcher.addURI(authority, TaskContract.PATH_TASK_TABLE, CODE_TASK);
        uriMatcher.addURI(authority, TaskContract.PATH_TASK_TABLE + "/#", CODE_TASK_ID);
        return uriMatcher;
    }

    private DbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CODE_TASK:
                cursor = database.query(TaskEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        TaskEntry.COLUMN_TASK_CREATION_DATE + " DESC");
                break;
            case CODE_TASK_ID:
                String taskId = uri.getLastPathSegment();
                String s = TaskEntry.COLUMN_TASK_CREATION_DATE + "=?";
                String[] selectionArguments = new String[]{taskId};
                cursor = database.query(TaskEntry.TABLE_NAME,
                        projection,
                        s,
                        selectionArguments,
                        null,
                        null,
                        null);
                break;
            default:
                throw new IllegalArgumentException("Query is not supported for " + uri);
        }
        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        long id;
        switch (match) {
            case CODE_TASK:
                id = database.insert(TaskEntry.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsDeleted;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CODE_TASK_ID:
                String taskId = uri.getLastPathSegment();
                String s = TaskEntry.COLUMN_TASK_CREATION_DATE + "=?";
                String[] selectionArguments = new String[]{taskId};
                rowsDeleted = database.delete(TaskEntry.TABLE_NAME, s, selectionArguments);
                break;
            default:
                throw new IllegalArgumentException("delete is not supported for " + uri);
        }
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CODE_TASK_ID:
                String taskId = uri.getLastPathSegment();
                String s = TaskEntry.COLUMN_TASK_CREATION_DATE + "=?";
                String[] selectionArguments = new String[]{taskId};
                rowsUpdated = database.update(
                        TaskEntry.TABLE_NAME,
                        values,
                        s,
                        selectionArguments
                );
                break;
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
