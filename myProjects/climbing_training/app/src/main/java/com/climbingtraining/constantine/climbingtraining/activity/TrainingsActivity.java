package com.climbingtraining.constantine.climbingtraining.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KonstantinSysoev on 01.05.15.
 */
public class TrainingsActivity extends AppCompatActivity implements TrainingsFragment.ITrainingsFragmentCallBack,
        TrainingFragment.ITrainingFragmentCallBack, AccountingQuantityFragment.IAccountingQuantityFragmentCallBack, ChoiceExerciseFragment.IChoiceExercisesFragmentCallBack {

    private static final String TAG = TrainingsActivity.class.getSimpleName();

    public static final String EXERCISE_ID = "exerciseId";
    public static final String TRAINING_ID = "trainingId";

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainings_layout);
        initToolbar();
        loadTrainingsFragment();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.trainings_layout_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.trainings);
        }
    }

    private void loadTrainingsFragment() {
        // load trainings fragment
        FragmentManager fragmentManager = getFragmentManager();
        TrainingsFragment trainingsFragment = TrainingsFragment.newInstance();
        fragmentManager.beginTransaction()
                .replace(R.id.trainings_container, trainingsFragment)
                .commit();
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
        loadEditTrainingFragment(training);
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
    public void saveTraining(Training training) {
        saveNewTraining(training);
    }

    /**
     * Отмена.
     */
    @Override
    public void cancel() {
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

    private void loadFragmentNewAccountingQuantity() {
        Log.d(TAG, "loadFragmentNewAccountingQuantity() start");
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.trainings_container, AccountingQuantityFragment.newInstance(), AccountingQuantityFragment.class.getSimpleName())
                .addToBackStack(AccountingQuantityFragment.class.getSimpleName())
                .commit();
        Log.d(TAG, "loadFragmentNewAccountingQuantity() done");
    }

    private void loadChooseExerciseFragment() {
        Log.d(TAG, "loadChooseExerciseFragment() start");
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.trainings_container, ChoiceExerciseFragment.newInstance(), ChoiceExerciseFragment.class.getSimpleName())
                .addToBackStack(ChoiceExerciseFragment.class.getSimpleName())
                .commit();
        Log.d(TAG, "loadChooseExerciseFragment() done");
    }

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

    private void saveNewTraining(Training training) {
        Log.d(TAG, "saveNewTraining() start");
        saveDataToDB(training);
        TrainingsFragment fragment = TrainingsFragment.newInstance();

        Bundle bundle = new Bundle();
        bundle.putInt(TrainingsActivity.TRAINING_ID, training.getId());
        fragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.trainings_container, fragment, TrainingsFragment.class.getSimpleName())
                .addToBackStack(TrainingsFragment.class.getSimpleName())
                .commit();

        Log.d(TAG, "saveNewTraining() done");
    }

    private void loadEditTrainingFragment(Training training) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        TrainingFragment trainingFragment = TrainingFragment.newInstance();
        fragmentTransaction.replace(R.id.trainings_container, trainingFragment,
                TrainingFragment.class.getSimpleName());

        Bundle bundle = new Bundle();
        bundle.getInt(CategoriesActivity.ENTITY_ID, training.getId());
        trainingFragment.setArguments(bundle);

        fragmentTransaction.addToBackStack(TrainingFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    private void saveDataToDB(Training training) {
        Log.d(TAG, "saveDataToDB() start");
        OrmHelper ormHelper = new OrmHelper(this, ICommonEntities.TRAINING_DATABASE_NAME,
                ICommonEntities.TRAINING_DATABASE_VERSION);
        try {
            CommonDao commonDao = ormHelper.getDaoByClass(Training.class);
            if (commonDao != null) {

                List<AccountingQuantity> quantities = new ArrayList<>();
                for (AccountingQuantity item : training.getQuantities()) {
                    Exercise exercise = item.getExercise();
                    ormHelper.getDaoByClass(Category.class).createOrUpdate(exercise.getCategory());
                    ormHelper.getDaoByClass(Equipment.class).createOrUpdate(exercise.getEquipment());
                    ormHelper.getDaoByClass(TypeExercise.class).createOrUpdate(exercise.getTypeExercise());
                    ormHelper.getDaoByClass(Exercise.class).createOrUpdate(exercise);
                    item.setExercise(exercise);
                    ormHelper.getDaoByClass(AccountingQuantity.class).createOrUpdate(item);
                    quantities.add(item);
                }

                training.setQuantities(quantities);
                commonDao.createOrUpdate(training);
            }
        } catch (SQLException e) {
            Toast.makeText(this, getString(R.string.error_added), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

//        try {
//            // проверяем добавилась запись или нет
//            List<AbstractEntity> result = trainingDao.queryBuilder().where().like(ICommonEntities.COLUMN_NAME_NAME, training.getName() + "%").query();
//            if (result != null && !result.isEmpty()) {
//                Toast.makeText(this, exercise.getClass().getSimpleName() + " " + getString(R.string.successfully_added), Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, getString(R.string.error_added), Toast.LENGTH_SHORT).show();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        Toast.makeText(this, training.getClass().getSimpleName() + " " + getString(R.string.successfully_added), Toast.LENGTH_SHORT).show();
        // Закрываем все подключения
        ormHelper.close();
        Log.d(TAG, "saveDataToDB() start");
    }
}
