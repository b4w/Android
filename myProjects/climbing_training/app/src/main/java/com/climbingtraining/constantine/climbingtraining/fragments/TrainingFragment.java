package com.climbingtraining.constantine.climbingtraining.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.activity.CategoriesActivity;
import com.climbingtraining.constantine.climbingtraining.adapters.AccountingQuantitiesAdapter;
import com.climbingtraining.constantine.climbingtraining.data.common.CommonDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.AccountingQuantity;
import com.climbingtraining.constantine.climbingtraining.data.dto.ICommonEntities;
import com.climbingtraining.constantine.climbingtraining.data.dto.Training;
import com.climbingtraining.constantine.climbingtraining.data.helpers.OrmHelper;
import com.climbingtraining.constantine.climbingtraining.enums.PhysicalTraining;
import com.climbingtraining.constantine.climbingtraining.utils.DatePicker;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    private OrmHelper ormHelper;
    private CommonDao commonDao;

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
            training = getTrainingById(getArguments().getInt(CategoriesActivity.ENTITY_ID));
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initXmlFields();
        loadListeners();
        updateAccountingQuantities();
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
        Log.d(TAG, "initXmlFields() done");
    }

    private void loadListeners() {
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
                callBack.saveTraining(getCreatedTraining());
            }
        });
        // отмена сохранения тренировки
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.cancel();
            }
        });
    }

    private void updateAccountingQuantities() {
        if (accountingQuantities != null) {
            accountingQuantitiesAdapter = new AccountingQuantitiesAdapter(getActivity(), accountingQuantities);
            trainingList.setAdapter(accountingQuantitiesAdapter);
        }
    }

    private void initDB() {
        ormHelper = new OrmHelper(getActivity(), ICommonEntities.TRAINING_DATABASE_NAME,
                ICommonEntities.TRAINING_DATABASE_VERSION);
        try {
            commonDao = ormHelper.getDaoByClass(Training.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Training getCreatedTraining() {
        Training training = new Training();
        Date dateTraining;
//        try {
//            dateTraining = sdf.parse(date.getText().toString());
//        } catch (ParseException e) {
//            throw new NumberFormatException();
//        }
        training.setDate(new Date());
        // TODO: Разобраться с изображением
        training.setPhysicalTrainingImagePath("");
        training.setDescription(description.getText().toString());
        training.setComment(comment.getText().toString());
        training.setQuantities(accountingQuantities);
        return training;
    }

    private Training getTrainingById(int trainingId) {
        Training result = null;
        try {
            result = (Training) commonDao.queryForId(trainingId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addAccountingQuantities(AccountingQuantity accountingQuantity) {
        if (accountingQuantities == null) {
            accountingQuantities = new ArrayList<>();
        }
        accountingQuantities.add(accountingQuantity);
    }

//    GET & SET

    public List<AccountingQuantity> getAccountingQuantities() {
        return accountingQuantities;
    }

    public Date getTrainingDate() {
        Date date = null;
        try {
            date = sdf.parse(this.date.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getDescription() {
        return description.getText().toString();
    }

    public String getComment() {
        return comment.getText().toString();
    }

    public interface ITrainingFragmentCallBack {
        void createNewAccountingQuantity();
        void saveTraining(Training training);
        void cancel();
    }
}
