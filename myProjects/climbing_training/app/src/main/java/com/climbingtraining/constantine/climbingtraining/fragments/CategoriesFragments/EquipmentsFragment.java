package com.climbingtraining.constantine.climbingtraining.fragments.CategoriesFragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
import com.j256.ormlite.android.loadercallback.OrmCursorLoaderCallback;
import com.j256.ormlite.stmt.PreparedQuery;
import com.melnykov.fab.FloatingActionButton;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Created by KonstantinSysoev on 08.05.15.
 */
public class EquipmentsFragment extends AbstractCategoriesListFragment {

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

    private CommonDao commonDao;
    private OrmCursorLoaderCallback<Equipment, Integer> equipmentLoaderCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callBack = (IEquipmentsFragmentCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement IEquipmentsFragmentCallBack.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipments, container, false);
        equipmentsListAdapter = new EquipmentsListAdapter(getActivity());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: костыль!
        // т.к. почему-то не работает categoryLoaderCallback (дожен обновляться автоматически),
        // на время поставил restartLoader
        if (equipmentsListAdapter.getCursor() != null) {
            getActivity().getLoaderManager().restartLoader(EQUIPMENT_ORM_LOADER_ID, null, equipmentLoaderCallback);
        }
    }

    public static EquipmentsFragment newInstance() {
        return new EquipmentsFragment();
    }

    protected void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        fragmentEquipmentsTitle = (TextView) getActivity().findViewById(R.id.equipments_list_layout_title);
        fragmentEquipmentsDescription = (TextView) getActivity().findViewById(R.id.equipments_list_layout_description);
        fragmentEquipmentsComments = (TextView) getActivity().findViewById(R.id.equipments_list_layout_comments);
        fragmentEquipmentsList = (ListView) getActivity().findViewById(R.id.fragment_equipments_list);
        fragmentEquipmentsFloatButton = (FloatingActionButton) getActivity().findViewById(R.id.fragment_equipments_float_button);
        fragmentEquipmentsFloatButton.attachToListView(fragmentEquipmentsList);
        Log.d(TAG, "initXmlFields() done");
    }

    protected void initListeners() {
        Log.d(TAG, "initListeners() start");
        fragmentEquipmentsFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.createNewEquipment();
            }
        });

        fragmentEquipmentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Equipment equipment = equipmentsListAdapter.getTypedItem(position);
                callBack.editEquipment(equipment);
            }
        });
        fragmentEquipmentsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                EquipmentsListAdapter.ViewHolder holder;
                holder = (EquipmentsListAdapter.ViewHolder) view.getTag();
                if (holder.isChecked) {
                    holder.isChecked = false;
                    // выделение цветом выбранной позиции
                    view.setBackgroundColor(getResources().getColor(R.color.text_icon));
                } else {
                    holder.isChecked = true;
                    // выделение цветом выбранной позиции
                    view.setBackgroundColor(getResources().getColor(R.color.divider_color));
                }
                Equipment category = equipmentsListAdapter.getTypedItem(position);
                updateEntitiesForEditing(holder.isChecked, category);
                callBack.showHideOptionsMenu(!getEntitiesForEditing().isEmpty());
                return true;
            }
        });
        Log.d(TAG, "initListeners() done");
    }

    protected void updateEntities() {
        Log.d(TAG, "updateEntities() start");
        List<Equipment> result = null;
        try {
            commonDao = getOrmHelper().getDaoByClass(Equipment.class);
            result = commonDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "updateEntities() done");
        equipments = result != null ? result : Collections.<Equipment>emptyList();
    }

    protected void initOrmCursorLoader() {
        Log.d(TAG, "initOrmCursorLoader() start");
        fragmentEquipmentsList.setAdapter(equipmentsListAdapter);
        try {
            PreparedQuery query = commonDao.queryBuilder().prepare();
            equipmentLoaderCallback =
                    new OrmCursorLoaderCallback<Equipment, Integer>(getActivity(), commonDao, query, equipmentsListAdapter);
            getActivity().getLoaderManager().initLoader(EQUIPMENT_ORM_LOADER_ID, null, equipmentLoaderCallback);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, "initOrmCursorLoader() done");
    }

    public interface IEquipmentsFragmentCallBack {
        void editEquipment(Equipment equipment);

        void createNewEquipment();

        void showHideOptionsMenu(boolean entitiesIsChecked);
    }
}
