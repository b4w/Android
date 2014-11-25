package com.climbtraining.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.climbtraining.app.R;
import com.climbtraining.app.pojo.Exercise;

import java.util.List;

public class ExerciseAdapter extends BaseAdapter {

    private List<Exercise> exercisesList;
    private LayoutInflater layoutInflater;

    public ExerciseAdapter(Context context, List<Exercise> exercisesList) {
        this.exercisesList = exercisesList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return exercisesList.size();
    }

    @Override
    public Object getItem(int position) {
        return exercisesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.activity_exercises_category_fragment, parent, false);
        }

        Exercise exercise = getExercise(position);
        ((TextView) view.findViewById(R.id.textViewForListViewCategoryFragment)).setText(exercise.getName());

        return view;
    }

    private Exercise getExercise(int position) {
        return (Exercise) getItem(position);
    }
}