package com.devcolibri.controloforientation.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
    }

    public void onPosition(View view) {
        Context context = getApplicationContext();
        Configuration configuration = getResources().getConfiguration();

        Toast toast;
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            toast = Toast.makeText(context, "Портретная ориентация", Toast.LENGTH_SHORT);
        } else {
            toast = Toast.makeText(context, "Альбомная ориентация", Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}
