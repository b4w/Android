package com.climbingtraining.constantine.climbingtraining.activity;

import android.app.FragmentManager;
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
import com.climbingtraining.constantine.climbingtraining.data.dto.AccountingQuantity;
import com.climbingtraining.constantine.climbingtraining.data.dto.Category;
import com.climbingtraining.constantine.climbingtraining.data.dto.Equipment;
import com.climbingtraining.constantine.climbingtraining.data.dto.Exercise;
import com.climbingtraining.constantine.climbingtraining.data.dto.ICommonEntities;
import com.climbingtraining.constantine.climbingtraining.data.dto.Training;
import com.climbingtraining.constantine.climbingtraining.data.dto.TypeExercise;
import com.climbingtraining.constantine.climbingtraining.data.helpers.OrmHelper;
import com.climbingtraining.constantine.climbingtraining.fragments.AccountingQuantityFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.ChoiceExerciseFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.TrainingFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.TrainingsFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by KonstantinSysoev on 01.05.15.
 */
public class TrainingsActivity extends AppCompatActivity implements TrainingsFragment.ITrainingsFragmentCallBack,
        TrainingFragment.ITrainingFragmentCallBack, AccountingQuantityFragment.IAccountingQuantityFragmentCallBack, ChoiceExerciseFragment.IChoiceExercisesFragmentCallBack {

    private static final String TAG = TrainingsActivity.class.getSimpleName();

    public static final String EXERCISE_ID = "exerciseId";
    public static final String TRAINING_ID = "trainingId";
    public final static String DIR_SD = "/Climbing training/images/trainings/";

    private Toolbar toolbar;
    private Training entity;

    private CommonDao trainingDao;
    private CommonDao aqDao;

    private String imageNameAndPath;
    private String imageNameForSDCard;

    private MenuItem menuItemShare;
    private MenuItem menuItemDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainings_layout);
        initToolbar();
        loadTrainingsFragment();
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
                if (entity != null) {
                    deleteTrainings(Arrays.asList(entity));
                    // раскомментировать, когда понадобится множественное удаление
//                  deleteExercises(EntitiesForEditing.getInstance().getExercisesForEditing());
                    showHideOptionsMenu(false);
                    getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.impossible_delete_new_training), Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });
        Log.d(TAG, "initMenuListeners() done");
    }

    private void initToolbar() {
        Log.d(TAG, "initToolbar() start");
        toolbar = (Toolbar) findViewById(R.id.trainings_layout_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.trainings);
        }
        Log.d(TAG, "initToolbar() done");
    }

    private void loadTrainingsFragment() {
        Log.d(TAG, "loadTrainingsFragment() start");
        // load trainings fragment
        FragmentManager fragmentManager = getFragmentManager();
        TrainingsFragment trainingsFragment = TrainingsFragment.newInstance();
        fragmentManager.beginTransaction()
                .replace(R.id.trainings_container, trainingsFragment)
                .commit();
        Log.d(TAG, "loadTrainingsFragment() done");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Создание новой тренировки.
     */
    @Override
    public void createNewTraining() {
        loadFragmentNewTraining();
    }

    /**
     * Редактирование тренировки.
     *
     * @param training - тренировка.
     */
    @Override
    public void editTraining(Training training) {
        entity = training;
        loadEditTrainingFragment();
    }

    /**
     * Создание нового количества подходов для упражнения.
     */
    @Override
    public void createNewAccountingQuantity() {
        loadFragmentNewAccountingQuantity();
    }

    /**
     * Сохранить тренировку.
     *
     * @param training - тренировка.
     */
    @Override
    public void saveTraining(Training training, Drawable drawable) {
        showHideOptionsMenu(false);
        saveNewTraining(training, drawable);
    }

    /**
     * Отмена.
     */
    @Override
    public void cancel() {
        showHideOptionsMenu(false);
        Toast.makeText(this, getString(R.string.cancel), Toast.LENGTH_SHORT).show();
        getFragmentManager().popBackStack();
    }

    /**
     * Добавление нового учета количества подходов в тренировку.
     *
     * @param accountingQuantity
     */
    @Override
    public void addNewAccountingQuantity(AccountingQuantity accountingQuantity) {
        addAccountQuantityToTraining(accountingQuantity);
    }

    /**
     * Отмена создания нового учета количества подходов.
     */
    @Override
    public void cancelAccountingQuantity() {
        Toast.makeText(this, getString(R.string.cancel), Toast.LENGTH_SHORT).show();
        getFragmentManager().popBackStack();
    }

    /**
     * Загружаем список упражнений для дальнейшего добавления в учет количества подходов.
     */
    @Override
    public void chooseExercise() {
        loadChooseExerciseFragment();
    }

    /**
     * Выбираем упражнение для добавления в учет количества подходов.
     *
     * @param exerciseId - id упражнения.
     */
    @Override
    public void chooseExercise(Integer exerciseId) {
        selectChosenExercise(exerciseId);
    }

    /**
     * Фрагмент для создания новой тренировки
     */
    private void loadFragmentNewTraining() {
        Log.d(TAG, "loadFragmentNewTraining() start");
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.trainings_container, TrainingFragment.newInstance(), TrainingFragment.class.getSimpleName())
                .addToBackStack(TrainingFragment.class.getSimpleName())
                .commit();
        Log.d(TAG, "loadFragmentNewTraining() done");
    }

    /**
     * Фрагмент для создания нового количества учета подходов.
     */
    private void loadFragmentNewAccountingQuantity() {
        Log.d(TAG, "loadFragmentNewAccountingQuantity() start");
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.trainings_container, AccountingQuantityFragment.newInstance(), AccountingQuantityFragment.class.getSimpleName())
                .addToBackStack(AccountingQuantityFragment.class.getSimpleName())
                .commit();
        Log.d(TAG, "loadFragmentNewAccountingQuantity() done");
    }

    /**
     * Фрагмент - список упражнений для добавления.
     */
    private void loadChooseExerciseFragment() {
        Log.d(TAG, "loadChooseExerciseFragment() start");
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.trainings_container, ChoiceExerciseFragment.newInstance(), ChoiceExerciseFragment.class.getSimpleName())
                .addToBackStack(ChoiceExerciseFragment.class.getSimpleName())
                .commit();
        Log.d(TAG, "loadChooseExerciseFragment() done");
    }

    /**
     * Добавление выбранного упражнения к учету подходов.
     *
     * @param exerciseId - id выбранного упражнения.
     */
    private void selectChosenExercise(Integer exerciseId) {
        Log.d(TAG, "selectChosenExercise() start");
        getFragmentManager().popBackStack();

        Bundle bundle = new Bundle();
        // TODO: подумать как переделать в Integer для проверки на != null
        bundle.putString(EXERCISE_ID, String.valueOf(exerciseId));

        AccountingQuantityFragment fragment = AccountingQuantityFragment.newInstance();
        fragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.trainings_container, fragment, AccountingQuantityFragment.class.getSimpleName())
                .addToBackStack(TrainingFragment.class.getSimpleName())
                .commit();
        Log.d(TAG, "selectChosenExercise() start");
    }

    /**
     * Добавление учета подходов в тренировку.
     *
     * @param accountingQuantity - сущность учета подходов.
     */
    private void addAccountQuantityToTraining(AccountingQuantity accountingQuantity) {
        Log.d(TAG, "addAccountQuantityToTraining() start");
        getFragmentManager().popBackStack();

        // TODO: findByTag VS create new fragment?
        TrainingFragment fragment = TrainingFragment.newInstance();
        fragment.addAccountingQuantities(accountingQuantity);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.trainings_container, fragment, AccountingQuantityFragment.class.getSimpleName())
                .addToBackStack(TrainingFragment.class.getSimpleName())
                .commit();
        Log.d(TAG, "addAccountQuantityToTraining() done");
    }

    /**
     * Сохранение новой тренировки.
     *
     * @param training
     */
    private void saveNewTraining(Training training, Drawable drawable) {
        Log.d(TAG, "saveNewTraining() start");
        entity = training;
        if (!saveImageToSDCard(drawable)) {
            Toast.makeText(this, getString(R.string.saving_image_sd), Toast.LENGTH_SHORT).show();
        }
        entity.setPhysicalTrainingImagePath(imageNameAndPath != null ? imageNameAndPath : "");
        saveDataToDB();
        TrainingsFragment fragment = TrainingsFragment.newInstance();

        Bundle bundle = new Bundle();
        bundle.putInt(TrainingsActivity.TRAINING_ID, entity.getId());
        fragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.trainings_container, fragment, TrainingsFragment.class.getSimpleName())
                .addToBackStack(TrainingsFragment.class.getSimpleName())
                .commit();

        Log.d(TAG, "saveNewTraining() done");
    }

    private void loadEditTrainingFragment() {
        Log.d(TAG, "loadEditTrainingFragment() start");

        TrainingFragment fragment = TrainingFragment.newInstance();

        Bundle bundle = new Bundle();
        bundle.putInt(TrainingsActivity.TRAINING_ID, entity.getId());
        fragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.trainings_container, fragment, TrainingFragment.class.getSimpleName())
                .addToBackStack(TrainingFragment.class.getSimpleName())
                .commit();

        Log.d(TAG, "loadEditTrainingFragment() done");
    }

    private void saveDataToDB() {
        Log.d(TAG, "saveDataToDB() start");
        OrmHelper ormHelper = new OrmHelper(this, ICommonEntities.CLIMBING_TRAINING_DB_NAME,
                ICommonEntities.CLIMBING_TRAINING_DB_VERSION);
        try {
            CommonDao trainingDao = ormHelper.getDaoByClass(Training.class);
            if (trainingDao != null) {
                List<AccountingQuantity> quantities = new ArrayList<>();
                for (AccountingQuantity item : entity.getQuantities()) {
                    Exercise exercise = item.getExercise();
                    ormHelper.getDaoByClass(Category.class).createOrUpdate(exercise.getCategory());
                    ormHelper.getDaoByClass(Equipment.class).createOrUpdate(exercise.getEquipment());
                    ormHelper.getDaoByClass(TypeExercise.class).createOrUpdate(exercise.getTypeExercise());
                    ormHelper.getDaoByClass(Exercise.class).createOrUpdate(exercise);
                    item.setExercise(exercise);
//                    TODO: заменить на выбранные даты
                    item.setTimeBegin(new Date());
                    item.setTimeEnd(new Date());
                    item.setTraining(entity);
                    quantities.add(item);
                }
                // сохраняем тренировку
                trainingDao.createOrUpdate(entity);

                // сохраняем список упражнений
                CommonDao aqDao = ormHelper.getDaoByClass(AccountingQuantity.class);
                for (AccountingQuantity quantity : quantities) {
                    aqDao.createOrUpdate(quantity);
                }
            }
        } catch (SQLException e) {
            Toast.makeText(this, getString(R.string.error_added), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        Toast.makeText(this, entity.getClass().getSimpleName() + " " + getString(R.string.successfully_added), Toast.LENGTH_SHORT).show();
        // Закрываем все подключения
        ormHelper.close();
        Log.d(TAG, "saveDataToDB() done");
    }

    private void deleteTrainings(List<Training> trainings) {
        Log.d(TAG, "deleteTrainings() start");
        if (trainings.isEmpty()) {
            Toast.makeText(this, "Is empty or new training", Toast.LENGTH_SHORT).show();
            return;
        }
        OrmHelper ormHelper = new OrmHelper(this, ICommonEntities.CLIMBING_TRAINING_DB_NAME,
                ICommonEntities.CLIMBING_TRAINING_DB_VERSION);
        for (Training training : trainings) {
            try {
                if (trainingDao == null) {
                    trainingDao = ormHelper.getDaoByClass(Training.class);
                }
                if (aqDao == null) {
                    aqDao = ormHelper.getDaoByClass(AccountingQuantity.class);
                }
                for (AccountingQuantity quantity : training.getQuantities()) {
                    aqDao.delete(quantity);
                }
                trainingDao.delete(training);
                // изображение пока удалять не стоит, т.к. их всего 5
//                deleteImageFromSD(training.getImagePath());
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.training_is_deleted), Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        Log.d(TAG, "deleteExercises() done");
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
        List<AccountingQuantity> quantities = ((ArrayList) entity.getQuantities());
        imageNameForSDCard = quantities.get(0).getPhysicalTraining().name() + ".jpg";
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
        Log.d(TAG, "saveImageToSDCard() done");
        return true;
    }
}
