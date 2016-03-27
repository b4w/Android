package com.devcolibri.senddatatolastactivity.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LastActivity extends Activity {

    private TextView loginTextView;
    private TextView passTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.last_layout);

        loginTextView = (TextView) findViewById(R.id.loginTextView);
        passTextView = (TextView) findViewById(R.id.passTextView);

        loginTextView.setText(getIntent().getStringExtra("login"));
        passTextView.setText(getIntent().getStringExtra("pass"));
    }

    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
