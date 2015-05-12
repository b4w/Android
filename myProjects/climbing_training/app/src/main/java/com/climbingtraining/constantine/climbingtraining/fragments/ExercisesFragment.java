package com.climbingtraining.constantine.climbingtraining.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.adapters.ExercisesListAdapter;
import com.climbingtraining.constantine.climbingtraining.data.common.CommonDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.Category;
import com.climbingtraining.constantine.climbingtraining.data.dto.Exercise;
import com.climbingtraining.constantine.climbingtraining.data.dto.ICommonEntities;
import com.climbingtraining.constantine.climbingtraining.data.helpers.OrmHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.melnykov.fab.FloatingActionButton;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by KonstantinSysoev on 03.05.15.
 */
public class ExercisesFragment extends Fragment {

    private ExpandableListView exercisesLayoutExlistView;
    private ExercisesListAdapter exercisesListAdapter;
    private FloatingActionButton fragmentExercisesFloatButton;

    private OrmHelper ormHelperExercise;
    //    private ConnectionSource connectionSourceExercise;
    private CommonDao commonDaoExercise;

    private OrmHelper ormHelperCategory;
    //    private ConnectionSource connectionSourceCategory;
    private CommonDao commonDaoCategory;

    private List<Exercise> exercises;
    private List<Category> categories;

    private IExercisesFragmentCallBack exercisesCallBack;

    public static ExercisesFragment newInstance() {
        ExercisesFragment fragment = new ExercisesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        exercisesListAdapter = new ExercisesListAdapter(getActivity(), initData());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            exercisesCallBack = (IExercisesFragmentCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement IExercisesFragmentCallBack.");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        exercisesLayoutExlistView = (ExpandableListView) getActivity().findViewById(R.id.fragment_exercises_exlist_view);
        fragmentExercisesFloatButton = (FloatingActionButton) getActivity().findViewById(R.id.fragment_exercises_float_button);
        fragmentExercisesFloatButton.attachToListView(exercisesLayoutExlistView);

        exercisesLayoutExlistView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Exercise exercise = (Exercise) parent.getExpandableListAdapter().getChild(groupPosition, childPosition);
                exercisesCallBack.editExercise(exercise);
                return false;
            }
        });

        fragmentExercisesFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exercisesCallBack.createNewExercise();
            }
        });

        exercisesLayoutExlistView.setAdapter(exercisesListAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercises, container, false);

        initDB();
        exercises = getAllExercises();
        categories = getAllCategory();

        exercisesListAdapter = new ExercisesListAdapter(getActivity(), initData());

        return view;
    }

    private void initDB() {
        ormHelperExercise = new OrmHelper(getActivity(), ICommonEntities.EXERCISES_DATABASE_NAME,
                ICommonEntities.EXERCISE_DATABASE_VERSION);
        ormHelperCategory = new OrmHelper(getActivity(), ICommonEntities.CATEGORIES_DATABASE_NAME,
                ICommonEntities.CATEGORIES_DATABASE_VERSION);
        try {
            commonDaoExercise = ormHelperExercise.getDaoByClass(Exercise.class);
            commonDaoCategory = ormHelperCategory.getDaoByClass(Category.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Exercise> getAllExercises() {
        List<Exercise> result = null;
        try {
            result = commonDaoExercise.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result != null ? result : Collections.<Exercise>emptyList();
    }

    private List<Category> getAllCategory() {
        List<Category> result = null;
        try {
            result = commonDaoCategory.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result != null ? result : Collections.<Category>emptyList();
    }

    private List<List<Exercise>> initData() {
        if (exercises == null || exercises.isEmpty() || categories == null || categories.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        List<List<Exercise>> parent = new ArrayList<>();
        List<Exercise> child;

        for (Category category : categories) {
            child = new ArrayList<>();
            for (Exercise exercise : exercises) {
                if (category.equals(exercise.getCategory())) {
                    child.add(exercise);
                }
            }
            if (!child.isEmpty()) {
                parent.add(child);
            }
        }
        return parent;
    }

    //    interface for onClick(); action button and create new fragments
    public interface IExercisesFragmentCallBack {
        void createNewExercise();

        void editExercise(Exercise exercise);
    }
}
