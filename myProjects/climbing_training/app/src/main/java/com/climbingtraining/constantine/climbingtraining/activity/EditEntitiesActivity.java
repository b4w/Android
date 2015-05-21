package com.climbingtraining.constantine.climbingtraining.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.data.common.CommonDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.AbstractEntity;
import com.climbingtraining.constantine.climbingtraining.data.dto.Category;
import com.climbingtraining.constantine.climbingtraining.data.dto.Equipment;
import com.climbingtraining.constantine.climbingtraining.data.dto.ICommonEntities;
import com.climbingtraining.constantine.climbingtraining.data.dto.TypeExercise;
import com.climbingtraining.constantine.climbingtraining.data.helpers.OrmHelper;
import com.climbingtraining.constantine.climbingtraining.fragments.DescriptionFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.LoadImageFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.OkCancelFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by KonstantinSysoev on 09.05.15.
 */
public class EditEntitiesActivity extends ActionBarActivity implements OkCancelFragment.IOkCancelFragmentCallBack {

    private final static String TAG = EditEntitiesActivity.class.getSimpleName();
    public final static String DIR_SD = "/Climbing_training/images/category/";

    private Toolbar toolbar;

    private String imageNameAndPath;
    private String name;
    private String description;
    private String comment;
    private String entityName;
    private int entityId;
    private String imageNameForSDCard;

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
        Intent intent = getIntent();
        imageNameAndPath = intent.getStringExtra(CategoryActivity.IMAGE_NAME_AND_PATH);
        name = intent.getStringExtra(CategoryActivity.NAME);
        description = intent.getStringExtra(CategoryActivity.DESCRIPTION);
        comment = intent.getStringExtra(CategoryActivity.COMMENT);
        entityName = intent.getStringExtra(CategoryActivity.ENTITY);
        entityId = intent.getIntExtra(CategoryActivity.ENTITY_ID, entityId);
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
    public void clickOkBtn() {

        initDBConnection();

//        TODO переделать, т.к. фрагментов уже может не быть
        FragmentManager fragmentManager = getFragmentManager();
        DescriptionFragment descriptionFragment = (DescriptionFragment)
                fragmentManager.findFragmentByTag(DescriptionFragment.class.getSimpleName());

        LoadImageFragment loadImageFragment = (LoadImageFragment)
                fragmentManager.findFragmentByTag(LoadImageFragment.class.getSimpleName());

        if(!saveImageToSDCard(loadImageFragment.getFragmentLoadImageImage(), descriptionFragment.getName())) {
            Toast.makeText(this, getString(R.string.saving_image_sd), Toast.LENGTH_SHORT).show();
        }

//        TODO передавать entity, а не поля, т.к. потом создаем new Entity();
        entity.setId(entityId);
        entity.setName(descriptionFragment.getName());
        entity.setImagePath(imageNameAndPath != null ? imageNameAndPath : "");
        entity.setDescription(descriptionFragment.getDescription());
        entity.setComment(descriptionFragment.getComment());

        saveDataToDB();
        backToCategory();
    }

    @Override
    public void clickCancelBnt() {
        Toast.makeText(this, getString(R.string.cancel), Toast.LENGTH_SHORT).show();
        getFragmentManager().popBackStack();
    }

    private void initDBConnection() {

        if (entityName.equals(Category.class.getSimpleName())) {
            ormHelper = new OrmHelper(this, ICommonEntities.CATEGORIES_DATABASE_NAME,
                    ICommonEntities.CATEGORIES_DATABASE_VERSION);
            entity = new Category();
        } else if (entityName.equals(Equipment.class.getSimpleName())) {
            ormHelper = new OrmHelper(this, ICommonEntities.EQUIPMENTS_DATABASE_NAME,
                    ICommonEntities.EQUIPMENTS_DATABASE_VERSION);
            entity = new Equipment();
        } else {
            ormHelper = new OrmHelper(this, ICommonEntities.TYPE_EXERCISES_DATABASE_NAME,
                    ICommonEntities.TYPE_EXERCISES_DATABASE_VERSION);
            entity = new TypeExercise();
        }
    }

    private void loadFragments() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        LoadImageFragment loadImageFragment = LoadImageFragment.newInstance();
        Bundle bundle = new Bundle();
//        TODO не забыть изменить на путь к файлу + сделать общий интерфейс для полей
        bundle.putString(CategoryActivity.IMAGE_NAME_AND_PATH, imageNameAndPath);
        loadImageFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.edit_entities_container_one, loadImageFragment, LoadImageFragment.class.getSimpleName());

        DescriptionFragment descriptionFragment = DescriptionFragment.newInstance();
        bundle.putString(CategoryActivity.NAME, name);
        bundle.putString(CategoryActivity.DESCRIPTION, description);
        bundle.putString(CategoryActivity.COMMENT, comment);
        descriptionFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.edit_entities_container_two, descriptionFragment, DescriptionFragment.class.getSimpleName());

        OkCancelFragment okCancelFragment = OkCancelFragment.newInstance();
        fragmentTransaction.add(R.id.edit_entities_container_three, okCancelFragment, OkCancelFragment.class.getSimpleName());

        // добавляем в стек, что бы потом по кнопкам ok, cancel вернуться на view
        fragmentTransaction.addToBackStack(DescriptionFragment.class.getSimpleName());
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

    private boolean saveImageToSDCard(ImageView imageView, String entityTitle) {
        BitmapDrawable btmpDr = (BitmapDrawable) imageView.getDrawable();
        Bitmap bmp = btmpDr.getBitmap();

        // проверяем доступность cd
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d(TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return false;
        }
        // название файла
        imageNameForSDCard = entityTitle + ".jpg";
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
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
        Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
        startActivity(intent);
    }
}
