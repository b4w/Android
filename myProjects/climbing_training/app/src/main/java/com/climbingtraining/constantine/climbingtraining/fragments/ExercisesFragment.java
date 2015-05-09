package com.climbingtraining.constantine.climbingtraining.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.adapters.ExercisesListAdapter;
import com.climbingtraining.constantine.climbingtraining.pojo.Exercises;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KonstantinSysoev on 03.05.15.
 */
public class ExercisesFragment extends Fragment {

    private ExpandableListView exercisesLayoutExlistView;
    private ExercisesListAdapter exercisesListAdapter;

    private FloatingActionsMenu exerciseslayoutActionsMenu;
    private FloatingActionButton categoryBtn;
    private FloatingActionButton equipmentBtn;
    private FloatingActionButton exerciseBtn;
    private FloatingActionButton typeExerciseBtn;

    private IExercisesFragmentCallBack exercisesCallBack;

    public static ExercisesFragment newInstance() {
        ExercisesFragment fragment = new ExercisesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exercisesListAdapter = new ExercisesListAdapter(getActivity(), initData());
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

        exerciseslayoutActionsMenu = (FloatingActionsMenu) getActivity().findViewById(R.id.fragment_exercises_actions_menu);
        exercisesLayoutExlistView = (ExpandableListView) getActivity().findViewById(R.id.fragment_exercises_exlist_view);

        categoryBtn = (FloatingActionButton) getActivity().findViewById(R.id.fragment_exercises_category_btn);
        equipmentBtn = (FloatingActionButton) getActivity().findViewById(R.id.fragment_exercises_equipment_btn);
        exerciseBtn = (FloatingActionButton) getActivity().findViewById(R.id.fragment_exercises_exercise_btn);
        typeExerciseBtn = (FloatingActionButton) getActivity().findViewById(R.id.fragment_exercises_type_of_exercise_btn);

        exercisesLayoutExlistView.setAdapter(exercisesListAdapter);

        categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exercisesCallBack.createNewCategory();
            }
        });

        equipmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exercisesCallBack.createNewEquipment();
            }
        });

        exerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exercisesCallBack.createNewExercise();
            }
        });

        typeExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exercisesCallBack.createNewTypeExercise();
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercises, container, false);
        return view;
    }

    private List<List<Exercises>> initData() {
        List<Exercises> child1 = new ArrayList<>();
        child1.add(new Exercises(R.drawable.logo, "Подтягивание на турнике", "Подтягивания", "Прямой хват", "Турник", 3, "Комментарий 1"));
        child1.add(new Exercises(R.drawable.logo, "Подтягивание на зацепах", "Подтягивания", "Активный хват", "Фингерборд", 5, "Комментарий 2"));
        child1.add(new Exercises(R.drawable.logo, "Подтягивание на турнике", "Подтягивания", "Прямой хват", "Турник", 2, "Комментарий 3"));

        List<Exercises> child2 = new ArrayList<>();
        child2.add(new Exercises(R.drawable.logo, "Подтягивание на турнике", "Отжимания", "Прямой хват", "Турник", 3, "Комментарий 1"));
        child2.add(new Exercises(R.drawable.logo, "Подтягивание на зацепах", "Отжимания", "Активный хват", "Фингерборд", 5, "Комментарий 2"));
        child2.add(new Exercises(R.drawable.logo, "Подтягивание на турнике", "Отжимания", "Прямой хват", "Турник", 2, "Комментарий 3"));

        List<List<Exercises>> result = new ArrayList<>();
        result.add(child1);
        result.add(child2);
        return result;
    }


//    interface for onClick(); action button and create new fragments
    public interface IExercisesFragmentCallBack {
        void createNewCategory();
        void createNewEquipment();
        void createNewExercise();
        void createNewTypeExercise();
    }
}
