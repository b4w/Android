package com.climbingtraining.constantine.climbingtraining.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.activity.TrainingsActivity;
import com.climbingtraining.constantine.climbingtraining.data.common.CommonDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.AccountingQuantity;
import com.climbingtraining.constantine.climbingtraining.data.dto.Exercise;
import com.climbingtraining.constantine.climbingtraining.data.dto.ICommonEntities;
import com.climbingtraining.constantine.climbingtraining.data.helpers.OrmHelper;
import com.climbingtraining.constantine.climbingtraining.enums.MeasurementMeasure;
import com.climbingtraining.constantine.climbingtraining.enums.PhysicalTraining;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.sql.SQLException;
import java.util.Locale;

/**
 * Created by KonstantinSysoev on 10.05.15.
 */
public class AccountingQuantityFragment extends Fragment {

    private final String TAG = AccountingQuantityFragment.class.getSimpleName();

    private Button saveBtn;
    private Button cancelBtn;
    private Button chooseExerciseBtn;

    private ImageView exerciseImageIv;
    private TextView exerciseNameTv;
    private TextView exerciseCategoryTv;
    private TextView exerciseTypeExerciseTv;
    private TextView exerciseEquipmentTv;
    private TextView exerciseCommentTv;

    private TextView descriptionTv;
    private TextView commentTv;
    private TextView nameTv;
    private EditText numberApproachesEt;
    private EditText numberTimeApproachEt;
    private EditText additionalWeightEt;
    private Spinner measurementMeasureSp;
    private Spinner physicalTrainingSp;
    private EditText quantityDistanceEt;

    private int exerciseId;
    private Exercise exercise;

    private IAccountingQuantityFragmentCallBack callBack;

    public static AccountingQuantityFragment newInstance() {
        return new AccountingQuantityFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callBack = (IAccountingQuantityFragmentCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement IAccountingQuantityFragmentCallBack.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounting_quantity, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initXmlFields();
        initListeners();
        initMeasurementMeasureSpinner();
        initCategoryExerciseSpinner();
        updateExerciseFields();
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        saveBtn = (Button) getActivity().findViewById(R.id.fragment_accounting_button_ok);
        cancelBtn = (Button) getActivity().findViewById(R.id.fragment_accounting_button_cancel);
        chooseExerciseBtn = (Button) getActivity().findViewById(R.id.fragment_accounting_quantity_choose_exercise);
        nameTv = (TextView) getActivity().findViewById(R.id.fragment_accounting_quantity_name);
        descriptionTv = (TextView) getActivity().findViewById(R.id.fragment_accounting_quantity_description);
        commentTv = (TextView) getActivity().findViewById(R.id.fragment_accounting_quantity_comment);
        physicalTrainingSp = (Spinner) getActivity().findViewById(R.id.fragment_accounting_spinner_category_exercise);

        exerciseImageIv = (ImageView) getActivity().findViewById(R.id.fragment_aq_image);
        exerciseNameTv = (TextView) getActivity().findViewById(R.id.fragment_aq_name);
        exerciseCategoryTv = (TextView) getActivity().findViewById(R.id.fragment_aq_name_category);
        exerciseTypeExerciseTv = (TextView) getActivity().findViewById(R.id.fragment_aq_type_exercise);
        exerciseEquipmentTv = (TextView) getActivity().findViewById(R.id.fragment_aq_equipment);
        exerciseCommentTv = (TextView) getActivity().findViewById(R.id.fragment_aq_comment);

        numberApproachesEt = (EditText) getActivity().findViewById(R.id.fragment_accounting_quantity_number_approaches);
        numberTimeApproachEt = (EditText) getActivity().findViewById(R.id.fragment_accounting_quantity_number_time_approach);
        additionalWeightEt = (EditText) getActivity().findViewById(R.id.fragment_accounting_quantity_additional_weight);
        measurementMeasureSp = (Spinner) getActivity().findViewById(R.id.fragment_accounting_quantity_spinner_measurement_measure);
        quantityDistanceEt = (EditText) getActivity().findViewById(R.id.fragment_accounting_quantity_distance);
        Log.d(TAG, "initXmlFields() done");
    }

    private void updateExerciseFields() {
        Log.d(TAG, "updateExerciseFields() start");
        // TODO: сделать в AsyncTask
        if (getArguments() == null
                || getArguments().getString(TrainingsActivity.EXERCISE_ID) == null
                || getArguments().getString(TrainingsActivity.EXERCISE_ID).isEmpty()) {
            return;
        }
        exerciseId = Integer.valueOf(getArguments().getString(TrainingsActivity.EXERCISE_ID));
        exercise = getExerciseFromDB();
        if (exercise.getImagePath() != null && !exercise.getImagePath().isEmpty()) {
            File file = new File(exercise.getImagePath());
            Picasso.with(getActivity()).load(file).into(exerciseImageIv);
        }
        exerciseNameTv.setText(exercise.getName());
        exerciseCategoryTv.setText(exercise.getCategory().getName());
        exerciseTypeExerciseTv.setText(exercise.getTypeExercise().getName());
        exerciseEquipmentTv.setText(exercise.getEquipment().getName());
        exerciseCommentTv.setText(exercise.getComment());
        nameTv.setText(exercise.getName());
        Log.d(TAG, "updateExerciseFields() done");
    }

    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        chooseExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.chooseExercise();
            }
        });

        // сохранение учета количества
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountingQuantity accountingQuantity = new AccountingQuantity();
                accountingQuantity.setNumberApproaches(Integer.valueOf(numberApproachesEt.getText().toString()));
                accountingQuantity.setNumberTimeApproach(Integer.valueOf(numberTimeApproachEt.getText().toString()));
                accountingQuantity.setAdditionalWeight(Float.valueOf(additionalWeightEt.getText().toString()));
                accountingQuantity.setMeasurementMeasure(
                        MeasurementMeasure.getMeasurementMeasureByName((String) measurementMeasureSp.getSelectedItem()));
                accountingQuantity.setPhysicalTraining(PhysicalTraining.getPhysicalTrainingByName((String) physicalTrainingSp.getSelectedItem()));
                accountingQuantity.setDistance(Float.valueOf(quantityDistanceEt.getText().toString()));
//                accountingQuantity.setTimeBegin();
//                accountingQuantity.setTimeEnd();
                accountingQuantity.setExercise(exercise);
                accountingQuantity.setDescription(descriptionTv.getText().toString());
                accountingQuantity.setComment(commentTv.getText().toString());
                callBack.addNewAccountingQuantity(accountingQuantity);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.cancelAccountingQuantity();
            }
        });
        Log.d(TAG, "initListeners() done");
    }

    private void initMeasurementMeasureSpinner() {
        Log.d(TAG, "initMeasurementMeasureSpinner() start");
        // TODO: сделать свой адаптер
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, MeasurementMeasure.getNameByLocale(Locale.getDefault()));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        measurementMeasureSp.setAdapter(arrayAdapter);
        Log.d(TAG, "initMeasurementMeasureSpinner() done");
    }

    private void initCategoryExerciseSpinner() {
        Log.d(TAG, "initCategoryExerciseSpinner() start");
        // TODO: сделать свой адаптер c преферансом и куртизанками!
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, PhysicalTraining.getNameByLocale(Locale.getDefault()));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        physicalTrainingSp.setAdapter(arrayAdapter);
        Log.d(TAG, "initCategoryExerciseSpinner() done");
    }

    private Exercise getExerciseFromDB() {
        Log.d(TAG, "getExerciseFromDB() start");
        Exercise exercise = null;
        OrmHelper ormHelper = new OrmHelper(getActivity(), ICommonEntities.CLIMBING_TRAINING_DB_NAME,
                ICommonEntities.CLIMBING_TRAINING_DB_VERSION);
        try {
            CommonDao commonDao = ormHelper.getDaoByClass(Exercise.class);
            exercise = (Exercise) commonDao.queryForId(exerciseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ormHelper.close();
        Log.d(TAG, "getExerciseFromDB() done");
        return exercise;
    }

    public interface IAccountingQuantityFragmentCallBack {
        void addNewAccountingQuantity(AccountingQuantity accountingQuantity);

        void cancelAccountingQuantity();

        void chooseExercise();
    }
}
