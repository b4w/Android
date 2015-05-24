package com.climbingtraining.constantine.climbingtraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.adapters.ViewPagerAdapter;
import com.climbingtraining.constantine.climbingtraining.data.dto.Category;
import com.climbingtraining.constantine.climbingtraining.data.dto.Equipment;
import com.climbingtraining.constantine.climbingtraining.data.dto.TypeExercise;
import com.climbingtraining.constantine.climbingtraining.fragments.CategoriesFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.EquipmentsFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.TypesExercisesFragment;
import com.climbingtraining.constantine.climbingtraining.pojo.CategoriesParcelable;
import com.climbingtraining.constantine.climbingtraining.utils.SlidingTabLayout;

/**
 * Created by KonstantinSysoev on 08.05.15.
 */
public class CategoriesActivity extends AppCompatActivity implements CategoriesFragment.ICategoryFragmentCallBack,
        EquipmentsFragment.IEquipmentsFragmentCallBack, TypesExercisesFragment.ITypesExercisesFragmentCallBack {

    private final String TAG = Category.class.getSimpleName();

    public final static String IMAGE_NAME_AND_PATH = "imageNameAndPath";
    public final static String NAME = "name";
    public final static String DESCRIPTION = "description";
    public final static String COMMENT = "comment";
    public final static String ENTITY = "entity";
    public final static String ENTITY_ID = "entityId";

    public final static String CATEGORIES_PARCELABLE = "categoriesParcelable";
    public final static String CATEGORIES_ID = "categoriesId";
    public final static String EQUIPMENTS_ID = "equipmentsId";
    public final static String TYPE_EXERCISES_ID = "typeExercisesId";
    public final static String CATEGORIES_NAME = "categoriesName";
    public final static String EQUIPMENTS_NAME = "equipmentsName";
    public final static String TYPE_EXERCISES_NAME = "typeExercisesName";


    private Toolbar toolbar;
    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private int numbOfTabs = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_layout);

        toolbar = (Toolbar) findViewById(R.id.category_layout_toolbar);

        initializeToolbar();
        initializeTabs();
    }

    private void initializeToolbar() {
        Log.d(TAG, "initializeToolbar() start");
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.categories));
        }
        Log.d(TAG, "initializeToolbar() done");
    }

    private void initializeTabs() {
        Log.d(TAG, "initializeTabs() start");
        CharSequence titles[] = {getString(R.string.category), getString(R.string.equipment), getString(R.string.type_of_exercise)};
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), titles, numbOfTabs);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        // true - фиксированные вкладки
        tabs.setDistributeEvenly(true);

        // выбор цвета полосы под выбранным табом
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.dark_primary_color);
            }
        });

        tabs.setViewPager(pager);
        Log.d(TAG, "initializeTabs() done");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void editCategory(Category category) {
        Intent intent = new Intent(getApplicationContext(), EditEntitiesActivity.class);
        intent.putExtra(CATEGORIES_PARCELABLE,
                new CategoriesParcelable(String.valueOf(category.getImagePath()),
                        category.getName(),
                        category.getDescription(),
                        category.getComment(),
                        category.getClass().getSimpleName(),
                        category.getId()));
        startActivity(intent);
    }

    @Override
    public void createNewCategory() {
        Intent intent = new Intent(getApplicationContext(), EditEntitiesActivity.class);
        intent.putExtra(ENTITY, Category.class.getSimpleName());
        startActivity(intent);
    }

    @Override
    public void editEquipment(Equipment equipment) {
        Intent intent = new Intent(getApplicationContext(), EditEntitiesActivity.class);
        intent.putExtra(CATEGORIES_PARCELABLE,
                new CategoriesParcelable(String.valueOf(equipment.getImagePath()),
                        equipment.getName(),
                        equipment.getDescription(),
                        equipment.getComment(),
                        equipment.getClass().getSimpleName(),
                        equipment.getId()));
        startActivity(intent);
    }

    @Override
    public void createNewEquipment() {
        Intent intent = new Intent(getApplicationContext(), EditEntitiesActivity.class);
        intent.putExtra(ENTITY, Equipment.class.getSimpleName());
        startActivity(intent);
    }

    @Override
    public void editTypeExercise(TypeExercise typeExercise) {
        Intent intent = new Intent(getApplicationContext(), EditEntitiesActivity.class);
        intent.putExtra(CATEGORIES_PARCELABLE,
                new CategoriesParcelable(String.valueOf(typeExercise.getImagePath()),
                        typeExercise.getName(),
                        typeExercise.getDescription(),
                        typeExercise.getComment(),
                        typeExercise.getClass().getSimpleName(),
                        typeExercise.getId()));
        startActivity(intent);
    }

    @Override
    public void createNewTypeExercise() {
        Intent intent = new Intent(getApplicationContext(), EditEntitiesActivity.class);
        intent.putExtra(ENTITY, TypeExercise.class.getSimpleName());
        startActivity(intent);
    }
}
