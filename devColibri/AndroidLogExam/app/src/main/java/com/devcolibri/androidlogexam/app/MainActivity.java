package com.devcolibri.androidlogexam.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    private final String TAG = "Dev";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
    }

    public void onInfoLog(View view) {
        Log.i(TAG, "Info level");
    }

    public void onWarnLog(View view) {
        Log.w(TAG, "Warning level");
    }

    public void onErrorLog(View view) {
        Log.e(TAG, "Error level");
    }

    public void onDebugLog(View view) {
        Log.d(TAG, "Debug level");
    }

    public void onVerbose(View view) {
        Log.v(TAG, "Verbose level");
    }

    public void onWtf(View view) {
        Log.wtf(TAG, "WTF level");
    }
}
