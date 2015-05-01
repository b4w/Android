package com.climbingtraining.constantine.climbingtraining.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.adapters.TrainingsListAdapter;
import com.climbingtraining.constantine.climbingtraining.pojo.Trainings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by KonstantinSysoev on 01.05.15.
 */
public class TrainingsActivity extends Activity {

    private static final String TAG = TrainingsActivity.class.getSimpleName();

    private ListView trainingsLayoutListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainings_layout);

        trainingsLayoutListView = (ListView) findViewById(R.id.trainings_layout_list_view);

        TrainingsListAdapter trainingsListAdapter = new TrainingsListAdapter(this, newDataSet());
        trainingsLayoutListView.setAdapter(trainingsListAdapter);
    }

    private List<Trainings> newDataSet() {
        List<Trainings> trainingsList = new ArrayList<>();
        List<String> exercises = new ArrayList();
        exercises.add("Подтягивание");
        exercises.add("Отжимание");
        exercises.add("Приседание");
        trainingsList.add(new Trainings(1, new Date(), R.drawable.ofp, exercises, "Комментарий 1", true));
        trainingsList.add(new Trainings(2, new Date(), R.drawable.ofp, exercises, "Комментарий 2", false));
        trainingsList.add(new Trainings(3, new Date(), R.drawable.sfp_ofp, exercises, "Комментарий 3", true));
        trainingsList.add(new Trainings(4, new Date(), R.drawable.sfp, exercises, "Комментарий 4", false));
        trainingsList.add(new Trainings(5, new Date(), R.drawable.sfp, exercises, "Комментарий 5", true));
        return trainingsList;
    }
}
