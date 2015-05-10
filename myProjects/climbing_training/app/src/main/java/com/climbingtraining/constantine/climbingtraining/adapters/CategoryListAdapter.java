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
import com.climbingtraining.constantine.climbingtraining.data.dto.Category;

import java.util.List;

/**
 * Created by KonstantinSysoev on 09.05.15.
 */
public class CategoryListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Category> categories;

    public CategoryListAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.category_list_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.category_list_layout_image);
            viewHolder.title = (TextView) convertView.findViewById(R.id.category_list_layout_title);
            viewHolder.description = (TextView) convertView.findViewById(R.id.category_list_layout_description);
            viewHolder.comments = (TextView) convertView.findViewById(R.id.category_list_layout_comments);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Category category = (Category) getItem(position);
        viewHolder.idCategory = category.getId();

        if (!category.getImagePath().isEmpty()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(category.getImagePath());
            viewHolder.image.setImageBitmap(myBitmap);
        }

        viewHolder.title.setText(category.getName());
        viewHolder.description.setText(category.getDescription());
        viewHolder.comments.setText(category.getComment());

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
