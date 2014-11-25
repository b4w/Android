package com.climbtraining.app.fragments.exercisesActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.climbtraining.app.R;

public class AddCategoryFragment extends DialogFragment {

    private ICommunicatorExercises iCommunicatorExercises;
    private EditText tvNameCategory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iCommunicatorExercises = (ICommunicatorExercises) getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_exercises_add_caterory, null);
        builder.setView(dialogView)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvNameCategory = (EditText) dialogView.findViewById(R.id.editTextNameCategory);
                        iCommunicatorExercises.addNewCategory(tvNameCategory.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddCategoryFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
