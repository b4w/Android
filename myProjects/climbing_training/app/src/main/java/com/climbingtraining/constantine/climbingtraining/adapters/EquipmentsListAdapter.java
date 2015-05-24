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
import com.climbingtraining.constantine.climbingtraining.data.dto.Equipment;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by KonstantinSysoev on 09.05.15.
 */
public class EquipmentsListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Equipment> equipments;

    public EquipmentsListAdapter(Context context, List<Equipment> equipments) {
        this.context = context;
        this.equipments = equipments;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return equipments.size();
    }

    @Override
    public Object getItem(int position) {
        return equipments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.equipments_list_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.equipments_list_layout_image);
            viewHolder.title = (TextView) convertView.findViewById(R.id.equipments_list_layout_title);
            viewHolder.description = (TextView) convertView.findViewById(R.id.equipments_list_layout_description);
            viewHolder.comments = (TextView) convertView.findViewById(R.id.equipments_list_layout_comments);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Equipment equipment = (Equipment) getItem(position);
        viewHolder.idCategory = equipment.getId();

        if (!equipment.getImagePath().isEmpty()) {
            File file = new File(equipment.getImagePath());
            Picasso.with(context).load(file).into(viewHolder.image);
        }

        viewHolder.title.setText(equipment.getName());
        viewHolder.description.setText(equipment.getDescription());
        viewHolder.comments.setText(equipment.getComment());

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
