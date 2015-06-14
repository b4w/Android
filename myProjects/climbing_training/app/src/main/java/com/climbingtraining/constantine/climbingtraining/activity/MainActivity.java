package com.climbingtraining.constantine.climbingtraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.adapters.MainListAdapter;
import com.climbingtraining.constantine.climbingtraining.data.dto.ICommonEntities;
import com.climbingtraining.constantine.climbingtraining.pojo.MainList;
import com.facebook.stetho.Stetho;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Toolbar toolbar;
    private ListView mainLayoutListView;
    private Drawer.Result drawerResult;

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

        initToolbar();
        initXmlFields();
        initListeners();
        initNavigationDrawer();

        MainListAdapter adapter = new MainListAdapter(this, initMainList());
        mainLayoutListView.setAdapter(adapter);
    }

    private void initToolbar() {
        Log.d(TAG, "initToolbar() start");
        toolbar = (Toolbar) findViewById(R.id.main_layout_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Log.d(TAG, "initToolbar() done");
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        mainLayoutListView = (ListView) findViewById(R.id.main_layout_list_view);
        Log.d(TAG, "initXmlFields() done");
    }

    private void initListeners() {
        Log.d(TAG, "initListeners() start");
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
                        intent = new Intent(getApplicationContext(), CategoriesActivity.class);
                        break;
                }
                if (intent != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }
        });
        Log.d(TAG, "initListeners() done");
    }

    @Override
    public void onBackPressed() {
        if (drawerResult != null && drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void initNavigationDrawer() {
        Log.d(TAG, "initNavigationDrawer() start");
        drawerResult = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(true)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(initDrawerItems())
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        Intent intent = null;
                        switch (i) {
                            case 1:
                                intent = new Intent(MainActivity.this, MainActivity.class);
                                break;
                            case 2:
                                intent = new Intent(MainActivity.this, SettingsActivity.class);
                                break;
                            case 3:
//                                intent = new Intent(MainActivity.this, CategoryActivity.class);
//                                break;
                        }
                        if (intent != null) {
                            startActivity(intent);
                        } else {
                            Toast.makeText(view.getContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .build();
        Log.d(TAG, "initNavigationDrawer() done");
    }

    private IDrawerItem[] initDrawerItems() {
        Log.d(TAG, "initDrawerItems()");
        return new IDrawerItem[]{new PrimaryDrawerItem()
                .withName(R.string.home)
                .withIdentifier(1)
                .withIcon(R.drawable.ic_home_black_36dp),
                new DividerDrawerItem(),
                new SecondaryDrawerItem()
                        .withName(R.string.settings)
                        .withIcon(R.drawable.ic_settings_black_36dp),
                new SecondaryDrawerItem()
                        .withName(R.string.about)
                        .withIcon(R.drawable.ic_info_black_36dp)};
    }

    private List<MainList> initMainList() {
        Log.d(TAG, "initMainList() start");
        List<MainList> mainLists = new ArrayList<>();
        mainLists.add(new MainList(R.drawable.training, getString(R.string.trainings), ""));
        mainLists.add(new MainList(R.drawable.exercise, getString(R.string.exercises), ""));
        mainLists.add(new MainList(R.drawable.graph, getString(R.string.categories), ""));
        Log.d(TAG, "initMainList() done");
        return mainLists;
    }
}
