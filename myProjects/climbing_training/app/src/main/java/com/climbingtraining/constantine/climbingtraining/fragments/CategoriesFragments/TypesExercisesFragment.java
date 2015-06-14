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
import com.climbingtraining.constantine.climbingtraining.adapters.TypesExercisesListAdapter;
import com.climbingtraining.constantine.climbingtraining.data.common.CommonDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.TypeExercise;
import com.j256.ormlite.android.loadercallback.OrmCursorLoaderCallback;
import com.j256.ormlite.stmt.PreparedQuery;
import com.melnykov.fab.FloatingActionButton;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Created by KonstantinSysoev on 08.05.15.
 */
public class TypesExercisesFragment extends AbstractCategoriesListFragment {

    private final static String TAG = CategoriesFragment.class.getSimpleName();

    private ImageView imageView;
    private TextView typesExercisesListLayoutTitle;
    private TextView typesExercisesListLayoutDescription;
    private TextView typesExercisesListLayoutComments;
    private ListView fragmentTypesExercisesList;

    private List<TypeExercise> typeExercises;
    private TypesExercisesListAdapter typesExercisesListAdapter;
    private ITypesExercisesFragmentCallBack callBack;
    private FloatingActionButton fragmentTypesExercisesFloatButton;
    private OrmCursorLoaderCallback<TypeExercise, Integer> typeExercisesLoaderCallback;

    private CommonDao commonDao;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callBack = (ITypesExercisesFragmentCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement ITypesExercisesFragmentCallBack.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_types_exercises, container, false);
        typesExercisesListAdapter = new TypesExercisesListAdapter(getActivity());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: костыль!
        // т.к. почему-то не работает categoryLoaderCallback (дожен обновляться автоматически),
        // на время поставил restartLoader
        if (typesExercisesListAdapter.getCursor() != null) {
            getActivity().getLoaderManager().restartLoader(TYPE_EXERCISE_ORM_LOADER_ID, null, typeExercisesLoaderCallback);
        }
    }

    public static TypesExercisesFragment newInstance() {
        return new TypesExercisesFragment();
    }

    @Override
    protected void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        typesExercisesListLayoutTitle = (TextView) getActivity().findViewById(R.id.types_exercises_list_layout_title);
        typesExercisesListLayoutDescription = (TextView) getActivity().findViewById(R.id.types_exercises_list_layout_description);
        typesExercisesListLayoutComments = (TextView) getActivity().findViewById(R.id.types_exercises_list_layout_comments);
        fragmentTypesExercisesList = (ListView) getActivity().findViewById(R.id.fragment_types_exercises_list);
        fragmentTypesExercisesFloatButton = (FloatingActionButton) getActivity().findViewById(R.id.fragment_types_exercises_float_button);
        fragmentTypesExercisesFloatButton.attachToListView(fragmentTypesExercisesList);
        Log.d(TAG, "initXmlFields() done");
    }

    @Override
    protected void initListeners() {
        Log.d(TAG, "initListeners() start");
        fragmentTypesExercisesFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.createNewTypeExercise();
            }
        });

        fragmentTypesExercisesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TypeExercise typeExercise = typesExercisesListAdapter.getTypedItem(position);
                callBack.editTypeExercise(typeExercise);
            }
        });

        fragmentTypesExercisesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TypesExercisesListAdapter.ViewHolder holder;
                holder = (TypesExercisesListAdapter.ViewHolder) view.getTag();
                if (holder.isChecked) {
                    holder.isChecked = false;
                    // выделение цветом выбранной позиции
                    view.setBackgroundColor(getResources().getColor(R.color.text_icon));
                } else {
                    holder.isChecked = true;
                    // выделение цветом выбранной позиции
                    view.setBackgroundColor(getResources().getColor(R.color.divider_color));
                }
                TypeExercise typeExercise = typesExercisesListAdapter.getTypedItem(position);
                updateEntitiesForEditing(holder.isChecked, typeExercise);
                callBack.showHideOptionsMenu(!getEntitiesForEditing().isEmpty());
                return true;
            }
        });

        Log.d(TAG, "initListeners() done");
    }

    @Override
    protected void updateEntities () {
        Log.d(TAG, "updateEntities() start");
        List<TypeExercise> result = null;
        try {
            commonDao = getOrmHelper().getDaoByClass(TypeExercise.class);
            result = commonDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "updateEntities() done");
        typeExercises = result != null ? result : Collections.<TypeExercise>emptyList();
    }

    @Override
    protected void initOrmCursorLoader() {
        Log.d(TAG, "initOrmCursorLoader() start");
        fragmentTypesExercisesList.setAdapter(typesExercisesListAdapter);
        try {
            PreparedQuery query = commonDao.queryBuilder().prepare();
            typeExercisesLoaderCallback =
                    new OrmCursorLoaderCallback<TypeExercise, Integer>(getActivity(), commonDao, query, typesExercisesListAdapter);
            getActivity().getLoaderManager().initLoader(TYPE_EXERCISE_ORM_LOADER_ID, null, typeExercisesLoaderCallback);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, "initOrmCursorLoader() done");
    }

    public interface ITypesExercisesFragmentCallBack {
        void editTypeExercise(TypeExercise typeExercise);

        void createNewTypeExercise();

        void showHideOptionsMenu(boolean entitiesIsChecked);
    }
}
