package com.climbingtraining.constantine.climbingtraining.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBarActivity;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.fragments.ExercisesFragment;

/**
 * Created by KonstantinSysoev on 02.05.15.
 */
public class ExercisesActivity extends ActionBarActivity {

    private final static String TAG = ExercisesActivity.class.getSimpleName();

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_layout);

        toolbar = (Toolbar) findViewById(R.id.exercises_layout_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("Exercises");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ExercisesFragment exercisesFragment = (ExercisesFragment) getFragmentManager().findFragmentById(R.id.exercises_fragment);
    }
}
