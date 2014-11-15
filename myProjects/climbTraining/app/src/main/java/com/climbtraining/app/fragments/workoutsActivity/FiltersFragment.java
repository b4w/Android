package com.climbtraining.app.fragments.workoutsActivity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.climbtraining.app.R;
import com.climbtraining.app.activity.WorkoutActivity;
import com.climbtraining.app.utils.RequestCodes;

public class FiltersFragment extends Fragment implements View.OnClickListener {

    private final static String TAG = FiltersFragment.class.getSimpleName();

    private Button btnFilters;
    private Button btnAddWorkout;

    private ICommunicatorWorkouts iCommunicatorWorkouts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_workouts_filters_fragment, container, false);

        btnAddWorkout = (Button) view.findViewById(R.id.btnAddWorkout);
        btnFilters = (Button) view.findViewById(R.id.btnFilters);
        iCommunicatorWorkouts = (ICommunicatorWorkouts) getActivity();

        btnFilters.setOnClickListener(FiltersFragment.this);
        btnAddWorkout.setOnClickListener(FiltersFragment.this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddWorkout:
                Log.d(TAG, "Add new workout");
                Intent intent = new Intent(getActivity().getApplicationContext(), WorkoutActivity.class);
                startActivityForResult(intent, RequestCodes.REQUEST_CODE_WORKOUT.ordinal());
                break;
            case R.id.btnFilters:
                Toast.makeText(getActivity().getApplicationContext(), "Пока не реализованно =(", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RequestCodes.REQUEST_CODE_WORKOUT.ordinal()) {
                iCommunicatorWorkouts.addItemToDataList(data.getStringExtra("name"));
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "ERROR!", Toast.LENGTH_SHORT).show();
        }
    }
}
