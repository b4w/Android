package com.climbingtraining.constantine.climbingtraining.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.data.dto.AccountingQuantity;
import com.climbingtraining.constantine.climbingtraining.data.dto.Training;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by KonstantinSysoev on 01.05.15.
 */
public class TrainingsListAdapter extends BaseAdapter {

    //    TODO перенести в отдельный класс utils
    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Training> trainings;

    public TrainingsListAdapter(Context context, List<Training> trainings) {
        this.context = context;
        this.trainings = trainings;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Возвращает количество элементов.
     *
     * @return
     */
    @Override
    public int getCount() {
        return trainings.size();
    }

    /**
     * Возвращает элемент списка по его позиции.
     *
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return trainings.get(position);
    }

    /**
     * Возвращает номер позиции элемента списка.
     *
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
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.trainings_list_layout_icon);
//            viewHolder.like = (CheckBox) convertView.findViewById(R.id.trainings_layout_like);
            viewHolder.date = (TextView) convertView.findViewById(R.id.trainings_list_layout_date);
            viewHolder.exercises = (TextView) convertView.findViewById(R.id.trainings_list_layout_exercises);
            viewHolder.comments = (TextView) convertView.findViewById(R.id.trainings_list_layout_comments);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Training training = (Training) getItem(position);

        viewHolder.date.setText(training.getDate() != null ? sdf.format(training.getDate()) : "");
        if (!training.getPhysicalTrainingImagePath().isEmpty()) {
            File file = new File(training.getPhysicalTrainingImagePath());
            Picasso.with(context).load(file).into(viewHolder.imageView);
        }

        StringBuilder sb = new StringBuilder();
        for (AccountingQuantity item : training.getQuantities()) {
            if (item.getExercise() != null) {
                sb.append(item.getExercise().getName());
            }
        }
        viewHolder.exercises.setText(sb.toString());
        viewHolder.comments.setText(training.getComment());

        return convertView;
    }

    static class ViewHolder {
        public ImageView imageView;
        public TextView date;
        public TextView exercises;
        public TextView comments;
    }
}
