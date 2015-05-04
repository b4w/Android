package com.climbingtraining.constantine.climbingtraining.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.data.common.CommonDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.AbstractEntity;
import com.climbingtraining.constantine.climbingtraining.data.dto.Category;
import com.climbingtraining.constantine.climbingtraining.data.dto.Equipment;
import com.climbingtraining.constantine.climbingtraining.data.dto.Exercise;
import com.climbingtraining.constantine.climbingtraining.data.dto.ICommonEntities;
import com.climbingtraining.constantine.climbingtraining.data.dto.TypeExercise;
import com.climbingtraining.constantine.climbingtraining.data.helpers.CategoryOrmHelper;
import com.climbingtraining.constantine.climbingtraining.data.helpers.OrmHelper;
import com.climbingtraining.constantine.climbingtraining.fragments.DescriptionFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.ExercisesFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.LoadImageFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.OkCancelFragment;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KonstantinSysoev on 02.05.15.
 */
public class ExercisesActivity extends ActionBarActivity implements ExercisesFragment.IExercisesFragmentCallBack,
        OkCancelFragment.IOkCancelFragmentCallBack {

    private final static String TAG = ExercisesActivity.class.getSimpleName();

    private Toolbar toolbar;
    private ExercisesFragment exercisesFragment;

    private Boolean createNewCategory;
    private Boolean createNewEquipment;
    private Boolean createNewExercise;
    private Boolean createNewTypeExercise;

    private Map<Class<? extends AbstractEntity>, Boolean> creatingEntities;

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

        if (creatingEntities == null) {
            creatingEntities = new HashMap<>();
//            или не заполнять?
            creatingEntities.put(Category.class, false);
            creatingEntities.put(Equipment.class, false);
            creatingEntities.put(Exercise.class, false);
            creatingEntities.put(TypeExercise.class, false);
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
    public void createNewCategory() {
        Log.i(TAG, "createNewCategory() started");
        // передать необходимые strings для отображения
        creatingEntities.put(Category.class, true);
        loadFragments();
        Log.i(TAG, "createNewCategory() done");
    }

    @Override
    public void createNewEquipment() {
        Log.i(TAG, "createNewEquipment() started");
        // передать необходимые strings для отображения
        creatingEntities.put(Equipment.class, true);
        loadFragments();
        Log.i(TAG, "createNewEquipment() started");
    }

    @Override
    public void createNewExercise() {
        Log.i(TAG, "createNewExercise() started");
        // передать необходимые strings для отображения
        creatingEntities.put(Exercise.class, true);
        loadFragments();
        Log.i(TAG, "createNewExercise() done");
    }

    @Override
    public void createNewTypeExercise() {
        Log.i(TAG, "createNewTypeExercise() started");
        // передать необходимые strings для отображения
        creatingEntities.put(TypeExercise.class, true);
        loadFragments();
        Log.i(TAG, "createNewTypeExercise() done");
    }

    @Override
    public void clickOkBtn() {
        saveDataToDB();
    }

    @Override
    public void clickCancelBnt() {
        Toast.makeText(this, getString(R.string.cancel), Toast.LENGTH_SHORT).show();
        getFragmentManager().popBackStack();
    }

    private void loadFragments() {
        Log.i(TAG, "loadFragments() started");
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.detach(exercisesFragment);

        LoadImageFragment loadImageFragment = LoadImageFragment.newInstance();
        fragmentTransaction.replace(R.id.exercises_container_one, loadImageFragment, LoadImageFragment.class.getSimpleName());

        DescriptionFragment descriptionFragment = DescriptionFragment.newInstance();
        fragmentTransaction.add(R.id.exercises_container_two, descriptionFragment, DescriptionFragment.class.getSimpleName());

        OkCancelFragment okCancelFragment = OkCancelFragment.newInstance();
        fragmentTransaction.add(R.id.exercises_container_three, okCancelFragment, OkCancelFragment.class.getSimpleName());

        // добавляем в стек, что бы потом по кнопкам ok, cancel вернуться на view
        fragmentTransaction.addToBackStack(DescriptionFragment.class.getSimpleName());

        fragmentTransaction.commit();
        Log.i(TAG, "loadFragments() done");
    }

    private void saveDataToDB() {
        Log.i(TAG, "saveDataToDB() started");
        FragmentManager fragmentManager = getFragmentManager();
        DescriptionFragment descriptionFragment = (DescriptionFragment)
                fragmentManager.findFragmentByTag(DescriptionFragment.class.getSimpleName());

        String name = descriptionFragment.getName();
        String description = descriptionFragment.getDescription();
        String comment = descriptionFragment.getComment();

//        Map.Entry<AbstractEntity, Boolean> entry;
//        for (Map.Entry<AbstractEntity, Boolean> entry : creatingEntities.entrySet()) {
//
//        }

        Category category = new Category(name, 1, description, comment);

        OrmHelper ormHelper = new CategoryOrmHelper(this, ICommonEntities.CATEGORIES_DATABASE_NAME,
                ICommonEntities.CATEGORIES_DATABASE_VERSION);
        ConnectionSource connectionSource = ormHelper.getConnectionSource();

        CommonDao commonDao = null;

        try {
            commonDao = ormHelper.getDaoByClass(Category.class);
            if (commonDao != null)
                commonDao.createOrUpdate(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (commonDao != null) {

//                 TODO не получается явно привести к типу CategoryDao
//                + сделать в asyncTask ???
//                categoryDao = (CategoryDao) commonDao;

                List<Category> result = commonDao.queryBuilder().where().like(ICommonEntities.COLUMN_NAME_NAME, name + "%")
                        .and().like(ICommonEntities.COLUMN_NAME_DESCRIPTION, description + "%")
                        .and().like(ICommonEntities.COLUMN_NAME_COMMENT, comment + "%").query();
                if (result != null && !result.isEmpty()) {
                    Toast.makeText(this, getString(R.string.successfully_added), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, getString(R.string.error_added), Toast.LENGTH_SHORT).show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (connectionSource != null)
                connectionSource.close();
            if (ormHelper != null)
                ormHelper.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getFragmentManager().popBackStack();
        Log.i(TAG, "saveDataToDB() done");
    }
}
