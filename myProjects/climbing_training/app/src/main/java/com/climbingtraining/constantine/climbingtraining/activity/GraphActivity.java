package com.climbingtraining.constantine.climbingtraining.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.fragments.GraphFragment;

/**
 * Created by KonstantinSysoev on 02.05.15.
 */
public class GraphActivity extends ActionBarActivity {


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

        GraphFragment graphFragment = (GraphFragment) getFragmentManager().findFragmentById(R.id.graph_fragment);

    }
}
