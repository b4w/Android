package com.climbingtraining.constantine.climbingtraining.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBarActivity;
import android.widget.ExpandableListView;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.adapters.ExercisesListAdapter;
import com.climbingtraining.constantine.climbingtraining.pojo.Exercises;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KonstantinSysoev on 02.05.15.
 */
public class ExercisesActivity extends ActionBarActivity {

    private final static String TAG = ExercisesActivity.class.getSimpleName();

    private ExpandableListView exercisesLayoutExlistView;
    private FloatingActionButton exercisesLayoutFab;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_layout);

        toolbar = (Toolbar) findViewById(R.id.exercises_layout_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        exercisesLayoutFab = (FloatingActionButton) findViewById(R.id.exercises_layout_fab);
        exercisesLayoutExlistView = (ExpandableListView) findViewById(R.id.exercises_layout_exlist_view);
        exercisesLayoutFab.attachToListView(exercisesLayoutExlistView);

        ExercisesListAdapter exercisesListAdapter = new ExercisesListAdapter(this, initData());
        exercisesLayoutExlistView.setAdapter(exercisesListAdapter);
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
}
