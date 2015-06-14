package com.climbingtraining.constantine.climbingtraining.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.adapters.ExercisesListAdapter;
import com.climbingtraining.constantine.climbingtraining.data.common.CommonDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.Category;
import com.climbingtraining.constantine.climbingtraining.data.dto.Exercise;
import com.climbingtraining.constantine.climbingtraining.data.dto.ICommonEntities;
import com.climbingtraining.constantine.climbingtraining.data.helpers.OrmHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by KonstantinSysoev on 11.05.15.
 */
public class ChoiceExerciseFragment extends Fragment {

    private static final String TAG = ChoiceExerciseFragment.class.getSimpleName();

    private ExpandableListView fragmentChoiceExercisesExlistView;
    private IChoiceExercisesFragmentCallBack callBack;
    private ExercisesListAdapter exercisesListAdapter;

    private OrmHelper ormHelper;
    private CommonDao commonDaoExercise;
    private CommonDao commonDaoCategory;

    private List<Exercise> exercises;
    private List<Category> categories;

    public static ChoiceExerciseFragment newInstance() {
        ChoiceExerciseFragment fragment = new ChoiceExerciseFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callBack = (IChoiceExercisesFragmentCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement IChoiceExercisesFragmentCallBack.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choice_exercise, container, false);
        initDB();
        exercises = getAllExercises();
        categories = getAllCategory();
        exercisesListAdapter = new ExercisesListAdapter(getActivity(), initData());
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initXmlFields();
        initListeners();
        fragmentChoiceExercisesExlistView.setAdapter(exercisesListAdapter);
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        fragmentChoiceExercisesExlistView = (ExpandableListView) getActivity().findViewById(R.id.fragment_choice_exercises_exlist_view);
        Log.d(TAG, "initXmlFields() done");
    }

    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        fragmentChoiceExercisesExlistView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Exercise exercise = (Exercise) parent.getExpandableListAdapter().getChild(groupPosition, childPosition);
                callBack.chooseExercise(exercise.getId());
                return false;
            }
        });
        Log.d(TAG, "initListeners() done");
    }

    private void initDB() {
        Log.d(TAG, "initDB() start");
        ormHelper = new OrmHelper(getActivity(), ICommonEntities.CLIMBING_TRAINING_DB_NAME,
                ICommonEntities.CLIMBING_TRAINING_DB_VERSION);
        try {
            commonDaoExercise = ormHelper.getDaoByClass(Exercise.class);
            commonDaoCategory = ormHelper.getDaoByClass(Category.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        TODO: будут вытаскиваться данные без ormHelper?
//        if (ormHelper != null)
//            ormHelper.close();
        Log.d(TAG, "initDB() done");
    }

    private List<Exercise> getAllExercises() {
        Log.d(TAG, "getAllExercises() start");
        List<Exercise> result = null;
        try {
//            TODO: добавить проверку на commonDaoExercise != null?
            result = commonDaoExercise.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "getAllExercises() done");
        return result != null ? result : Collections.<Exercise>emptyList();
    }


    private List<Category> getAllCategory() {
        Log.d(TAG, "getAllCategory() start");
        List<Category> result = null;
        try {
            result = commonDaoCategory.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "getAllCategory() done");
        return result != null ? result : Collections.<Category>emptyList();
    }

    private List<List<Exercise>> initData() {
        Log.d(TAG, "initData() start");
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
        Log.d(TAG, "initData() done");
        return parent;
    }

    public interface IChoiceExercisesFragmentCallBack {
        void chooseExercise(Integer exerciseId);
    }
}
