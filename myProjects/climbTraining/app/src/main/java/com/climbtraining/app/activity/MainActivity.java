package com.climbtraining.app.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.climbtraining.app.R;

public class MainActivity extends ActionBarActivity {

    private Button btnAddNewWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAddNewWorkout = (Button) findViewById(R.id.btnAddNewWorkout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuSettings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
            case R.id.menuWorkouts:
                startActivity(new Intent(getApplicationContext(), WorkoutsActivity.class));
                break;
            case R.id.menuExercises:
                startActivity(new Intent(getApplicationContext(), ExercisesActivity.class));
                break;
            case R.id.menuExit:
                onDestroy();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNewWorkout(View view) {
        startActivity(new Intent(getApplicationContext(), WorkoutActivity.class));
    }
}
