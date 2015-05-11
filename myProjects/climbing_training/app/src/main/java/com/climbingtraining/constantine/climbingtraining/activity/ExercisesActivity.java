package com.climbingtraining.constantine.climbingtraining.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ImageView;
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
import com.climbingtraining.constantine.climbingtraining.fragments.DescriptionFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.ExercisesFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.LoadImageFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.OkCancelFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.SectionsExerciseFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by KonstantinSysoev on 02.05.15.
 */
public class ExercisesActivity extends ActionBarActivity implements ExercisesFragment.IExercisesFragmentCallBack,
        OkCancelFragment.IOkCancelFragmentCallBack {

    private final static String TAG = ExercisesActivity.class.getSimpleName();
    public final static String DIR_SD = "/Climbing_training/images/exercises/";

    private Toolbar toolbar;
    private ExercisesFragment exercisesFragment;

    private OrmHelper ormHelper;
    private Exercise exercise;
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
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("Exercises");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // load exercises fragment
        FragmentManager fragmentManager = getFragmentManager();
        exercisesFragment = ExercisesFragment.newInstance();
        Log.i(TAG, "Create new fragment - Exercises fragment");
        fragmentManager.beginTransaction()
                .replace(R.id.exercises_container, exercisesFragment)
                .commit();
    }

    @Override
    public void createNewExercise() {
        Log.d(TAG, "createNewExercise() started");
        loadFragments();
        Log.d(TAG, "createNewExercise() done");
    }

    @Override
    public void editExercise(Exercise exercise) {
        initFragments(exercise);
    }

    @Override
    public void clickOkBtn() {
        initDBConnection();
        updateExercise();
        saveDataToDB();
        getFragmentManager().popBackStack();
    }

    @Override
    public void clickCancelBnt() {
        Toast.makeText(this, getString(R.string.cancel), Toast.LENGTH_SHORT).show();
        getFragmentManager().popBackStack();
    }

    private void initFragments(Exercise exercise) {
        Log.i(TAG, "loadFragments() started");
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.detach(exercisesFragment);
        Bundle bundle = new Bundle();

        LoadImageFragment loadImageFragment = LoadImageFragment.newInstance();
        bundle.putString(CategoryActivity.IMAGE_NAME_AND_PATH, exercise.getImagePath());
        loadImageFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.exercises_container_one, loadImageFragment, LoadImageFragment.class.getSimpleName());

        SectionsExerciseFragment sectionsExerciseFragment = SectionsExerciseFragment.newInstance();
        fragmentTransaction.add(R.id.exercises_container_two, sectionsExerciseFragment, SectionsExerciseFragment.class.getSimpleName());

//      TODO переделать !!!
        updateSectionsExercise();
        sectionsExerciseFragment.setCategories(categories);
        sectionsExerciseFragment.setEquipments(equipments);
        sectionsExerciseFragment.setTypeExercise(typeExercises);

        bundle.putInt(CategoryActivity.CATEGORIES_ID, exercise.getCategory().getId());
        bundle.putInt(CategoryActivity.EQUIPMENTS_ID, exercise.getEquipment().getId());
        bundle.putInt(CategoryActivity.TYPE_EXERCISES_ID, exercise.getTypeExercise().getId());
        sectionsExerciseFragment.setArguments(bundle);

        DescriptionFragment descriptionFragment = DescriptionFragment.newInstance();
        fragmentTransaction.add(R.id.exercises_container_three, descriptionFragment, DescriptionFragment.class.getSimpleName());

        bundle.putString(CategoryActivity.NAME, exercise.getName());
        bundle.putString(CategoryActivity.DESCRIPTION, exercise.getDescription());
        bundle.putString(CategoryActivity.COMMENT, exercise.getComment());
        descriptionFragment.setArguments(bundle);

        OkCancelFragment okCancelFragment = OkCancelFragment.newInstance();
        fragmentTransaction.add(R.id.exercises_container_four, okCancelFragment, OkCancelFragment.class.getSimpleName());

        // добавляем в стек, что бы потом по кнопкам ok, cancel вернуться на view
        fragmentTransaction.addToBackStack(DescriptionFragment.class.getSimpleName());

        fragmentTransaction.commit();
        Log.i(TAG, "loadFragments() done");
    }

    private void loadFragments() {
        Log.i(TAG, "loadFragments() started");
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.detach(exercisesFragment);

        LoadImageFragment loadImageFragment = LoadImageFragment.newInstance();
        fragmentTransaction.replace(R.id.exercises_container_one, loadImageFragment, LoadImageFragment.class.getSimpleName());

        SectionsExerciseFragment sectionsExerciseFragment = SectionsExerciseFragment.newInstance();
        fragmentTransaction.add(R.id.exercises_container_two, sectionsExerciseFragment, SectionsExerciseFragment.class.getSimpleName());

//      TODO переделать !!!
        updateSectionsExercise();
        sectionsExerciseFragment.setCategories(categories);
        sectionsExerciseFragment.setEquipments(equipments);
        sectionsExerciseFragment.setTypeExercise(typeExercises);

        DescriptionFragment descriptionFragment = DescriptionFragment.newInstance();
        fragmentTransaction.add(R.id.exercises_container_three, descriptionFragment, DescriptionFragment.class.getSimpleName());

        OkCancelFragment okCancelFragment = OkCancelFragment.newInstance();
        fragmentTransaction.add(R.id.exercises_container_four, okCancelFragment, OkCancelFragment.class.getSimpleName());

        // добавляем в стек, что бы потом по кнопкам ok, cancel вернуться на view
        fragmentTransaction.addToBackStack(DescriptionFragment.class.getSimpleName());

        fragmentTransaction.commit();
        Log.i(TAG, "loadFragments() done");
    }

    private void updateExercise() {
        Log.i(TAG, "updateExercise() started");
        FragmentManager fragmentManager = getFragmentManager();
        DescriptionFragment descriptionFragment = (DescriptionFragment)
                fragmentManager.findFragmentByTag(DescriptionFragment.class.getSimpleName());

        LoadImageFragment loadImageFragment = (LoadImageFragment)
                fragmentManager.findFragmentByTag(LoadImageFragment.class.getSimpleName());

        SectionsExerciseFragment sectionsExerciseFragment = (SectionsExerciseFragment)
                fragmentManager.findFragmentByTag(SectionsExerciseFragment.class.getSimpleName());

//        TODO Сделать сохранение изображения после успешного добавления данных в БД
        if (!saveImageToSDCard(loadImageFragment.getFragmentLoadImageImage(), descriptionFragment.getName())) {
            Toast.makeText(this, getString(R.string.saving_image_sd), Toast.LENGTH_SHORT).show();
        }

        exercise = new Exercise();
        exercise.setId(entityId);
        exercise.setName(descriptionFragment.getName());
        exercise.setImagePath(imageNameAndPath != null ? imageNameAndPath : "");
        exercise.setDescription(descriptionFragment.getDescription());
        exercise.setComment(descriptionFragment.getComment());
        exercise.setCategory(sectionsExerciseFragment.getChoseCategory());
        exercise.setEquipment(sectionsExerciseFragment.getChoseEquipment());
        exercise.setTypeExercise(sectionsExerciseFragment.getChoseTypeExercise());

        Log.i(TAG, "updateExercise() done");
    }

    private void saveDataToDB() {
        if (exercise != null && ormHelper != null) {
            try {
                commonDao = ormHelper.getDaoByClass(Exercise.class);
                if (commonDao != null) {

                    // записываем или обновляем данные в БД
                    ormHelper.getDaoByClass(Category.class).createOrUpdate(exercise.getCategory());
                    ormHelper.getDaoByClass(Equipment.class).createOrUpdate(exercise.getEquipment());
                    ormHelper.getDaoByClass(TypeExercise.class).createOrUpdate(exercise.getTypeExercise());

                    commonDao.createOrUpdate(exercise);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                // проверяем добавилась запись или нет
                List<AbstractEntity> result = commonDao.queryBuilder().where().like(ICommonEntities.COLUMN_NAME_NAME, exercise.getName() + "%").query();
                if (result != null && !result.isEmpty()) {
                    Toast.makeText(this, exercise.getClass().getSimpleName() + " " + getString(R.string.successfully_added), Toast.LENGTH_SHORT).show();
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

    private void initDBConnection() {
        ormHelper = new OrmHelper(this, ICommonEntities.EXERCISES_DATABASE_NAME,
                ICommonEntities.EXERCISE_DATABASE_VERSION);
    }

    private void updateSectionsExercise() {

        ormHelper = new OrmHelper(this, ICommonEntities.CATEGORIES_DATABASE_NAME,
                ICommonEntities.CATEGORIES_DATABASE_VERSION);
        try {
            CommonDao commonDao = ormHelper.getDaoByClass(Category.class);
            if (commonDao != null) {
                categories = commonDao.queryForAll();
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ormHelper.close();
    }
}
