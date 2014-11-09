package com.climbtraining.app.fragments.exercisesActivity;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.climbtraining.app.R;
import com.climbtraining.app.dbhelpers.ExercisesSQLHelper;
import com.climbtraining.app.pojo.Exercise;

public class ButtonsFragment extends Fragment implements View.OnClickListener{

    private Button btnAdd;
    private Button btnCancel;
    private ExercisesSQLHelper exercisesSQLHelper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_exercises_buttons_fragment, container, false);

        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        exercisesSQLHelper = new ExercisesSQLHelper(view.getContext());

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd:
                insertNewItemToDB();
                break;
            case R.id.btnCancel:

                break;
        }
    }

    private void insertNewItemToDB() {
        Exercise exercise = new Exercise("Отжимания", "Какой-то комментарий для категории отжимания");
        exercisesSQLHelper.insertExercise(exercise);
    }
}
