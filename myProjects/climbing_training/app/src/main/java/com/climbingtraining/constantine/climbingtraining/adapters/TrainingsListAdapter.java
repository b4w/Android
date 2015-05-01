package com.climbingtraining.constantine.climbingtraining.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.pojo.Trainings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KonstantinSysoev on 01.05.15.
 */
public class TrainingsListAdapter extends BaseAdapter {

//    TODO перенести в отдельный класс utils
    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Trainings> trainingsArrayList;

    public TrainingsListAdapter(Context context, List<Trainings> trainingsArrayList) {
        this.context = context;
        this.trainingsArrayList = trainingsArrayList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Возвращает количество элементов.
     * @return
     */
    @Override
    public int getCount() {
        return trainingsArrayList.size();
    }

    /**
     * Возвращает элемент списка по его позиции.
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return trainingsArrayList.get(position);
    }

    /**
     * Возвращает номер позиции элемента списка.
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.trainings_list_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.trainings_list_layout_icon);
            viewHolder.like = (CheckBox) convertView.findViewById(R.id.trainings_layout_like);
            viewHolder.date = (TextView) convertView.findViewById(R.id.trainings_list_layout_date);
            viewHolder.exercises = (TextView) convertView.findViewById(R.id.trainings_list_layout_exercises);
            viewHolder.comments = (TextView) convertView.findViewById(R.id.trainings_list_layout_comments);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Trainings training = (Trainings) getItem(position);
        viewHolder.icon.setImageResource(training.getIcon());
        viewHolder.like.setChecked(training.isLike());
        viewHolder.date.setText(sdf.format(training.getDate()));
        viewHolder.exercises.setText(training.getExercises().toString());
        viewHolder.comments.setText(training.getComments());

        return convertView;
    }

    static class ViewHolder {
//        public int idTraining;
        public ImageView icon;
        public CheckBox like;
        public TextView date;
        public TextView exercises;
        public TextView comments;
    }
}
