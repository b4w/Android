package com.devcolibri.examintent.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
    }

    public void goToNewActivity(View view) {
        Intent intent = new Intent(this, LastActivity.class);
        startActivity(intent);
    }
}
