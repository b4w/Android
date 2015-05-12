package com.climbingtraining.constantine.climbingtraining.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.adapters.EquipmentsListAdapter;
import com.climbingtraining.constantine.climbingtraining.data.common.CommonDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.Equipment;
import com.climbingtraining.constantine.climbingtraining.data.dto.ICommonEntities;
import com.climbingtraining.constantine.climbingtraining.data.helpers.OrmHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.melnykov.fab.FloatingActionButton;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Created by KonstantinSysoev on 08.05.15.
 */
public class EquipmentsFragment extends Fragment {

    private final static String TAG = EquipmentsFragment.class.getSimpleName();

    private ImageView imageView;
    private TextView fragmentEquipmentsTitle;
    private TextView fragmentEquipmentsDescription;
    private TextView fragmentEquipmentsComments;

    private List<Equipment> equipments;
    private EquipmentsListAdapter equipmentsListAdapter;
    private ListView fragmentEquipmentsList;
    private IEquipmentsFragmentCallBack callBack;
    private FloatingActionButton fragmentEquipmentsFloatButton;

    private OrmHelper ormHelper;
    private ConnectionSource connectionSource;
    private CommonDao commonDao;

    public static EquipmentsFragment newInstance() {
        return new EquipmentsFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callBack = (IEquipmentsFragmentCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement IEquipmentsFragmentCallBack.");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDB();
        equipments = getAllCategories();
        equipmentsListAdapter = new EquipmentsListAdapter(getActivity(), equipments);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipments, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragmentEquipmentsTitle = (TextView) getActivity().findViewById(R.id.equipments_list_layout_title);
        fragmentEquipmentsDescription = (TextView) getActivity().findViewById(R.id.equipments_list_layout_description);
        fragmentEquipmentsComments = (TextView) getActivity().findViewById(R.id.equipments_list_layout_comments);
        fragmentEquipmentsList = (ListView) getActivity().findViewById(R.id.fragment_equipments_list);
        fragmentEquipmentsFloatButton = (FloatingActionButton)getActivity().findViewById(R.id.fragment_equipments_float_button);
        fragmentEquipmentsFloatButton.attachToListView(fragmentEquipmentsList);

        fragmentEquipmentsFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.createNewEquipment();
            }
        });

        fragmentEquipmentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Equipment equipment = (Equipment) parent.getAdapter().getItem(position);
                callBack.editEquipment(equipment);
            }
        });

        fragmentEquipmentsList.setAdapter(equipmentsListAdapter);
    }

    private void initDB() {
        ormHelper = new OrmHelper(getActivity(), ICommonEntities.EQUIPMENTS_DATABASE_NAME,
                ICommonEntities.EQUIPMENTS_DATABASE_VERSION);
//        ormHelper.clearDatabase();
        connectionSource = ormHelper.getConnectionSource();
        try {
            commonDao = ormHelper.getDaoByClass(Equipment.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Equipment> getAllCategories() {
        List<Equipment> result = null;
        try {
            result = commonDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result != null ? result : Collections.<Equipment>emptyList();
    }

    public interface IEquipmentsFragmentCallBack {
        void editEquipment(Equipment equipment);
        void createNewEquipment();
    }
}
