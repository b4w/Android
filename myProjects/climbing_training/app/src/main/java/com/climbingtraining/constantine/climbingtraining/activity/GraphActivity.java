package com.climbingtraining.constantine.climbingtraining.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.fragments.GraphFragment;

/**
 * Created by KonstantinSysoev on 02.05.15.
 */
public class GraphActivity extends AppCompatActivity {


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

        // load graph fragment
        FragmentManager fragmentManager = getFragmentManager();
        GraphFragment graphFragment = GraphFragment.newInstance();
        fragmentManager.beginTransaction()
                .replace(R.id.graph_container, graphFragment)
                .commit();
    }
}
