package com.climbingtraining.constantine.climbingtraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.adapters.ViewPagerAdapter;
import com.climbingtraining.constantine.climbingtraining.data.common.CommonDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.AbstractEntity;
import com.climbingtraining.constantine.climbingtraining.data.dto.Category;
import com.climbingtraining.constantine.climbingtraining.data.dto.Equipment;
import com.climbingtraining.constantine.climbingtraining.data.dto.ICommonEntities;
import com.climbingtraining.constantine.climbingtraining.data.dto.TypeExercise;
import com.climbingtraining.constantine.climbingtraining.data.helpers.OrmHelper;
import com.climbingtraining.constantine.climbingtraining.fragments.CategoriesFragments.CategoriesFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.CategoriesFragments.EquipmentsFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.CategoriesFragments.TypesExercisesFragment;
import com.climbingtraining.constantine.climbingtraining.pojo.CategoriesParcelable;
import com.climbingtraining.constantine.climbingtraining.utils.EntitiesForEditing;
import com.climbingtraining.constantine.climbingtraining.utils.SlidingTabLayout;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by KonstantinSysoev on 08.05.15.
 */
public class CategoriesActivity extends AppCompatActivity implements CategoriesFragment.ICategoryFragmentCallBack,
        EquipmentsFragment.IEquipmentsFragmentCallBack, TypesExercisesFragment.ITypesExercisesFragmentCallBack {

    private final String TAG = Category.class.getSimpleName();

    public final static String IMAGE_NAME_AND_PATH = "imageNameAndPath";
    public final static String ENTITY = "entity";

    public final static String CATEGORIES_PARCELABLE = "categoriesParcelable";
    public final static String CATEGORIES_ID = "categoriesId";
    public final static String EQUIPMENTS_ID = "equipmentsId";
    public final static String TYPE_EXERCISES_ID = "typeExercisesId";

    private Toolbar toolbar;
    private ViewPager pager;
    private ViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private int numbOfTabs = 3;

    private MenuItem menuItemShare;
    private MenuItem menuItemDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_layout);

        initToolbar();
        initTabs();
    }

    private void initToolbar() {
        Log.d(TAG, "initToolbar() start");
        toolbar = (Toolbar) findViewById(R.id.category_layout_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.categories));
        }
        Log.d(TAG, "initToolbar() done");
    }

    private void initTabs() {
        Log.d(TAG, "initTabs() start");
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
        Log.d(TAG, "initTabs() done");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_edit_entity, menu);
        menuItemShare = menu.findItem(R.id.menu_toolbar_share);
        menuItemDelete = menu.findItem(R.id.menu_toolbar_delete);
        menuItemShare.setVisible(false);
        menuItemDelete.setVisible(false);
        initMenuListeners();
        return true;
    }

    private void initMenuListeners() {
        Log.d(TAG, "initMenuListeners() start");
        menuItemShare.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getApplicationContext(), "It doesn't work yet", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        menuItemDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                deleteEntities(EntitiesForEditing.getInstance().getEntitiesForEditing());
                // TODO: не работает обновление категорий после удаления =(
                return true;
            }
        });
        Log.d(TAG, "initMenuListeners() done");
    }

    @Override
    public void showHideOptionsMenu(boolean entitiesIsChecked) {
        Log.d(TAG, "showHideOptionsMenu() start");
        boolean menuIsVisible;
        if (entitiesIsChecked) {
            menuIsVisible = true;
        } else {
            menuIsVisible = false;
        }
        menuItemDelete.setVisible(menuIsVisible);
        menuItemShare.setVisible(menuIsVisible);
        Log.d(TAG, "showHideOptionsMenu() done");
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
        Log.d(TAG, "editCategory() start");
        Intent intent = new Intent(getApplicationContext(), EditEntitiesActivity.class);
        intent.putExtra(CATEGORIES_PARCELABLE,
                new CategoriesParcelable(String.valueOf(category.getImagePath()),
                        category.getName(),
                        category.getDescription(),
                        category.getComment(),
                        category.getClass().getSimpleName(),
                        category.getId()));
        startActivity(intent);
        Log.d(TAG, "editCategory() done");
    }

    @Override
    public void createNewCategory() {
        Log.d(TAG, "createNewCategory() start");
        Intent intent = new Intent(getApplicationContext(), EditEntitiesActivity.class);
        intent.putExtra(ENTITY, Category.class.getSimpleName());
        startActivity(intent);
        Log.d(TAG, "createNewCategory() done");
    }

    @Override
    public void editEquipment(Equipment equipment) {
        Log.d(TAG, "editEquipment() start");
        Intent intent = new Intent(getApplicationContext(), EditEntitiesActivity.class);
        intent.putExtra(CATEGORIES_PARCELABLE,
                new CategoriesParcelable(String.valueOf(equipment.getImagePath()),
                        equipment.getName(),
                        equipment.getDescription(),
                        equipment.getComment(),
                        equipment.getClass().getSimpleName(),
                        equipment.getId()));
        startActivity(intent);
        Log.d(TAG, "editEquipment() done");
    }

    @Override
    public void createNewEquipment() {
        Log.d(TAG, "createNewEquipment() start");
        Intent intent = new Intent(getApplicationContext(), EditEntitiesActivity.class);
        intent.putExtra(ENTITY, Equipment.class.getSimpleName());
        startActivity(intent);
        Log.d(TAG, "createNewEquipment() done");
    }

    @Override
    public void editTypeExercise(TypeExercise typeExercise) {
        Log.d(TAG, "editTypeExercise() start");
        Intent intent = new Intent(getApplicationContext(), EditEntitiesActivity.class);
        intent.putExtra(CATEGORIES_PARCELABLE,
                new CategoriesParcelable(String.valueOf(typeExercise.getImagePath()),
                        typeExercise.getName(),
                        typeExercise.getDescription(),
                        typeExercise.getComment(),
                        typeExercise.getClass().getSimpleName(),
                        typeExercise.getId()));
        startActivity(intent);
        Log.d(TAG, "editTypeExercise() done");
    }

    @Override
    public void createNewTypeExercise() {
        Log.d(TAG, "createNewTypeExercise() start");
        Intent intent = new Intent(getApplicationContext(), EditEntitiesActivity.class);
        intent.putExtra(ENTITY, TypeExercise.class.getSimpleName());
        startActivity(intent);
        Log.d(TAG, "createNewTypeExercise() done");
    }

    private void deleteEntities(List<AbstractEntity> entities) {
        Log.d(TAG, "deleteEntities() start");
        OrmHelper ormHelper = new OrmHelper(this, ICommonEntities.CLIMBING_TRAINING_DB_NAME,
                ICommonEntities.CLIMBING_TRAINING_DB_VERSION);
        CommonDao commonDao = null;
        for (AbstractEntity entity : entities) {
            try {
                commonDao = ormHelper.getDaoByClass(entity.getClass());
                commonDao.delete(entity);
                deleteImageFromSD(entity.getImagePath());
            } catch (SQLException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        if (ormHelper != null)
            ormHelper.close();
        Log.d(TAG, "deleteEntities() done");
    }

    private void deleteImageFromSD(String imagePath) {
        Log.d(TAG, "deleteImageFromSD() start");
        // проверяем доступность cd карты
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d(TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
        File file = new File(imagePath);
        if (!file.delete()) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.file_not_delete), Toast.LENGTH_SHORT).show();
        }
        Log.d(TAG, "deleteImageFromSD() done");
    }
}
