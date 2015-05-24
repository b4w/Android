package com.climbingtraining.constantine.climbingtraining.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_entities_layout);

        toolbar = (Toolbar) findViewById(R.id.edit_entities_layout_toolbar);

        initializeFields();
        initializeToolbar();
        loadFragments();
    }

    private void initializeToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.edit) + " " + entityName);
        }
    }

    private void initializeFields() {
        categoriesParcelable = getIntent().getParcelableExtra(CategoriesActivity.CATEGORIES_PARCELABLE);
        // редактирование сущности
        if (categoriesParcelable != null) {
            imageNameAndPath = categoriesParcelable.getImageNameAndPath();
            entityName = categoriesParcelable.getEntity();
        }
        // создание новой сущности
        else {
            entityName = getIntent().getStringExtra(CategoriesActivity.ENTITY);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_edit_entity, menu);
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
        initDBConnection(abstractEntity);

        if (!saveImageToSDCard(drawable)) {
            Toast.makeText(this, getString(R.string.saving_image_sd), Toast.LENGTH_SHORT).show();
        }

        entity.setImagePath(imageNameAndPath != null ? imageNameAndPath : "");
        saveDataToDB();
//        TODO Переделать возврат на categories
        backToCategory();
    }

    @Override
    public void cancel() {
        Toast.makeText(this, getString(R.string.cancel), Toast.LENGTH_SHORT).show();
        getFragmentManager().popBackStack();
    }

    private void initDBConnection(AbstractEntity abstractEntity) {
        if (abstractEntity.getClass().equals(Category.class)) {
            ormHelper = new OrmHelper(this, ICommonEntities.CATEGORIES_DATABASE_NAME,
                    ICommonEntities.CATEGORIES_DATABASE_VERSION);
        } else if (abstractEntity.getClass().equals(Equipment.class)) {
            ormHelper = new OrmHelper(this, ICommonEntities.EQUIPMENTS_DATABASE_NAME,
                    ICommonEntities.EQUIPMENTS_DATABASE_VERSION);
        } else {
            ormHelper = new OrmHelper(this, ICommonEntities.TYPE_EXERCISES_DATABASE_NAME,
                    ICommonEntities.TYPE_EXERCISES_DATABASE_VERSION);
        }
        entity = abstractEntity;
    }

    private void loadFragments() {
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
                bundle.putParcelable(CategoriesActivity.CATEGORIES_PARCELABLE, categoriesParcelable);
                fragment.setArguments(bundle);
            }
            fragmentTransaction.replace(R.id.edit_entities_container, fragment, fragment.getClass().getSimpleName());
            // добавляем в стек, что бы потом по кнопкам ok, cancel вернуться на view
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragmentTransaction.commit();
    }

    private void saveDataToDB() {
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
            if (ormHelper != null)
                ormHelper.close();
        }
    }

    private boolean saveImageToSDCard(Drawable drawable) {
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
        return true;
    }

    private void backToCategory() {
        Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
        startActivity(intent);
    }
}
