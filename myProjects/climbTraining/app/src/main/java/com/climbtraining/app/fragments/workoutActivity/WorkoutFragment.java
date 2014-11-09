package com.climbtraining.app.fragments.workoutActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.climbtraining.app.R;
import com.climbtraining.app.activity.WorkoutsActivity;

public class WorkoutFragment extends Fragment implements View.OnClickListener {

    private final String EMPTY_TEXT = "You must fill the field";
    private final String NAME = "name";

    private Button btnWorkoutAdd;
    private Button btnWorkoutClear;
    private Button btnWorkoutCancel;

    private EditText etWorkoutName;
    private EditText etWorkoutComments;

    private ICommunicatorWorkout communicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_workout_workout_fragment,container,false);

        btnWorkoutAdd = (Button) view.findViewById(R.id.btnWorkoutAdd);
        btnWorkoutClear = (Button) view.findViewById(R.id.btnWorkoutClear);
        btnWorkoutCancel = (Button) view.findViewById(R.id.btnWorkoutCancel);

        btnWorkoutAdd.setOnClickListener(this);
        btnWorkoutClear.setOnClickListener(this);
        btnWorkoutCancel.setOnClickListener(this);

        etWorkoutName = (EditText) view.findViewById(R.id.etWorkoutName);
        etWorkoutComments = (EditText) view.findViewById(R.id.etWorkoutComments);

        communicator = (ICommunicatorWorkout) getActivity();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnWorkoutAdd:
                addNewWorkout(v);
                break;
            case R.id.btnWorkoutClear:
                clearFields(v);
                break;
            case R.id.btnWorkoutCancel:
                cancel(v);
                break;
        }
    }

    public void cancel(View view) {
        // todo переделать на -1 шаг в стеке Fragments
        startActivity(new Intent(getActivity().getApplicationContext(), WorkoutsActivity.class));
    }

    public void clearFields(View view) {
        etWorkoutName.setText("");
        etWorkoutComments.setText("");
    }

    public void addNewWorkout(View view) {
        if (etWorkoutName.getText().toString().equals("")) {
            Toast.makeText(getActivity().getApplicationContext(), EMPTY_TEXT + NAME, Toast.LENGTH_SHORT).show();
        } else {
            // todo создаем новый объект Training, заполняем данными с полей и сохраняем в БД
            Toast.makeText(getActivity().getApplicationContext(), "OK!", Toast.LENGTH_SHORT).show();
            communicator.addNewItemToList(etWorkoutName.getText().toString());
//            Intent intent = new Intent(getActivity().getApplicationContext(), WorkoutsActivity.class);
//            intent.putExtra("etWorkoutName", etWorkoutName.getText().toString());
//            intent.putExtra("etWorkoutComments", etWorkoutComments.getText().toString());
//            setResult(Activity.RESULT_OK, intent);
//            finish();
        }
    }
}
