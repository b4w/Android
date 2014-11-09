package com.climbtraining.app.fragments.mainActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.climbtraining.app.R;
import com.climbtraining.app.activity.WorkoutsActivity;

public class BodyFragment extends Fragment implements View.OnClickListener {

    private Button btnAddNewWorkout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_body_fragment, container, false);

        btnAddNewWorkout = (Button) view.findViewById(R.id.btnAddNewWorkout);
        btnAddNewWorkout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddNewWorkout:
                startActivity(new Intent(getActivity().getApplicationContext(), WorkoutsActivity.class));
                break;
        }

    }
}
