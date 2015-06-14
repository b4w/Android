package com.climbingtraining.constantine.climbingtraining.activity;

import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.data.common.CommonDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.AbstractEntity;
import com.climbingtraining.constantine.climbingtraining.data.dto.Category;
import com.climbingtraining.constantine.climbingtraining.data.dto.Equipment;
import com.climbingtraining.constantine.climbingtraining.data.dto.ICommonEntities;
import com.climbingtraining.constantine.climbingtraining.data.dto.TypeExercise;
import com.climbingtraining.constantine.climbingtraining.data.helpers.OrmHelper;
import com.climbingtraining.constantine.climbingtraining.fragments.CategoriesFragments.AbstractCategoriesFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.CategoriesFragments.CategoryFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.CategoriesFragments.EquipmentFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.CategoriesFragments.TypeExerciseFragment;
import com.climbingtraining.constantine.climbingtraining.pojo.CategoriesParcelable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by KonstantinSysoev on 09.05.15.
 */
public class EditEntitiesActivity extends AppCompatActivity implements AbstractCategoriesFragment.IAbstractFragmentCallBack {

    private final static String TAG = EditEntitiesActivity.class.getSimpleName();
    public final static String DIR_SD = "/Climbing training/images/categories/";

    private Toolbar toolbar;

    private String imageNameAndPath;
    private String entityName;
    private String imageNameForSDCard;

    private CategoriesParcelable categoriesParcelable;

    private OrmHelper ormHelper;
    private AbstractEntity entity;
    private CommonDao commonDao;

    private MenuItem menuItemShare;
    private MenuItem menuItemDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_entities_layout);
        initFields();
        initToolbar();
        loadFragments();
    }

    private void initToolbar() {
        Log.d(TAG, "initToolbar() start");
        toolbar = (Toolbar) findViewById(R.id.edit_entities_layout_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.edit) + " " + entityName);
        }
        Log.d(TAG, "initToolbar() done");
    }

    private void initFields() {
        Log.d(TAG, "initFields() start");
        categoriesParcelable = getIntent().getParcelableExtra(CategoriesActivity.CATEGORIES_PARCELABLE);
        // редактирование сущности
        if (categoriesParcelable != null) {
            initEntity(categoriesParcelable);
        }
        // создание новой сущности
        else {
            entityName = getIntent().getStringExtra(CategoriesActivity.ENTITY);
        }
        Log.d(TAG, "initFields() done");
    }

    private void initEntity(CategoriesParcelable categoriesParcelable) {
        Log.d(TAG, "initEntity() start");
        if (categoriesParcelable.getEntity().equals(Category.class.getSimpleName())) {
            entity = new Category();
        } else if (categoriesParcelable.getEntity().equals(Equipment.class.getSimpleName())) {
            entity = new Equipment();
        } else if (categoriesParcelable.getEntity().equals(TypeExercise.class.getSimpleName())) {
            entity = new TypeExercise();
        }

        entityName = categoriesParcelable.getEntity();
        imageNameAndPath = categoriesParcelable.getImageNameAndPath();

        entity.setId(categoriesParcelable.getEntityId());
        entity.setName(categoriesParcelable.getName());
        entity.setComment(categoriesParcelable.getComment());
        entity.setDescription(categoriesParcelable.getDescription());
        entity.setImagePath(categoriesParcelable.getImageNameAndPath());
        Log.d(TAG, "initEntity() done");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_edit_entity, menu);
        menuItemShare = menu.findItem(R.id.menu_toolbar_share);
        menuItemDelete = menu.findItem(R.id.menu_toolbar_delete);
        initMenuListeners();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void saveEntity(AbstractEntity abstractEntity, Drawable drawable) {
        Log.d(TAG, "saveEntity() start");
        initDBConnection(abstractEntity);
        if (!saveImageToSDCard(drawable)) {
            Toast.makeText(this, getString(R.string.saving_image_sd), Toast.LENGTH_SHORT).show();
        }
        entity.setImagePath(imageNameAndPath != null ? imageNameAndPath : "");
        saveDataToDB();
        onBackPressed();
        Log.d(TAG, "saveEntity() done");
    }

    @Override
    public void cancel() {
        Log.d(TAG, "cancel() start");
        Toast.makeText(this, getString(R.string.cancel), Toast.LENGTH_SHORT).show();
        onBackPressed();
        Log.d(TAG, "cancel() done");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ormHelper != null) {
            ormHelper.close();
        }
    }

    private void initMenuListeners() {
        Log.d(TAG, "initMenuListeners() start");
        menuItemShare.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getApplicationContext(), "It doesn't work yet", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        menuItemDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                deleteDataFromDB();
                deleteImageFromSDCard();
                onBackPressed();
                return false;
            }
        });
        Log.d(TAG, "initMenuListeners() done");
    }

    private void initDBConnection(AbstractEntity abstractEntity) {
        Log.d(TAG, "initDBConnection() start");
        ormHelper = new OrmHelper(this, ICommonEntities.CLIMBING_TRAINING_DB_NAME,
                ICommonEntities.CLIMBING_TRAINING_DB_VERSION);
        entity = abstractEntity;
        Log.d(TAG, "initDBConnection() done");
    }

    private void loadFragments() {
        Log.d(TAG, "loadFragments() start");
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        AbstractCategoriesFragment fragment = null;
        Bundle bundle;

        if (entityName.equals(Category.class.getSimpleName())) {
            fragment = CategoryFragment.newInstance();
        } else if (entityName.equals(Equipment.class.getSimpleName())) {
            fragment = EquipmentFragment.newInstance();
        } else if (entityName.equals(TypeExercise.class.getSimpleName())) {
            fragment = TypeExerciseFragment.newInstance();
        }

        if (fragment != null) {
            // передаем данные, если редактируем сущность
            if (categoriesParcelable != null) {
                bundle = new Bundle();
//                TODO: посмотреть подробнее, возможно нет необходимости тянуть объект
                bundle.putParcelable(CategoriesActivity.CATEGORIES_PARCELABLE, categoriesParcelable);
                fragment.setArguments(bundle);
            }
            fragmentTransaction.replace(R.id.edit_entities_container, fragment, fragment.getClass().getSimpleName());
            // добавляем в стек, что бы потом по кнопкам ok, cancel вернуться на view
            // addBackStack - null ?
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragmentTransaction.commit();
        Log.d(TAG, "loadFragments() done");
    }

    private void saveDataToDB() {
        Log.d(TAG, "saveDataToDB() start");
        if (entity != null && ormHelper != null) {
            try {
                commonDao = ormHelper.getDaoByClass(entity.getClass());
                if (commonDao != null)
                    // записываем или обновляем данные в БД
                    commonDao.createOrUpdate(entity);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (commonDao != null) {
                    // проверяем добавилась запись или нет
                    List<AbstractEntity> result = commonDao.queryBuilder().where().like(ICommonEntities.COLUMN_NAME_NAME, entity.getName() + "%").query();
                    if (result != null && !result.isEmpty()) {
                        Toast.makeText(this, entityName + " " + getString(R.string.successfully_added), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, getString(R.string.error_added), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // Закрываем все подключения
            ormHelper.close();
        }
        Log.d(TAG, "saveDataToDB() done");
    }

    private void deleteDataFromDB() {
        Log.d(TAG, "deleteDataFromDB() start");
        initDBConnection(entity);
        try {
            commonDao = ormHelper.getDaoByClass(entity.getClass());
            commonDao.delete(entity);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, "deleteDataFromDB() done");
    }

    private void deleteImageFromSDCard() {
        Log.d(TAG, "deleteImageFromSDCard() start");
        // проверяем доступность cd карты
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d(TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
        File file = new File(entity.getImagePath());
        if (!file.delete()) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.file_not_delete), Toast.LENGTH_SHORT).show();
        }
        Log.d(TAG, "deleteImageFromSDCard() done");
    }

    private boolean saveImageToSDCard(Drawable drawable) {
        Log.d(TAG, "saveImageToSDCard() start");
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bmp = bitmapDrawable.getBitmap();

        // проверяем доступность cd
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d(TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return false;
        }
        // название файла
        imageNameForSDCard = entity.getName() + ".jpg";
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD + "/" + entity.getClass().getSimpleName());
        // создаем каталог
        sdPath.mkdirs();
        // формируем объект File, который содержит путь к файлу
        File imageToSd = new File(sdPath, imageNameForSDCard);
        try {
            FileOutputStream fos = new FileOutputStream(imageToSd);
            // 100 - 100% качество
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            // пишем данные
            fos.flush();
            // закрываем поток
            fos.close();
            Log.d(TAG, "Файл записан на SD: " + imageToSd.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        imageNameAndPath = sdPath + "/" + imageNameForSDCard;
        Log.d(TAG, "saveImageToSDCard() done");
        return true;
    }
}
