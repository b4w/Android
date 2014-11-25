package com.climbtraining.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.climbtraining.app.R;
import com.climbtraining.app.pojo.exercise.Category;

import java.util.List;

public class CategoriesAdapter extends BaseAdapter {

    private List<Category> categoryList;
    private LayoutInflater layoutInflater;

    public CategoriesAdapter(Context context, List<Category> categoryList) {
        this.categoryList = categoryList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
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

        Category category = getCategory(position);
        ((TextView) view.findViewById(R.id.textViewForListViewCategoryFragment)).setText(category.getName());

        return view;
    }

    private Category getCategory(int position) {
        return (Category) getItem(position);
    }
}
