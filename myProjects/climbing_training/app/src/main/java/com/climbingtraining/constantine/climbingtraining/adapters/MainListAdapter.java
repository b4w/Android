package com.climbingtraining.constantine.climbingtraining.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.pojo.MainList;

import java.util.List;

/**
 * Created by KonstantinSysoev on 29.04.15.
 */
public class MainListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<MainList> mainLists;

    public MainListAdapter(Context context, List<MainList> mainLists) {
        this.context = context;
        this.mainLists = mainLists;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mainLists.size();
    }

    @Override
    public Object getItem(int position) {
        return mainLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.main_list_layout, null);
        }
        MainList mainList = (MainList) getItem(position);
        ImageView image = (ImageView) convertView.findViewById(R.id.main_list_layout_logo);
        image.setImageResource(mainList.getLogo());
        TextView title = (TextView) convertView.findViewById(R.id.main_list_layout_title);
        title.setText(mainList.getTitle());
        return convertView;
    }
}
