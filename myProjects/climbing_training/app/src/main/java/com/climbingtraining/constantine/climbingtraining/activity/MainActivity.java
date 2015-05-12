package com.climbingtraining.constantine.climbingtraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.adapters.MainListAdapter;
import com.climbingtraining.constantine.climbingtraining.data.dto.ICommonEntities;
import com.climbingtraining.constantine.climbingtraining.pojo.MainList;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Toolbar toolbar;
    private ListView mainLayoutListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        // библиотека для работы с БД в браузере
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());

        toolbar = (Toolbar) findViewById(R.id.main_layout_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mainLayoutListView = (ListView) findViewById(R.id.main_layout_list_view);

        mainLayoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case ICommonEntities.SHEET_ITEM_TRAINING:
                        intent = new Intent(getApplicationContext(), TrainingsActivity.class);
                        break;
                    case ICommonEntities.SHEET_ITEM_EXERCISE:
                        intent = new Intent(getApplicationContext(), ExercisesActivity.class);
                        break;
                    case ICommonEntities.SHEET_CATEGORY:
                        intent = new Intent(getApplicationContext(), CategoryActivity.class);
                        break;
                }
                if (intent != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), "ERROR!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        MainListAdapter adapter = new MainListAdapter(this, initMainList());
        mainLayoutListView.setAdapter(adapter);
    }

    private List<MainList> initMainList() {
        List<MainList> mainLists = new ArrayList<>();
        mainLists.add(new MainList(R.drawable.training, getString(R.string.trainings), ""));
        mainLists.add(new MainList(R.drawable.exercise, getString(R.string.exercises), ""));
        mainLists.add(new MainList(R.drawable.graph, getString(R.string.category), ""));
        return mainLists;
    }
}
