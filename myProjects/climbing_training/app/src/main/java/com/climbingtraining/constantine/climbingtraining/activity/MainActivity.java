package com.climbingtraining.constantine.climbingtraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        MainListAdapter adapter = new MainListAdapter(this, initMainList());
        mainLayoutListView.setAdapter(adapter);

        initializeNavigationDrawer();
    }

    @Override
    public void onBackPressed() {
        if (drawerResult != null && drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    private void initializeNavigationDrawer() {
        drawerResult = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowToolbar(true)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(initializeDrawerItems())
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
    }

    private IDrawerItem[] initializeDrawerItems() {
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
        List<MainList> mainLists = new ArrayList<>();
        mainLists.add(new MainList(R.drawable.training, getString(R.string.trainings), ""));
        mainLists.add(new MainList(R.drawable.exercise, getString(R.string.exercises), ""));
        mainLists.add(new MainList(R.drawable.graph, getString(R.string.categories), ""));
        return mainLists;
    }
}
