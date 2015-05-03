package com.climbingtraining.constantine.climbingtraining.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.adapters.TrainingsListAdapter;
import com.climbingtraining.constantine.climbingtraining.pojo.Trainings;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by KonstantinSysoev on 03.05.15.
 */
public class TrainingsFragment extends Fragment {

    private ListView trainingsLayoutListView;
    private FloatingActionsMenu trainingsLayoutActionsMenu;

    private List<Trainings> tmpList;
    private TrainingsListAdapter trainingsListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tmpList = newDataSet();
        trainingsListAdapter = new TrainingsListAdapter(getActivity(), tmpList);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        trainingsLayoutActionsMenu = (FloatingActionsMenu) getActivity().findViewById(R.id.fragment_trainings_actions_menu);
        trainingsLayoutListView = (ListView) getActivity().findViewById(R.id.fragment_trainings_list_view);

        trainingsLayoutListView.setAdapter(trainingsListAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainings, container, false);
        return view;
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
