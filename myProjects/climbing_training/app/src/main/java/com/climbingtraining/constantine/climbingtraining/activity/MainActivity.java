package com.climbingtraining.constantine.climbingtraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.adapters.MainListAdapter;
import com.climbingtraining.constantine.climbingtraining.data.util.MainListUtils;
import com.climbingtraining.constantine.climbingtraining.data.dto.IMainListDto;
import com.climbingtraining.constantine.climbingtraining.data.dto.MainListDto;
import com.climbingtraining.constantine.climbingtraining.pojo.MainList;
import com.facebook.stetho.Stetho;
import com.j256.ormlite.android.loadercallback.OrmCursorLoaderCallback;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int ORM_LOADER_ID = 5;

    private ListView mainLayoutListView;
    private Toolbar toolbar;

    private MainListAdapter mainListAdapter;
    private MainListUtils mainListUtils;

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

        mainListUtils = new MainListUtils();

        toolbar = (Toolbar) findViewById(R.id.main_layout_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initMainList();
        setListOnClick();

    }

    private void initMainList() {
        Log.i(TAG, "initMainList() stated");
        mainListUtils.initMainListDB(this, IMainListDto.DATABASE_NAME, IMainListDto.DATABASE_VERSION);

        List<MainList> mainLists = new ArrayList<>();
        mainLists.add(new MainList(R.drawable.training, getString(R.string.training), "Какой-то текст про тренировки"));
        mainLists.add(new MainList(R.drawable.exercise, getString(R.string.exercise), "Какой-то текст про упражнения"));
        mainLists.add(new MainList(R.drawable.graph, getString(R.string.graph), "Какой-то текст про графики"));
        mainLists.add(new MainList(R.drawable.training, getString(R.string.training), "Какой-то текст про тренировки"));
        mainLists.add(new MainList(R.drawable.exercise, getString(R.string.exercise), "Какой-то текст про упражнения"));
        mainLists.add(new MainList(R.drawable.graph, getString(R.string.graph), "Какой-то текст про графики"));

        mainListUtils.fillDataMainList(mainLists);

        // инициализация главного списка
        mainLayoutListView = (ListView) findViewById(R.id.main_layout_list_view);
        mainListAdapter = new MainListAdapter(this);
        ((AdapterView<ListAdapter>) mainLayoutListView).setAdapter(mainListAdapter);

        try {
            // ставим "callBack" на список
            OrmCursorLoaderCallback<MainListDto, Integer> ormCursorLoaderCallback =
                    new OrmCursorLoaderCallback<MainListDto, Integer>(this, mainListUtils.getMainListDao(), mainListUtils.getMainListDao().getTestQuery(), mainListAdapter);
            getLoaderManager().initLoader(ORM_LOADER_ID, null, ormCursorLoaderCallback);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "initMainList() done");
    }

    public void setListOnClick() {
        mainLayoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case IMainListDto.SHEET_ITEM_TRAINING:
                        intent = new Intent(getApplicationContext(), TrainingsActivity.class);
                        break;
                    case IMainListDto.SHEET_ITEM_EXERCISE:
                        intent = new Intent(getApplicationContext(), ExercisesActivity.class);
                        break;
                    case IMainListDto.SHEET_ITEM_GRAPH:
                        intent = new Intent(getApplicationContext(), GraphActivity.class);
                        break;
                }
                if (intent != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), "ERROR!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
