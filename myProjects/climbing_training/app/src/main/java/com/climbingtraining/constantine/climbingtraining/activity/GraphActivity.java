package com.climbingtraining.constantine.climbingtraining.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.climbingtraining.constantine.climbingtraining.R;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

/**
 * Created by KonstantinSysoev on 02.05.15.
 */
public class GraphActivity extends ActionBarActivity {

    private ListView graphLayoutListView;
    private FloatingActionButton graphLayoutFab;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);

        toolbar = (Toolbar) findViewById(R.id.graph_layout_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        graphLayoutListView = (ListView) findViewById(R.id.graph_layout_list_view);
        graphLayoutFab = (FloatingActionButton) findViewById(R.id.graph_layout_fab);
        graphLayoutFab.attachToListView(graphLayoutListView);

//        TODO Убрать тестовые данные!
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(" i = " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list);
        graphLayoutListView.setAdapter(adapter);

    }
}
