package com.devcolibri.listviewexam.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.devcolibri.listviewexam.app.R;
import com.devcolibri.listviewexam.app.pojo.PhoneModel;

import java.util.List;

public class PhoneModelAdapter extends BaseAdapter {

    private List<PhoneModel> list;
    private LayoutInflater layoutInflater;

    public PhoneModelAdapter(Context context, List<PhoneModel> list) {
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_layout, parent, false);
        }

        PhoneModel phoneModel = getPhoneModel(position);

        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(phoneModel.getName());

        return view;
    }

    private PhoneModel getPhoneModel(int position) {
        return (PhoneModel) getItem(position);
    }
}
