package com.climbingtraining.constantine.climbingtraining.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.adapters.TrainingsListAdapter;
import com.climbingtraining.constantine.climbingtraining.data.common.CommonDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.ICommonEntities;
import com.climbingtraining.constantine.climbingtraining.data.dto.Training;
import com.climbingtraining.constantine.climbingtraining.data.helpers.OrmHelper;
import com.melnykov.fab.FloatingActionButton;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Created by KonstantinSysoev on 03.05.15.
 */
public class TrainingsFragment extends Fragment {

    private static final String TAG = TrainingsFragment.class.getSimpleName();

    private ListView trainingsLayoutListView;
    private List<Training> trainings;
    private TrainingsListAdapter trainingsListAdapter;
    private FloatingActionButton fragmentTrainingsFloatButton;
    private ITrainingsFragmentCallBack callBack;

    private OrmHelper ormHelper;
    private CommonDao commonDao;

    public static TrainingsFragment newInstance() {
        TrainingsFragment fragment = new TrainingsFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callBack = (ITrainingsFragmentCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement ITrainingsFragmentCallBack.");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDB();
        trainings = loadTrainingFromDB();
        trainingsListAdapter = new TrainingsListAdapter(getActivity(), trainings);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initXmlFields();
        initListeners();
        trainingsLayoutListView.setAdapter(trainingsListAdapter);
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        trainingsLayoutListView = (ListView) getActivity().findViewById(R.id.fragment_trainings_list_view);
        fragmentTrainingsFloatButton = (FloatingActionButton) getActivity().findViewById(R.id.fragment_trainings_float_button);
        Log.d(TAG, "initXmlFields() done");
    }

    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        trainingsLayoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Training training = (Training) parent.getAdapter().getItem(position);
                callBack.editTraining(training);
            }
        });

        fragmentTrainingsFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.createNewTraining();
            }
        });
        Log.d(TAG, "initListeners() done");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainings, container, false);
        return view;
    }

    private void initDB() {
        Log.d(TAG, "initDB() start");
        ormHelper = new OrmHelper(getActivity(), ICommonEntities.CLIMBING_TRAINING_DB_NAME,
                ICommonEntities.CLIMBING_TRAINING_DB_VERSION);
        try {
            commonDao = ormHelper.getDaoByClass(Training.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "initDB() done");
    }

    private List<Training> loadTrainingFromDB() {
        Log.d(TAG, "loadTrainingFromDB() start");
        List<Training> result = null;
        try {
            result = commonDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "loadTrainingFromDB() done");
        return result != null ? result : Collections.<Training>emptyList();
    }

    public interface ITrainingsFragmentCallBack {
        void createNewTraining();

        void editTraining(Training training);
    }
}
