package com.climbingtraining.constantine.climbingtraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.adapters.ViewPagerAdapter;
import com.climbingtraining.constantine.climbingtraining.data.dto.Category;
import com.climbingtraining.constantine.climbingtraining.data.dto.Equipment;
import com.climbingtraining.constantine.climbingtraining.data.dto.TypeExercise;
import com.climbingtraining.constantine.climbingtraining.fragments.CategoryFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.EquipmentsFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.TypesExercisesFragment;
import com.climbingtraining.constantine.climbingtraining.utils.SlidingTabLayout;

/**
 * Created by KonstantinSysoev on 08.05.15.
 */
public class CategoryActivity extends ActionBarActivity implements CategoryFragment.ICategoryFragmentCallBack,
        EquipmentsFragment.IEquipmentsFragmentCallBack, TypesExercisesFragment.ITypesExercisesFragmentCallBack {

    public final static String IMAGE_NAME_AND_PATH = "imageNameAndPath";
    public final static String NAME = "name";
    public final static String DESCRIPTION = "description";
    public final static String COMMENT = "comment";
    public final static String ENTITY = "entity";
    public final static String ENTITY_ID = "entityId";

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
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            TODO разобраться с title
            toolbar.setTitle(getString(R.string.categories));
        }

        CharSequence titles[] = {getString(R.string.category), getString(R.string.equipment), getString(R.string.type_of_exercise)};
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), titles, numbOfTabs);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        // true - фиксированные вкладки
        tabs.setDistributeEvenly(false);

        // выбор цвета полосы под выбранным табом
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.text_icon);
            }
        });

        tabs.setViewPager(pager);
    }

    @Override
    public void editCategory(Category category) {
        Intent intent = new Intent(getApplicationContext(), EditEntitiesActivity.class);
//        TODO переделать на Bundle
        intent.putExtra(IMAGE_NAME_AND_PATH, String.valueOf(category.getImagePath()));
        intent.putExtra(NAME, category.getName());
        intent.putExtra(DESCRIPTION, category.getDescription());
        intent.putExtra(COMMENT, category.getComment());
        intent.putExtra(ENTITY, category.getClass().getSimpleName());
        intent.putExtra(ENTITY_ID, category.getId());
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
//        TODO переделать на Bundle
        intent.putExtra(IMAGE_NAME_AND_PATH, String.valueOf(equipment.getImagePath()));
        intent.putExtra(NAME, equipment.getName());
        intent.putExtra(DESCRIPTION, equipment.getDescription());
        intent.putExtra(COMMENT, equipment.getComment());
        intent.putExtra(ENTITY, equipment.getClass().getSimpleName());
        intent.putExtra(ENTITY_ID, equipment.getId());
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
//        TODO переделать на Bundle
        intent.putExtra(IMAGE_NAME_AND_PATH, String.valueOf(typeExercise.getImagePath()));
        intent.putExtra(NAME, typeExercise.getName());
        intent.putExtra(DESCRIPTION, typeExercise.getDescription());
        intent.putExtra(COMMENT, typeExercise.getComment());
        intent.putExtra(ENTITY, typeExercise.getClass().getSimpleName());
        intent.putExtra(ENTITY_ID, typeExercise.getId());
        startActivity(intent);
    }

    @Override
    public void createNewTypeExercise() {
        Intent intent = new Intent(getApplicationContext(), EditEntitiesActivity.class);
        intent.putExtra(ENTITY, TypeExercise.class.getSimpleName());
        startActivity(intent);
    }
}
