package com.climbingtraining.constantine.climbingtraining.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.activity.TrainingsActivity;
import com.climbingtraining.constantine.climbingtraining.adapters.AccountingQuantitiesAdapter;
import com.climbingtraining.constantine.climbingtraining.data.common.CommonDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.AccountingQuantity;
import com.climbingtraining.constantine.climbingtraining.data.dto.Exercise;
import com.climbingtraining.constantine.climbingtraining.data.dto.ICommonEntities;
import com.climbingtraining.constantine.climbingtraining.data.dto.Training;
import com.climbingtraining.constantine.climbingtraining.data.helpers.OrmHelper;
import com.climbingtraining.constantine.climbingtraining.enums.PhysicalTraining;
import com.climbingtraining.constantine.climbingtraining.utils.DatePicker;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by KonstantinSysoev on 10.05.15.
 */
public class TrainingFragment extends Fragment {

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
    private final static String TAG = TrainingFragment.class.getSimpleName();

    private TextView date;
    private Button addExerciseBtn;
    private ITrainingFragmentCallBack callBack;
    private ListView trainingList;
    private TextView description;
    private TextView comment;
    private Button saveBtn;
    private Button cancelBtn;

    private List<AccountingQuantity> accountingQuantities;
    private AccountingQuantitiesAdapter accountingQuantitiesAdapter;
    private Training training;

    private CommonDao trainingDao;
    private CommonDao aqDao;
    private OrmHelper ormHelper;

    public static TrainingFragment newInstance() {
        return new TrainingFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callBack = (ITrainingFragmentCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement ITrainingFragmentCallBack.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_training, container, false);
        // Если открыли на редактирование, то загружаем все данные по упражнениям
        if (getArguments() != null) {
            initDB();
            training = getTrainingById(getArguments().getInt(TrainingsActivity.TRAINING_ID));
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initXmlFields();
        initListeners();
        updateAccountingQuantities();
        callBack.showHideOptionsMenu(true);
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        date = (TextView) getActivity().findViewById(R.id.fragment_training_date_text_view);
        date.setText(getString(R.string.determine_date));
        addExerciseBtn = (Button) getActivity().findViewById(R.id.fragment_training_add_exercise_btn);
        trainingList = (ListView) getActivity().findViewById(R.id.fragment_training_list);
        description = (TextView) getActivity().findViewById(R.id.fragment_training_description_edit_text);
        comment = (TextView) getActivity().findViewById(R.id.fragment_training_comment_edit_text);
        saveBtn = (Button) getActivity().findViewById(R.id.fragment_training_save_btn);
        cancelBtn = (Button) getActivity().findViewById(R.id.fragment_training_cancel_btn);
        if (training != null) {
            initXmlFieldsFromDB();
        }
        Log.d(TAG, "initXmlFields() done");
    }

    private void initXmlFieldsFromDB() {
        Log.d(TAG, "initXmlFieldsFromDB() start");
        date.setText(sdf.format(training.getDate()));
//        accountingQuantities = new ArrayList<>(training.getQuantities());
        description.setText(training.getDescription());
        comment.setText(training.getComment());
        Log.d(TAG, "initXmlFieldsFromDB() done");
    }

    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        // добавление учета количества для упражнения
        addExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.createNewAccountingQuantity();
            }
        });
        // выбор даты
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dateDialog = new DatePicker();
                dateDialog.show(getFragmentManager(), "datePicker");
            }
        });
        // сохраняем тренировку
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.saveTraining(getCreatedTraining(), getImageForTraining());
            }
        });
        // отмена сохранения тренировки
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.cancel();
            }
        });
        Log.d(TAG, "initListeners() done");
    }

    private void updateAccountingQuantities() {
        if (accountingQuantities != null) {
            accountingQuantitiesAdapter = new AccountingQuantitiesAdapter(getActivity(), accountingQuantities);
            trainingList.setAdapter(accountingQuantitiesAdapter);
        }
    }

    private void initDB() {
        Log.d(TAG, "initDB() start");
        ormHelper = new OrmHelper(getActivity(), ICommonEntities.CLIMBING_TRAINING_DB_NAME,
                ICommonEntities.CLIMBING_TRAINING_DB_VERSION);
        try {
            trainingDao = ormHelper.getDaoByClass(Training.class);
            aqDao = ormHelper.getDaoByClass(AccountingQuantity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "initDB() done");
    }

    private Training getCreatedTraining() {
        Log.d(TAG, "getCreatedTraining() start");
        if (training == null) {
            training = new Training();
        }
        Date dateTraining;
//        try {
//            dateTraining = sdf.parse(date.getText().toString());
//        } catch (ParseException e) {
//            throw new NumberFormatException();
//        }
        training.setDate(new Date());
        // TODO: Разобраться с изображением
//        training.setPhysicalTrainingImagePath("");
        training.setDescription(description.getText().toString());
        training.setComment(comment.getText().toString());
        training.setQuantities(accountingQuantities);
        Log.d(TAG, "getCreatedTraining() done");
        return training;
    }

    private Drawable getImageForTraining() {
        Log.d(TAG, "getImageForTraining() start");
        Drawable result = null;
        if (!accountingQuantities.isEmpty()) {
            PhysicalTraining physicalTraining = accountingQuantities.get(0).getPhysicalTraining();
            if (PhysicalTraining.OFP.equals(physicalTraining)) {
                result = getResources().getDrawable(R.drawable.ofp);
            } else if (PhysicalTraining.SFP.equals(physicalTraining)) {
                result = getResources().getDrawable(R.drawable.sfp);
            } else if (PhysicalTraining.OFP_SFP.equals(physicalTraining)) {
                result = getResources().getDrawable(R.drawable.sfp_ofp);
            } else if (PhysicalTraining.CARDIO.equals(physicalTraining)) {
                // TODO: т.к. пока нет cardio
                result = getResources().getDrawable(R.drawable.ofp);
            }
        } else {
            Log.e(TAG, "AccountingQuantities.isEmpty()");
            result = null;
        }
        Log.d(TAG, "getImageForTraining() done");
        return result;
    }

    private Training getTrainingById(int trainingId) {
        Log.d(TAG, "getTrainingById() start");
        Training result = null;
        if (ormHelper == null) {
            initDB();
        }
        try {
            result = (Training) trainingDao.queryForId(trainingId);
            accountingQuantities = aqDao.queryForAll();
            for (AccountingQuantity quantity : accountingQuantities) {
                ormHelper.getDaoByClass(Training.class).refresh(quantity.getTraining());
                ormHelper.getDaoByClass(Exercise.class).refresh(quantity.getExercise());
            }
            result.setQuantities(accountingQuantities);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "getTrainingById() done");
        return result;
    }

    public void addAccountingQuantities(AccountingQuantity accountingQuantity) {
        if (accountingQuantities == null) {
            accountingQuantities = new ArrayList<>();
        }
        accountingQuantities.add(accountingQuantity);
    }

    public interface ITrainingFragmentCallBack {
        void createNewAccountingQuantity();

        void saveTraining(Training training, Drawable drawable);

        void cancel();

        void showHideOptionsMenu(boolean entitiesIsChecked);
    }
}
