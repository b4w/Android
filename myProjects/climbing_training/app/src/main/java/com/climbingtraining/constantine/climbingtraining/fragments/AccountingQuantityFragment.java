package com.climbingtraining.constantine.climbingtraining.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.climbingtraining.constantine.climbingtraining.data.common.CommonDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.AccountingQuantity;
import com.climbingtraining.constantine.climbingtraining.data.dto.Category;
import com.climbingtraining.constantine.climbingtraining.data.dto.Exercise;
import com.climbingtraining.constantine.climbingtraining.data.dto.ICommonEntities;
import com.climbingtraining.constantine.climbingtraining.data.helpers.OrmHelper;
import com.climbingtraining.constantine.climbingtraining.enums.MeasurementMeasure;
import com.climbingtraining.constantine.climbingtraining.enums.PhysicalTraining;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Locale;

/**
 * Created by KonstantinSysoev on 10.05.15.
 */
public class AccountingQuantityFragment extends Fragment {

    private Button fragmentAccountingButtonOk;
    private Button fragmentAccountingButtonCancel;
    private Button fragmentAccountingQuantityChooseExercise;

    private ImageView fragmentAqImage;
    private TextView fragmentAqName;
    private TextView fragmentAqNameCategory;
    private TextView fragmentAqTypeExercise;
    private TextView fragmentAqEquipment;
    private TextView fragmentAqComment;
    private TextView fragmentAccountingQuantityDescription;
    private TextView fragmentAccountingQuantityComment;

    private TextView fragmentAccountingQuantityName;
    private EditText numberApproaches;
    private EditText numberTimeApproach;
    private EditText additionalWeight;
    private Spinner spinnerMeasurementMeasure;
    private EditText quantityDistance;

    private String imagePath;
    private String name;
    private String category;
    private String typeExercise;
    private String equipment;
    private String comment;
    private int exerciseId;

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

        fragmentAccountingButtonOk = (Button) getActivity().findViewById(R.id.fragment_accounting_button_ok);
        fragmentAccountingButtonCancel = (Button) getActivity().findViewById(R.id.fragment_accounting_button_cancel);
        fragmentAccountingQuantityChooseExercise = (Button) getActivity()
                .findViewById(R.id.fragment_accounting_quantity_choose_exercise);
        fragmentAccountingQuantityName = (TextView) getActivity().findViewById(R.id.fragment_accounting_quantity_name);


        fragmentAccountingQuantityChooseExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.chooseExercise();
            }
        });

        fragmentAccountingButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountingQuantity accountingQuantity = new AccountingQuantity();
                accountingQuantity.setNumberApproaches(Integer.valueOf(numberApproaches.getText().toString()));
                accountingQuantity.setNumberTimeApproach(Integer.valueOf(numberTimeApproach.getText().toString()));
                accountingQuantity.setAdditionalWeight(Float.valueOf(additionalWeight.getText().toString()));
                accountingQuantity.setMeasurementMeasure(
                        MeasurementMeasure.getMeasurementMeasureByName((String)spinnerMeasurementMeasure.getSelectedItem()));
                accountingQuantity.setDistance(Float.valueOf(quantityDistance.getText().toString()));

//                accountingQuantity.setTimeBegin();
//                accountingQuantity.setTimeEnd();

                accountingQuantity.setExercise(getExerciseFromDB());

                accountingQuantity.setDescription(fragmentAccountingQuantityDescription.getText().toString());
                accountingQuantity.setComment(fragmentAccountingQuantityComment.getText().toString());

                callBack.addNewAccountingQuantity(accountingQuantity);
            }
        });

        fragmentAccountingButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.cancelAccountingQuantity();
            }
        });

        fragmentAqImage = (ImageView) getActivity().findViewById(R.id.fragment_aq_image);
        if (imagePath != null && !imagePath.isEmpty()) {
            fragmentAqImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        }
        fragmentAqName = (TextView) getActivity().findViewById(R.id.fragment_aq_name);
        fragmentAqName.setText(name);
        fragmentAccountingQuantityName.setText(name);
        fragmentAqNameCategory = (TextView) getActivity().findViewById(R.id.fragment_aq_name_category);
        fragmentAqNameCategory.setText(category);
        fragmentAqTypeExercise = (TextView) getActivity().findViewById(R.id.fragment_aq_type_exercise);
        fragmentAqTypeExercise.setText(typeExercise);
        fragmentAqEquipment = (TextView) getActivity().findViewById(R.id.fragment_aq_equipment);
        fragmentAqEquipment.setText(equipment);
        fragmentAqComment = (TextView) getActivity().findViewById(R.id.fragment_aq_comment);
        fragmentAqComment.setText(comment);
        fragmentAccountingQuantityDescription = (TextView) getActivity().findViewById(R.id.fragment_accounting_quantity_description);
        fragmentAccountingQuantityComment = (TextView) getActivity().findViewById(R.id.fragment_accounting_quantity_comment);

        numberApproaches = (EditText) getActivity().findViewById(R.id.fragment_accounting_quantity_number_approaches);
        numberTimeApproach = (EditText) getActivity().findViewById(R.id.fragment_accounting_quantity_number_time_approach);
        additionalWeight = (EditText) getActivity().findViewById(R.id.fragment_accounting_quantity_additional_weight);
        spinnerMeasurementMeasure = (Spinner) getActivity().findViewById(R.id.fragment_accounting_quantity_spinner_measurement_measure);
        quantityDistance = (EditText) getActivity().findViewById(R.id.fragment_accounting_quantity_distance);

        updateDate();
    }

    private void updateDate() {
//        TODO сделать свой адаптер
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, MeasurementMeasure.getNameByLocale(Locale.getDefault()));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMeasurementMeasure.setAdapter(arrayAdapter);
    }

    private Exercise getExerciseFromDB () {
        Exercise exercise = null;
        OrmHelper ormHelper = new OrmHelper(getActivity(), ICommonEntities.EXERCISES_DATABASE_NAME,
                ICommonEntities.EXERCISE_DATABASE_VERSION);
        try {
            CommonDao commonDao = ormHelper.getDaoByClass(Exercise.class);
            exercise = (Exercise) commonDao.queryForId(exerciseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ormHelper.close();
        return exercise;
    }

//    GET & SET
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTypeExercise(String typeExercise) {
        this.typeExercise = typeExercise;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public interface IAccountingQuantityFragmentCallBack {
        void addNewAccountingQuantity(AccountingQuantity accountingQuantity);

        void cancelAccountingQuantity();

        void chooseExercise();
    }
}
