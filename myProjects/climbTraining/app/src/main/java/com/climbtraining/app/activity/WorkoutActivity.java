package com.climbtraining.app.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.climbtraining.app.R;

public class  WorkoutActivity extends ActionBarActivity {

    private final String EMPTY_TEXT = "You must fill the field";
    private final String NAME = "name";

    private Button btnWorkoutAdd;
    private Button btnWorkoutClear;
    private Button btnWorkoutCancel;

    private EditText etWorkoutName;
    private EditText etWorkoutComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        btnWorkoutAdd = (Button) findViewById(R.id.btnWorkoutAdd);
        btnWorkoutClear = (Button) findViewById(R.id.btnWorkoutClear);
        btnWorkoutCancel = (Button) findViewById(R.id.btnWorkoutCancel);

        etWorkoutName = (EditText) findViewById(R.id.etWorkoutName);
        etWorkoutComments = (EditText) findViewById(R.id.etWorkoutComments);
    }

    public void cancel(View view) {
        startActivity(new Intent(getApplicationContext(), WorkoutsActivity.class));
    }

    public void clearFields(View view) {
        etWorkoutName.setText("");
        etWorkoutComments.setText("");
    }

    public void addNewWorkout(View view) {
        if (etWorkoutName.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), EMPTY_TEXT + NAME, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getApplicationContext(), WorkoutsActivity.class);
            intent.putExtra("etWorkoutName", etWorkoutName.getText().toString());
            intent.putExtra("etWorkoutComments", etWorkoutComments.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
