package com.climbtraining.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.climbtraining.app.R;
import com.climbtraining.app.fragments.workoutActivity.ICommunicatorWorkout;

public class  WorkoutActivity extends FragmentActivity implements ICommunicatorWorkout {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
    }

    @Override
    public void addNewItemToList(String item) {
        Intent intent = new Intent(getApplicationContext(), WorkoutsActivity.class);
        intent.putExtra("name", item);
        setResult(RESULT_OK, intent);
        finish();
    }
}
