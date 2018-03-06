package com.example.android.taskremindercapstone.ui.utilities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiten on 1/31/2018.
 */

public class DateAndTimeUtils {

    private static String mTaskDate;

    private static String mTaskTime;

    private static final Calendar calendar = Calendar.getInstance();

    private static final String TAG = DateAndTimeUtils.class.getSimpleName();


    public static String getTime(int hourOfDay, int minute) {
        mTaskTime = hourOfDay + ":" + minute;
        return mTaskTime;
    }

    public static String getDate(int year, int month, int dayOfMonth) {
        mTaskDate = month + "-" + dayOfMonth + "-" + year;
        return mTaskDate;
    }

    public static String showTime(Context context,
                                  TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                onTimeSetListener, hour, minute, false);
        timePickerDialog.show();
        return mTaskTime;
    }

    public static String showCalendar(Context context,
                                      DatePickerDialog.OnDateSetListener onDateSetListener) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                onDateSetListener, year, month, day);
        datePickerDialog.show();
        return mTaskDate;
    }

    public static String getNextReminderDateAndTime(long millis) {
        String[] dateAndTime = convertMillisToDateAndTime(millis);
        return dateAndTime[0] + " " + dateAndTime[1];

    }

    public static long convertDateAndTimeToMillis(String mDate, String mTime) {
        String timeAndDate = mTime + " " + mDate;
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("HH:mm MM-dd-yyyy", Locale.getDefault());
        try {
            Date date = dateFormat.parse(timeAndDate);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String[] convertMillisToDateAndTime(long millis) {
        String[] dateAndTime = new String[2];
        String mTime;
        String mDate;
        calendar.setTimeInMillis(millis);
        SimpleDateFormat timeFormat =
                new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        mTime = timeFormat.format(calendar.getTime());
        mDate = dateFormat.format(calendar.getTime());
        try {
            Date time = timeFormat.parse(mTime);
            Date date = dateFormat.parse(mDate);

            dateAndTime[0] = DateFormat.getTimeInstance(DateFormat.SHORT).format(time);
            dateAndTime[1] = DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateAndTime;
    }

    public static long getCreationMillis() {
        return System.currentTimeMillis();
    }

    public static long getSnoozeWindow() {
        return System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5);
    }
}
