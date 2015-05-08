package com.climbingtraining.constantine.climbingtraining.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.pojo.Exercises;

import java.io.File;
import java.util.List;

/**
 * Created by KonstantinSysoev on 02.05.15.
 */
public class ExercisesListAdapter extends BaseExpandableListAdapter {

    private final static String TAG = ExercisesListAdapter.class.getSimpleName();

    private Context context;
    private LayoutInflater layoutInflater;
    private List<List<Exercises>> exercises;
    private Resources resources;

    public ExercisesListAdapter(Context context, List<List<Exercises>> exercises) {
        this.context = context;
        this.exercises = exercises;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        resources = context.getResources();
    }

    /**
     * Возвращает количество элементов (родителей) в списке.
     *
     * @return
     */
    @Override
    public int getGroupCount() {
        return exercises.size();
    }

    /**
     * Возвращает количество "детей" у "родителя".
     *
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return exercises.get(groupPosition).size();
    }

    /**
     * Возвращает родителя по заданной позиции.
     *
     * @param groupPosition
     * @return
     */
    @Override
    public Object getGroup(int groupPosition) {
        return exercises.get(groupPosition);
    }

    /**
     * Возвращает "ребенка" по заданному номеру "родителя" и "ребенка".
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return exercises.get(groupPosition).get(childPosition);
    }

    /**
     *
     * @param groupPosition
     * @return
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
//            convertView = layoutInflater.inflate(R.layout.exercises_group_layout, parent, false);
            convertView = layoutInflater.inflate(R.layout.exercises_group_layout, null);
        }

        if (isExpanded){
            //Изменяем что-нибудь, если текущая Group раскрыта
//            Toast.makeText(context, "Open", Toast.LENGTH_SHORT).show();
        }
        else{
            //Изменяем что-нибудь, если текущая Group скрыта
//            Toast.makeText(context, "Close", Toast.LENGTH_SHORT).show();
        }

        TextView groupName = (TextView) convertView.findViewById(R.id.exercises_group_layout_name);
        Exercises exercises = (Exercises)getChild(groupPosition, 0);
        groupName.setText(exercises != null ? exercises.getNameCategory() : "");

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.exercises_list_layout, null);
        }

        Exercises exercises = (Exercises) getChild(groupPosition, childPosition);

//        TODO разобраться с хранением и доставать данные с карточки
        ImageView image = (ImageView) convertView.findViewById(R.id.exercises_list_layout_image);
        image.setImageResource(exercises.getImage());

        TextView name = (TextView) convertView.findViewById(R.id.exercises_list_layout_name);
        name.setText(exercises.getName());

        TextView nameCategory = (TextView) convertView.findViewById(R.id.exercises_list_layout_name_category);
        nameCategory.setText(resources.getString(R.string.category) + ": " + exercises.getNameCategory());

        TextView typeExercise = (TextView) convertView.findViewById(R.id.exercises_list_layout_type_exercise);
        typeExercise.setText(resources.getString(R.string.type_of_exercise) + ": " + exercises.getTypeExercise());

        TextView equipment = (TextView) convertView.findViewById(R.id.exercises_list_layout_equipment);
        equipment.setText(resources.getString(R.string.equipment) + ": " + exercises.getEquipment());

        RatingBar rating = (RatingBar) convertView.findViewById(R.id.exercises_list_layout_rating);
        rating.setRating(exercises.getRating());

        TextView comment = (TextView) convertView.findViewById(R.id.exercises_list_layout_comment);
        comment.setText(resources.getString(R.string.comment) + ": " + exercises.getComment());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
