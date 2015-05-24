package com.climbingtraining.constantine.climbingtraining.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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

    private TextView fragmentTrainingDateTextView;
    private Spinner fragmentTrainingSpinnerCategory;
    private Button fragmentTrainingAddExerciseBtn;
    private ITrainingFragmentCallBack callBack;
    private ListView fragmentTrainingList;
    private TextView fragmentTrainingDescriptionEditText;
    private TextView fragmentTrainingCommentEditText;

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
        if (getArguments() != null) {
            initDB();
            training = getTrainingById(getArguments().getInt(CategoriesActivity.ENTITY_ID));
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragmentTrainingDateTextView = (TextView) getActivity().findViewById(R.id.fragment_training_date_text_view);
        fragmentTrainingDateTextView.setText(getString(R.string.determine_date));
        fragmentTrainingSpinnerCategory = (Spinner) getActivity().findViewById(R.id.fragment_training_spinner_category);
        fragmentTrainingAddExerciseBtn = (Button) getActivity().findViewById(R.id.fragment_training_add_exercise_btn);
        fragmentTrainingList = (ListView) getActivity().findViewById(R.id.fragment_training_list);
        fragmentTrainingDescriptionEditText = (TextView) getActivity().findViewById(R.id.fragment_training_description_edit_text);
        fragmentTrainingCommentEditText = (TextView) getActivity().findViewById(R.id.fragment_training_comment_edit_text);

//        if (training != null) {
//            initFields();
//        }

        fragmentTrainingAddExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.createNewAccountingQuantity();
            }
        });

        updateSpinner();
        updateAccountingQuantities();

        fragmentTrainingDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dateDialog = new DatePicker();
                dateDialog.show(getFragmentManager(), "datePicker");
            }
        });
    }

    private void updateAccountingQuantities() {
        if (accountingQuantities != null) {
            accountingQuantitiesAdapter = new AccountingQuantitiesAdapter(getActivity(), accountingQuantities);
            fragmentTrainingList.setAdapter(accountingQuantitiesAdapter);
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

    private Training getTrainingById(int trainingId) {
        Training result = null;
        try {
            result = (Training) commonDao.queryForId(trainingId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void updateSpinner() {
//        TODO сделать свой адаптер c преферансом и куртизанками!
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, PhysicalTraining.getNameByLocale(Locale.getDefault()));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fragmentTrainingSpinnerCategory.setAdapter(arrayAdapter);
    }

    public void addAccountingQuantities(AccountingQuantity accountingQuantity) {
        if (accountingQuantities == null) {
            accountingQuantities = new ArrayList<>();
        }
        accountingQuantities.add(accountingQuantity);
    }

//    private void initFields() {
//        fragmentTrainingDateTextView.setText(training.getDate() != null ? sdf.format(training.getDate()) : "");
//        initSpinnerCategory();
//        accountingQuantities = (List)training.getQuantities();
//        fragmentTrainingDescriptionEditText.setText(training.getDescription());
//        fragmentTrainingCommentEditText.setText(training.getComment());
//    }

    private void initSpinnerCategory() {
        for (PhysicalTraining item : PhysicalTraining.values()) {
            if (item.equals(training.getPhysicalTraining())) {
                fragmentTrainingSpinnerCategory.setSelection(item.ordinal());
            }
        }
    }

//    GET & SET

    public List<AccountingQuantity> getAccountingQuantities() {
        return accountingQuantities;
    }

    public Date getTrainingDate() {
        Date date = null;
        try {
            date = sdf.parse(fragmentTrainingDateTextView.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public PhysicalTraining getPhysicalTraining() {
        return PhysicalTraining.getPhysicalTrainingByName((String) fragmentTrainingSpinnerCategory.getSelectedItem());
    }

    public String getDescription() {
        return fragmentTrainingDescriptionEditText.getText().toString();
    }

    public String getComment() {
        return fragmentTrainingCommentEditText.getText().toString();
    }

    public interface ITrainingFragmentCallBack {
        void createNewAccountingQuantity();

//        void editAccountingQuantity(AccountingQuantity quantity);
    }
}
