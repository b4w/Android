package com.climbtraining.app.fragments.exercisesActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.climbtraining.app.R;
import com.climbtraining.app.adapters.ExerciseAdapter;
import com.climbtraining.app.dbhelpers.ExercisesSQLHelper;
import com.climbtraining.app.pojo.Exercise;

import java.util.List;

public class CategoryFragment extends Fragment {

    private ListView listViewCategoryFragment;
    ExercisesSQLHelper exercisesSQLHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_exercises_category_fragment, container, false);

        listViewCategoryFragment = (ListView) view.findViewById(R.id.expandableListViewCategoryFragment);
        exercisesSQLHelper = new ExercisesSQLHelper(view.getContext());

        List<Exercise> exercises = init();

        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(view.getContext(), exercises);
        listViewCategoryFragment.setAdapter(exerciseAdapter);
        return view;
    }

    private List<Exercise> init() {
        return exercisesSQLHelper.getAllExercises();
    }
}
