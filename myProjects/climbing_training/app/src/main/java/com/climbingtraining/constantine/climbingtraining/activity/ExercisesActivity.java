package com.climbingtraining.constantine.climbingtraining.activity;

import android.app.FragmentManager;
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
import com.climbingtraining.constantine.climbingtraining.data.dto.Exercise;
import com.climbingtraining.constantine.climbingtraining.data.dto.ICommonEntities;
import com.climbingtraining.constantine.climbingtraining.data.dto.TypeExercise;
import com.climbingtraining.constantine.climbingtraining.data.helpers.OrmHelper;
import com.climbingtraining.constantine.climbingtraining.fragments.ExerciseFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.ExercisesFragment;
import com.climbingtraining.constantine.climbingtraining.pojo.ExerciseParcelable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by KonstantinSysoev on 02.05.15.
 */
public class ExercisesActivity extends AppCompatActivity implements ExercisesFragment.IExercisesFragmentCallBack,
        ExerciseFragment.IExerciseFragmentCallBack {

    private final static String TAG = ExercisesActivity.class.getSimpleName();
    public final static String EXERCISE_PARCELABLE = "exerciseParcelable";
    public final static String DIR_SD = "/Climbing training/images/exercises/";

    private Toolbar toolbar;
    private ExercisesFragment exercisesFragment;

    private OrmHelper ormHelper;
    private Exercise entity;
    private CommonDao commonDao;

    private String imageNameForSDCard;
    private String imageNameAndPath;
    private String name;
    private String description;
    private String comment;
    private String entityName;
    private int entityId;

    private List<Category> categories;
    private List<Equipment> equipments;
    private List<TypeExercise> typeExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_layout);

        toolbar = (Toolbar) findViewById(R.id.exercises_layout_toolbar);

        initializeToolbar();
        initializeExercisesFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Создаем новое упражнение.
     */
    @Override
    public void createNewExercise() {
        Log.d(TAG, "createNewExercise() started");
        loadFragmentExercise();
        Log.d(TAG, "createNewExercise() done");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_edit_entity, menu);
        return true;
    }

    /**
     * Открываем упражнение на редактирование.
     * @param exercise
     */
    @Override
    public void editExercise(Exercise exercise) {
        Log.d(TAG, "editExercise() start");
        initFragments(exercise);
        Log.d(TAG, "editExercise() done");
    }

    /**
     * Сохраняем упражнение.
     * @param exercise
     * @param drawable
     */
    @Override
    public void saveExercise(Exercise exercise, Drawable drawable) {
        initDBConnection(exercise);
        if (!saveImageToSDCard(drawable)) {
            Toast.makeText(this, getString(R.string.saving_image_sd), Toast.LENGTH_SHORT).show();
        }
        entity.setImagePath(imageNameAndPath != null ? imageNameAndPath : "");
        saveDataToDB();
        //TODO ???
        onBackPressed();
//        getFragmentManager().popBackStack();
    }

    @Override
    public void cancel() {
        Toast.makeText(this, getString(R.string.cancel), Toast.LENGTH_SHORT).show();
        getFragmentManager().popBackStack();
    }

    private void initializeToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.exercises);
        }
    }

    /**
     * Начальная инициализация фрагмента Упражнения.
     */
    private void initializeExercisesFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        exercisesFragment = ExercisesFragment.newInstance();
        Log.d(TAG, "Create new fragment - Exercises fragment");
        fragmentManager.beginTransaction()
                .replace(R.id.exercises_container, exercisesFragment)
                .addToBackStack(ExercisesFragment.class.getSimpleName())
                .commit();
    }

    private void initFragments(Exercise exercise) {
        Log.d(TAG, "loadFragments() started");

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        ExerciseFragment fragment = ExerciseFragment.newInstance();
        fragmentTransaction.replace(R.id.exercises_container, fragment, ExerciseFragment.class.getSimpleName());

//      TODO переделать !!!
        updateSectionsExercise();

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXERCISE_PARCELABLE,
                new ExerciseParcelable(exercise.getImagePath(),
                        exercise.getName(),
                        exercise.getDescription(),
                        exercise.getComment(),
                        exercise.getId(),
                        exercise.getCategory().getId(),
                        exercise.getEquipment().getId(),
                        exercise.getTypeExercise().getId(),
                        categories,
                        equipments,
                        typeExercises));

        fragment.setArguments(bundle);

        // добавляем в стек, что бы потом по кнопкам ok, cancel вернуться на view
        fragmentTransaction.addToBackStack(ExerciseFragment.class.getSimpleName());
        fragmentTransaction.commit();

        Log.d(TAG, "loadFragments() done");
    }

    private void loadFragmentExercise() {
        Log.d(TAG, "loadFragmentExercise() started");
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        ExerciseFragment fragment = ExerciseFragment.newInstance();
        fragmentTransaction.replace(R.id.exercises_container, fragment, ExerciseFragment.class.getSimpleName());

//      TODO переделать !!!
        updateSectionsExercise();

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXERCISE_PARCELABLE, new ExerciseParcelable(categories, equipments, typeExercises));

        fragment.setArguments(bundle);
        fragmentTransaction.addToBackStack(ExerciseFragment.class.getSimpleName());
        fragmentTransaction.commit();

        Log.d(TAG, "loadFragmentExercise() done");
    }

    private void saveDataToDB() {
        if (entity != null && ormHelper != null) {
            try {
                commonDao = ormHelper.getDaoByClass(Exercise.class);
                if (commonDao != null) {

                    // записываем или обновляем данные в БД
                    ormHelper.getDaoByClass(Category.class).createOrUpdate(entity.getCategory());
                    ormHelper.getDaoByClass(Equipment.class).createOrUpdate(entity.getEquipment());
                    ormHelper.getDaoByClass(TypeExercise.class).createOrUpdate(entity.getTypeExercise());

                    commonDao.createOrUpdate(entity);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                // проверяем добавилась запись или нет
                List<AbstractEntity> result = commonDao.queryBuilder().where().like(ICommonEntities.COLUMN_NAME_NAME, entity.getName() + "%").query();
                if (result != null && !result.isEmpty()) {
                    Toast.makeText(this, entity.getClass().getSimpleName() + " " + getString(R.string.successfully_added), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getString(R.string.error_added), Toast.LENGTH_SHORT).show();
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

    private void initDBConnection(Exercise exercise) {
        ormHelper = new OrmHelper(this, ICommonEntities.EXERCISES_DATABASE_NAME,
                ICommonEntities.EXERCISE_DATABASE_VERSION);
        entity = exercise;
    }

    /**
     * Обновляем все категории, типы упражнений и оборудование для выбора в новом упражнении.
     */
    private void updateSectionsExercise() {
        Log.d(TAG, "updateSectionsExercise() start");
        ormHelper = new OrmHelper(this, ICommonEntities.CATEGORIES_DATABASE_NAME,
                ICommonEntities.CATEGORIES_DATABASE_VERSION);
        try {
            CommonDao commonDao = ormHelper.getDaoByClass(Category.class);
            if (commonDao != null) {
                categories = commonDao.queryForAll();
                Log.d(TAG, "Categories size = " + categories.size());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ormHelper = new OrmHelper(this, ICommonEntities.EQUIPMENTS_DATABASE_NAME,
                ICommonEntities.EQUIPMENTS_DATABASE_VERSION);
        try {
            CommonDao commonDao = ormHelper.getDaoByClass(Equipment.class);
            if (commonDao != null) {
                equipments = commonDao.queryForAll();
                Log.d(TAG, "Equipments size = " + equipments.size());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ormHelper = new OrmHelper(this, ICommonEntities.TYPE_EXERCISES_DATABASE_NAME,
                ICommonEntities.TYPE_EXERCISES_DATABASE_VERSION);
        try {
            CommonDao commonDao = ormHelper.getDaoByClass(TypeExercise.class);
            if (commonDao != null) {
                typeExercises = commonDao.queryForAll();
                Log.d(TAG, "TypeExercises size = " + typeExercises.size());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ormHelper.close();
        Log.d(TAG, "updateSectionsExercise() done");
    }
}
