package com.climbtraining.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.climbtraining.app.R;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
