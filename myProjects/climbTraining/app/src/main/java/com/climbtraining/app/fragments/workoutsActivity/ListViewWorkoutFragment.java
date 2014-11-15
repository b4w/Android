package com.climbtraining.app.fragments.workoutsActivity;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListViewWorkoutFragment extends ListFragment {

    private List<String> dataList;
    ArrayAdapter<String> adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, updateDataList());
        setListAdapter(adapter);
    }

    private List<String> updateDataList() {
        if (dataList == null) {
            dataList = new ArrayList<String>();
        }
        dataList.add("One");
        dataList.add("Two");
        dataList.add("Tree");
        return dataList;
    }

    public void addItemToDataList(String item) {
        dataList.add(item);
        adapter.notifyDataSetChanged();
    }
}