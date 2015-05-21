package com.climbingtraining.constantine.climbingtraining.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
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
import com.climbingtraining.constantine.climbingtraining.fragments.OkCancelFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.TrainingFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.TrainingsFragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KonstantinSysoev on 01.05.15.
 */
public class TrainingsActivity extends ActionBarActivity implements TrainingsFragment.ITrainingsFragmentCallBack,
        OkCancelFragment.IOkCancelFragmentCallBack, TrainingFragment.ITrainingFragmentCallBack,
        AccountingQuantityFragment.IAccountingQuantityFragmentCallBack, ChoiceExerciseFragment.IChoiceExercisesFragmentCallBack {

    private static final String TAG = TrainingsActivity.class.getSimpleName();

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainings_layout);

        toolbar = (Toolbar) findViewById(R.id.trainings_layout_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.trainings);
        }

        // load trainings fragment
        FragmentManager fragmentManager = getFragmentManager();
        TrainingsFragment trainingsFragment = TrainingsFragment.newInstance();
        fragmentManager.beginTransaction()
                .replace(R.id.trainings_container_one, trainingsFragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void createNewTraining() {
        loadTrainingFragments();
    }

    @Override
    public void editTraining(Training training) {
        loadEditTrainingFragment(training);
    }

    @Override
    public void createNewAccountingQuantity() {
        loadAccountingQuantityFragment();
    }

    @Override
    public void clickOkBtn() {
        loadDataFromTrainingFragment();
    }

    @Override
    public void clickCancelBnt() {
        Toast.makeText(this, getString(R.string.cancel), Toast.LENGTH_SHORT).show();
        getFragmentManager().popBackStack();
    }

    @Override
    public void addNewAccountingQuantity(AccountingQuantity accountingQuantity) {
        addAccountQuantityToTraining(accountingQuantity);
    }

    @Override
    public void cancelAccountingQuantity() {
        Toast.makeText(this, getString(R.string.cancel), Toast.LENGTH_SHORT).show();
        getFragmentManager().popBackStack();
    }

    @Override
    public void chooseExercise() {
        loadChooseExerciseFragment();
    }

    @Override
    public void chooseExercise(Exercise exercise) {
        selectChosenExercise(exercise);
    }

    private void loadTrainingFragments() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        TrainingFragment trainingFragment = TrainingFragment.newInstance();
        fragmentTransaction.replace(R.id.trainings_container_one, trainingFragment,
                TrainingFragment.class.getSimpleName());

        OkCancelFragment okCancelFragment = OkCancelFragment.newInstance();
        fragmentTransaction.add(R.id.trainings_container_two, okCancelFragment,
                OkCancelFragment.class.getSimpleName());

        // добавляем в стек, что бы потом по кнопкам ok, cancel вернуться на view
        fragmentTransaction.addToBackStack(TrainingFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    private void loadAccountingQuantityFragment() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        AccountingQuantityFragment accountingQuantityFragment = AccountingQuantityFragment.newInstance();
        fragmentTransaction.replace(R.id.trainings_container_one, accountingQuantityFragment,
                AccountingQuantityFragment.class.getSimpleName());

        fragmentTransaction.remove(getFragmentManager().findFragmentByTag(OkCancelFragment.class.getSimpleName()));

        // добавляем в стек, что бы потом по кнопкам ok, cancel вернуться на view
        fragmentTransaction.addToBackStack(AccountingQuantityFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    private void loadChooseExerciseFragment() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        ChoiceExerciseFragment choiceExerciseFragment = ChoiceExerciseFragment.newInstance();
        fragmentTransaction.replace(R.id.trainings_container_one, choiceExerciseFragment,
                ChoiceExerciseFragment.class.getSimpleName());

        // добавляем в стек, что бы потом по кнопкам ok, cancel вернуться на view
        fragmentTransaction.addToBackStack(ChoiceExerciseFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    private void selectChosenExercise(Exercise exercise) {
        getFragmentManager().popBackStack();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        AccountingQuantityFragment fragment = (AccountingQuantityFragment)
                getFragmentManager().findFragmentByTag(AccountingQuantityFragment.class.getSimpleName());

        fragment.setImagePath(exercise.getImagePath());
        fragment.setName(exercise.getName());
        fragment.setCategory(exercise.getCategory().getName());
        fragment.setTypeExercise(exercise.getTypeExercise().getName());
        fragment.setEquipment(exercise.getEquipment().getName());
        fragment.setComment(exercise.getComment());
        fragment.setExerciseId(exercise.getId());
    }

    private void addAccountQuantityToTraining(AccountingQuantity accountingQuantity) {
        getFragmentManager().popBackStack();
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        TrainingFragment trainingFragment = (TrainingFragment)
                getFragmentManager().findFragmentByTag(TrainingFragment.class.getSimpleName());
        trainingFragment.addAccountingQuantities(accountingQuantity);
    }

    private void loadDataFromTrainingFragment() {
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        TrainingFragment trainingFragment = (TrainingFragment)
                getFragmentManager().findFragmentByTag(TrainingFragment.class.getSimpleName());

        Training training = new Training();
        training.setDate(trainingFragment.getTrainingDate());
        training.setPhysicalTraining(trainingFragment.getPhysicalTraining());
//        TODO !!!
        training.setPhysicalTrainingImagePath("Заменить на путь к изображению");
        training.setDescription(trainingFragment.getDescription());
        training.setComment(trainingFragment.getComment());
        training.setQuantities(trainingFragment.getAccountingQuantities());

        saveDataToDB(training);
        getFragmentManager().popBackStack();
    }

    private void loadEditTrainingFragment(Training training) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        TrainingFragment trainingFragment = TrainingFragment.newInstance();
        fragmentTransaction.replace(R.id.trainings_container_one, trainingFragment,
                TrainingFragment.class.getSimpleName());

        Bundle bundle = new Bundle();
        bundle.getInt(CategoryActivity.ENTITY_ID, training.getId());
        trainingFragment.setArguments(bundle);

        fragmentTransaction.addToBackStack(TrainingFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    private void saveDataToDB(Training training) {
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
        if (ormHelper != null)
            ormHelper.close();
    }
}
