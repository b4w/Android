package com.climbingtraining.constantine.climbingtraining.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.data.dto.Equipment;
import com.j256.ormlite.android.apptools.OrmLiteCursorAdapter;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by KonstantinSysoev on 09.05.15.
 */
public class EquipmentsListAdapter extends OrmLiteCursorAdapter<Equipment, View> {

    private LayoutInflater layoutInflater;

    public EquipmentsListAdapter(Context context) {
        super(context);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void bindView(View view, Context context, Equipment equipment) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.idCategory = equipment.getId();
        if (!equipment.getImagePath().isEmpty()) {
            File file = new File(equipment.getImagePath());
            Picasso.with(context).load(file).into(viewHolder.image);
        }
        viewHolder.title.setText(equipment.getName());
        viewHolder.description.setText(equipment.getDescription());
        viewHolder.comments.setText(equipment.getComment());
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        final View view = layoutInflater.inflate(R.layout.equipments_list_layout, parent, false);
        viewHolder.image = (ImageView) view.findViewById(R.id.equipments_list_layout_image);
        viewHolder.title = (TextView) view.findViewById(R.id.equipments_list_layout_title);
        viewHolder.description = (TextView) view.findViewById(R.id.equipments_list_layout_description);
        viewHolder.comments = (TextView) view.findViewById(R.id.equipments_list_layout_comments);
        view.setTag(viewHolder);
        return view;
    }

    public static class ViewHolder {
        public int idCategory;
        public ImageView image;
        public TextView title;
        public TextView description;
        public TextView comments;
        public boolean isChecked;
    }
}
