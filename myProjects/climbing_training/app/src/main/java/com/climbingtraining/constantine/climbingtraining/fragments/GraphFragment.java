package com.climbingtraining.constantine.climbingtraining.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.climbingtraining.constantine.climbingtraining.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KonstantinSysoev on 03.05.15.
 */
public class GraphFragment extends Fragment {

    private ListView graphLayoutListView;

    private List<String> tmpList;
    private ArrayAdapter<String> tmpAdapter;

    public static GraphFragment newInstance() {
        GraphFragment fragment = new GraphFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        TODO Убрать тестовые данные!
        tmpList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tmpList.add(" i = " + i);
        }
        tmpAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, tmpList);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        graphLayoutListView = (ListView) getActivity().findViewById(R.id.fragment_graph_list_view);

        graphLayoutListView.setAdapter(tmpAdapter);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph, container, false);
        return view;
    }
}
