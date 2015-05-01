package com.climbingtraining.constantine.climbingtraining.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.adapters.MainListAdapter;
import com.climbingtraining.constantine.climbingtraining.data.OrmHelper;
import com.climbingtraining.constantine.climbingtraining.data.dao.MainListDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.MainListDto;
import com.climbingtraining.constantine.climbingtraining.pojo.MainList;
import com.facebook.stetho.Stetho;
import com.j256.ormlite.android.loadercallback.OrmCursorLoaderCallback;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int ORM_LOADER_ID = 5;

    private ListView mainLayoutListView;
    private OrmHelper ormHelper;
    private MainListDao mainListDao;
    private MainListDto mainListDto;
    private MainListAdapter mainListAdapter;

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

        setContentView(R.layout.main_layout);


        mainLayoutListView = (ListView) findViewById(R.id.main_layout_list_view);
        mainListAdapter = new MainListAdapter(this);
        ((AdapterView<ListAdapter>) mainLayoutListView).setAdapter(mainListAdapter);

        initTable();

        try {
            OrmCursorLoaderCallback<MainListDto, Integer> ormCursorLoaderCallback =
                    new OrmCursorLoaderCallback<MainListDto, Integer>(this, mainListDao, mainListDao.getTestQuery(), mainListAdapter);
            getLoaderManager().initLoader(ORM_LOADER_ID, null, ormCursorLoaderCallback);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void initTable() {
        List<MainList> mainLists = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mainLists.add(new MainList(i, i + " TITLE", i + " Some Text"));
        }
        initDB();
        fillSampleData(mainLists);
    }

    /**
     * Иниализация подключения к БД.
     */
    private void initDB() {
        Log.i(TAG, "initDB() started");
        ormHelper = new OrmHelper(this, "main_list",  1);
//        ormHelper.onCreate(ormHelper.getWritableDatabase(), ormHelper.getConnectionSource());
        ormHelper.clearDatabase();
        try {
            mainListDao = new MainListDao(ormHelper.getConnectionSource(), MainListDto.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "initDB() done");
    }

    /**
     * Запись данных в БД.
     *
     * @param mainLists
     */
    private void fillSampleData(List<MainList> mainLists) {
        Log.i(TAG, "fillSampleData() started");
        for (MainList item : mainLists) {
            mainListDto = new MainListDto(item.getLogo(), item.getTitle(), item.getText());
            try {
                mainListDao.create(mainListDto);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, "fillSampleData() done");
    }
}
