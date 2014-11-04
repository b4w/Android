package com.climbtraining.app.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatting {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String LOG = "DateFormatting";

    public static Date stringToDate(String string) {
        try {
            return sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d(LOG, "stringToDate() done. Parse fail.");
        return null;
    }

    public static String dateToString(Date date) {
        try {
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(LOG, "dateToString() done. Format fail.");
        return null;
    }
}
