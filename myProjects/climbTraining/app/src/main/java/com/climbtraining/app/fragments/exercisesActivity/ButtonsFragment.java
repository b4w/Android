package com.climbtraining.app.fragments.exercisesActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.climbtraining.app.R;

public class ButtonsFragment extends Fragment implements View.OnClickListener{

    private Button btnAdd;
    private Button btnCancel;

    private ICommunicatorExercises iCommunicatorExercises;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_exercises_buttons_fragment, container, false);

        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        iCommunicatorExercises = (ICommunicatorExercises) getActivity();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                iCommunicatorExercises.showDialogAddCategory();
                break;
            case R.id.btnCancel:

                break;
        }
    }
}
