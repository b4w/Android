package com.climbingtraining.constantine.climbingtraining.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.data.dto.MainList;
import com.j256.ormlite.android.apptools.OrmLiteCursorAdapter;

/**
 * Created by KonstantinSysoev on 29.04.15.
 */
public class MainListAdapter extends OrmLiteCursorAdapter<MainList, View> {

    private final static String TAG = MainListAdapter.class.getSimpleName();
    private final LayoutInflater inflater;

    public MainListAdapter(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void bindView(View view, Context context, MainList mainList) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.logo.setImageResource(mainList.getLogo());
        holder.title.setText(mainList.getTitle());
        holder.text.setText(mainList.getText());
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        final View view = inflater.inflate(R.layout.main_list_layout, parent, false);
        holder.logo = (ImageView) view.findViewById(R.id.main_list_layout_logo);
        holder.title = (TextView) view.findViewById(R.id.main_list_layout_title);
        holder.text = (TextView) view.findViewById(R.id.main_list_layout_text);
        view.setTag(holder);
        return view;
    }

    static class ViewHolder {
        public ImageView logo;
        public TextView title;
        public TextView text;
    }
}
