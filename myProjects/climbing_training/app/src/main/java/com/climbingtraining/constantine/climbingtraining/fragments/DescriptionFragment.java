package com.climbingtraining.constantine.climbingtraining.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.activity.CategoryActivity;

/**
 * Created by KonstantinSysoev on 03.05.15.
 */
public class DescriptionFragment extends Fragment {

    private EditText nameEditText;
    private EditText descriptionEditText;
    private EditText commentEditText;

    private String name;
    private String description;
    private String comment;

    public static DescriptionFragment newInstance() {
        DescriptionFragment fragment = new DescriptionFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        if (getArguments() != null) {
            name = getArguments().getString(CategoryActivity.NAME);
            description = getArguments().getString(CategoryActivity.DESCRIPTION);
            comment = getArguments().getString(CategoryActivity.COMMENT);
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        nameEditText = (EditText) getActivity().findViewById(R.id.fragment_description_name_edit_text);
        descriptionEditText = (EditText) getActivity().findViewById(R.id.fragment_description_description_edit_text);
        commentEditText = (EditText) getActivity().findViewById(R.id.fragment_description_comment_edit_text);

        updateFields();
    }

    private void updateFields() {
        nameEditText.setText(name);
        descriptionEditText.setText(description);
        commentEditText.setText(comment);
    }

    // GET & SET

    public String getName() {
        name = nameEditText.getText().toString();
        return name;
    }

    public String getDescription() {
        description = descriptionEditText.getText().toString();
        return description;
    }

    public String getComment() {
        comment = commentEditText.getText().toString();
        return comment;
    }
}
