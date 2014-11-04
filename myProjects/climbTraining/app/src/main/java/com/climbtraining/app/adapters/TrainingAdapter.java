package com.climbtraining.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.climbtraining.app.R;
import com.climbtraining.app.pojo.Training;

import java.util.List;

public class TrainingAdapter extends BaseAdapter {

    private List<Training> trainingList;
    private LayoutInflater layoutInflater;

    public TrainingAdapter (Context context, List<Training> trainingList) {
        this.trainingList = trainingList;
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return trainingList.size();
    }

    @Override
    public Object getItem(int position) {
        return trainingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            // todo проверить выбор layout - R.layout.activity_workouts
            view = layoutInflater.inflate(R.layout.activity_workouts, parent, false);
        }

//        Training training = getTraining(position);
//        TextView textView = (TextView) view.findViewById(R.id.textView);
//        textView.setText(training.getNameTraining());

        return view;
    }

    private Training getTraining(int position) {
        return (Training) getItem(position);
    }
}
