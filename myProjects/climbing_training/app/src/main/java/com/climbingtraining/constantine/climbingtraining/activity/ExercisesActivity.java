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
import java.util.Arrays;
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

    private List<Category> categories;
    private List<Equipment> equipments;
    private List<TypeExercise> typeExercises;

    private MenuItem menuItemShare;
    private MenuItem menuItemDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_layout);
        initToolbar();
        initExercisesFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // TODO: onStop or onDestroy ???
        if (ormHelper != null)
            ormHelper.close();
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
        menuItemShare = menu.findItem(R.id.menu_toolbar_share);
        menuItemDelete = menu.findItem(R.id.menu_toolbar_delete);
        menuItemShare.setVisible(false);
        menuItemDelete.setVisible(false);
        initMenuListeners();
        return true;
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

    /**
     * Открываем упражнение на редактирование.
     *
     * @param exercise - упражнение.
     */
    @Override
    public void editExercise(Exercise exercise) {
        Log.d(TAG, "editExercise() start");
        // добавляем упражнение, если будем его удалять
        entity = exercise;
        initFragments(exercise);
        Log.d(TAG, "editExercise() done");
    }

    /**
     * Сохраняем упражнение.
     *
     * @param exercise - упражнение.
     * @param drawable - изображение упражнения.
     */
    @Override
    public void saveExercise(Exercise exercise, Drawable drawable) {
        Log.d(TAG, "saveExercise() start");
        showHideOptionsMenu(false);
        initDBConnection(exercise);
        if (!saveImageToSDCard(drawable)) {
            Toast.makeText(this, getString(R.string.saving_image_sd), Toast.LENGTH_SHORT).show();
        }
        entity.setImagePath(imageNameAndPath != null ? imageNameAndPath : "");
        saveDataToDB();
        getFragmentManager().popBackStack();
        Log.d(TAG, "saveExercise() start");
    }

    @Override
    public void cancel() {
        Log.d(TAG, "cancel() start");
        showHideOptionsMenu(false);
        // необходимо только для множественного выбора и удаления
//        EntitiesForEditing.getInstance().getExercisesForEditing().remove(entity);
        entity = null;
        Toast.makeText(this, getString(R.string.cancel), Toast.LENGTH_SHORT).show();
        getFragmentManager().popBackStack();
        Log.d(TAG, "cancel() done");
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
                deleteExercises(Arrays.asList(entity));
                // раскомментировать, когда понадобится множественное удаление
//                deleteExercises(EntitiesForEditing.getInstance().getExercisesForEditing());
                cancel();
                return true;
            }
        });
        Log.d(TAG, "initMenuListeners() done");
    }

    private void initToolbar() {
        Log.d(TAG, "initToolbar() start");
        toolbar = (Toolbar) findViewById(R.id.exercises_layout_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.exercises);
        }
        Log.d(TAG, "initToolbar() done");
    }

    /**
     * Начальная инициализация фрагмента Упражнения.
     */
    private void initExercisesFragment() {
        Log.d(TAG, "initExercisesFragment() start");
        FragmentManager fragmentManager = getFragmentManager();
        exercisesFragment = ExercisesFragment.newInstance();

        fragmentManager.beginTransaction()
                .replace(R.id.exercises_container, exercisesFragment)
                .addToBackStack(ExercisesFragment.class.getSimpleName())
                .commit();
        Log.d(TAG, "initExercisesFragment() done");
    }

    /**
     * Инициализация фрагмента Упражнение для редактирования упражнения.
     *
     * @param exercise - упражнение.
     */
    private void initFragments(Exercise exercise) {
        Log.d(TAG, "loadFragments() started");

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        ExerciseFragment fragment = ExerciseFragment.newInstance();
        fragmentTransaction.replace(R.id.exercises_container, fragment, ExerciseFragment.class.getSimpleName());

//      TODO переделать !!! Можно сделать класс-кеш, который будет хранить загруженные сущности.
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

        fragmentTransaction.addToBackStack(ExerciseFragment.class.getSimpleName());
        fragmentTransaction.commit();

        Log.d(TAG, "loadFragments() done");
    }

    /**
     * Инициализация фрагмента Упражнение для сохранения нового упражнения.
     */
    private void loadFragmentExercise() {
        Log.d(TAG, "loadFragmentExercise() started");
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        ExerciseFragment fragment = ExerciseFragment.newInstance();
        fragmentTransaction.replace(R.id.exercises_container, fragment, ExerciseFragment.class.getSimpleName());

//      TODO переделать !!!
        updateSectionsExercise();

        Bundle bundle = new Bundle();
//        TODO: стоит ли тащить всю сущность ?
        bundle.putParcelable(EXERCISE_PARCELABLE, new ExerciseParcelable(categories, equipments, typeExercises));

        fragment.setArguments(bundle);
        fragmentTransaction.addToBackStack(ExerciseFragment.class.getSimpleName());
        fragmentTransaction.commit();
        Log.d(TAG, "loadFragmentExercise() done");
    }

    private void deleteExercises(List<Exercise> exercises) {
        Log.d(TAG, "deleteExercises() start");
        if (exercises.isEmpty()) {
            Toast.makeText(this, "Is empty or new exercise", Toast.LENGTH_SHORT).show();
            return;
        }
        ormHelper = new OrmHelper(this, ICommonEntities.CLIMBING_TRAINING_DB_NAME,
                ICommonEntities.CLIMBING_TRAINING_DB_VERSION);
        for (Exercise exercise : exercises) {
            try {
                if (commonDao == null) {
                    commonDao = ormHelper.getDaoByClass(Exercise.class);
                }
                commonDao.delete(exercise);
                deleteImageFromSD(exercise.getImagePath());
            } catch (SQLException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        Log.d(TAG, "deleteExercises() done");
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

    private void saveDataToDB() {
        Log.d(TAG, "saveDataToDB() start");
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
            ormHelper.close();
        }
        Log.d(TAG, "saveDataToDB() start");
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

    private void initDBConnection(Exercise exercise) {
        Log.d(TAG, "initDBConnection() start");
        ormHelper = new OrmHelper(this, ICommonEntities.CLIMBING_TRAINING_DB_NAME,
                ICommonEntities.CLIMBING_TRAINING_DB_VERSION);
        entity = exercise;
        Log.d(TAG, "initDBConnection() done");
    }

    /**
     * Обновляем все категории, типы упражнений и оборудование для выбора в новом упражнении.
     */
    private void updateSectionsExercise() {
        Log.d(TAG, "updateSectionsExercise() start");
//        if (ormHelper == null) {
        // TODO: ошибка если if
            ormHelper = new OrmHelper(this, ICommonEntities.CLIMBING_TRAINING_DB_NAME,
                    ICommonEntities.CLIMBING_TRAINING_DB_VERSION);
//        }
        try {
            CommonDao commonDao = ormHelper.getDaoByClass(Category.class);
            if (commonDao != null) {
                categories = commonDao.queryForAll();
                Log.d(TAG, "Categories size = " + categories.size());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            CommonDao commonDao = ormHelper.getDaoByClass(Equipment.class);
            if (commonDao != null) {
                equipments = commonDao.queryForAll();
                Log.d(TAG, "Equipments size = " + equipments.size());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
