package com.climbingtraining.constantine.climbingtraining.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.data.dto.TypeExercise;

import java.util.List;

/**
 * Created by KonstantinSysoev on 09.05.15.
 */
public class TypesExercisesListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<TypeExercise> typeExercises;

    public TypesExercisesListAdapter(Context context, List<TypeExercise> typeExercises) {
        this.context = context;
        this.typeExercises = typeExercises;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return typeExercises.size();
    }

    @Override
    public Object getItem(int position) {
        return typeExercises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.types_exercises_list_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.types_exercises_list_layout_image);
            viewHolder.title = (TextView) convertView.findViewById(R.id.types_exercises_list_layout_title);
            viewHolder.description = (TextView) convertView.findViewById(R.id.types_exercises_list_layout_description);
            viewHolder.comments = (TextView) convertView.findViewById(R.id.types_exercises_list_layout_comments);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TypeExercise typeExercise = (TypeExercise) getItem(position);
        viewHolder.idCategory = typeExercise.getId();

        if (!typeExercise.getImagePath().isEmpty()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(typeExercise.getImagePath());
            viewHolder.image.setImageBitmap(myBitmap);
        }

        viewHolder.title.setText(typeExercise.getName());
        viewHolder.description.setText(typeExercise.getDescription());
        viewHolder.comments.setText(typeExercise.getComment());

        return convertView;
    }

    static class ViewHolder {
        public int idCategory;
        public ImageView image;
        public TextView title;
        public TextView description;
        public TextView comments;
    }
}
