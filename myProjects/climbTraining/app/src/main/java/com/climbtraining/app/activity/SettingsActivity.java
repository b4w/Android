package com.climbtraining.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.climbtraining.app.R;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuMain:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.menuWorkouts:
                startActivity(new Intent(getApplicationContext(), WorkoutsActivity.class));
                break;
            case R.id.menuExercises:
                startActivity(new Intent(getApplicationContext(), ExercisesActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
