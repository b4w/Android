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
import com.melnykov.fab.FloatingActionButton;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by KonstantinSysoev on 03.05.15.
 */
public class ExercisesFragment extends Fragment {

    private final static String TAG = ExercisesFragment.class.getSimpleName();

    private ExpandableListView exercisesLayoutExlistView;
    private ExercisesListAdapter exercisesListAdapter;
    private FloatingActionButton fragmentExercisesFloatButton;

    private OrmHelper ormHelperExercise;
    private CommonDao commonDaoExercise;

    private OrmHelper ormHelperCategory;
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
        initDB();
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

        loadListeners();

        exercisesLayoutExlistView.setAdapter(exercisesListAdapter);
    }

    private void loadListeners() {
        Log.d(TAG, "loadListeners() start");
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
        Log.d(TAG, "loadListeners() done");
    }

    @Override
    public void onResume() {
        super.onResume();
//        TODO разобраться с обновлением данных
        exercises = getAllExercises();
        categories = getAllCategory();
        exercisesListAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercises, container, false);

        exercises = getAllExercises();
        categories = getAllCategory();
        exercisesListAdapter = new ExercisesListAdapter(getActivity(), initData());

        return view;
    }

    /**
     * Инициализируем подключение к БД.
     */
    private void initDB() {
        Log.d(TAG, "initDB() start");
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
        Log.d(TAG, "initDB() done");
    }

    /**
     * Возвращаем лист всех упражнений.
     * @return List<Exercise>
     */
    private List<Exercise> getAllExercises() {
        Log.d(TAG, "getAllExercises() start");
        List<Exercise> result = null;
        try {
            result = commonDaoExercise.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "getAllExercises() done");
        return result != null ? result : Collections.<Exercise>emptyList();
    }

    /**
     * Возвращаем лист всех категорий.
     * @return List<Category>
     */
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

    /**
     * Структуируем упражнения и категории в один лист для отображения.
     * @return List<List<Exercise>>
     */
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
                if (category.getId() == exercise.getCategory().getId()) {
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

    public interface IExercisesFragmentCallBack {
        void createNewExercise();

        void editExercise(Exercise exercise);
    }
}
