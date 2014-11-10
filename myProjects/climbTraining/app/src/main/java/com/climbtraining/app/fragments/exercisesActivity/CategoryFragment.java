package com.climbtraining.app.fragments.exercisesActivity;

import android.app.ListFragment;
import android.os.Bundle;
import com.climbtraining.app.adapters.ExerciseAdapter;
import com.climbtraining.app.dbhelpers.exercises.ExercisesSQLHelper;
import com.climbtraining.app.pojo.Exercise;

import java.util.List;

public class CategoryFragment extends ListFragment {

    ExercisesSQLHelper exercisesSQLHelper;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        exercisesSQLHelper = new ExercisesSQLHelper(getActivity().getApplicationContext());
        List<Exercise> exercises = init();
        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(getActivity().getApplicationContext(), exercises);
        setListAdapter(exerciseAdapter);
    }

    private List<Exercise> init() {
        return exercisesSQLHelper.getAllExercises();
    }
}
