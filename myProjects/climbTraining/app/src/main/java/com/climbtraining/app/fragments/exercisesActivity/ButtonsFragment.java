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

    private Button btnAddCategory;
    private Button btnCancelCategory;
    private Button btnRemoveCategory;

    private ICommunicatorExercises iCommunicatorExercises;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_exercises_buttons_fragment, container, false);

        btnAddCategory = (Button) view.findViewById(R.id.btnAddCategory);
        btnCancelCategory = (Button) view.findViewById(R.id.btnCancelCategory);
        btnRemoveCategory = (Button) view.findViewById(R.id.btnRemoveCategory);

        btnCancelCategory.setOnClickListener(this);
        btnAddCategory.setOnClickListener(this);
        btnRemoveCategory.setOnClickListener(this);

        iCommunicatorExercises = (ICommunicatorExercises) getActivity();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddCategory:
                iCommunicatorExercises.showDialogAddCategory();
                break;
            case R.id.btnCancelCategory:

                break;
            case R.id.btnRemoveCategory:
                iCommunicatorExercises.removeCategoryById("2");
                break;
        }
    }
}
