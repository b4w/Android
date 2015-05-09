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
import com.climbingtraining.constantine.climbingtraining.adapters.TypesExercisesListAdapter;
import com.climbingtraining.constantine.climbingtraining.data.common.CommonDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.ICommonEntities;
import com.climbingtraining.constantine.climbingtraining.data.dto.TypeExercise;
import com.climbingtraining.constantine.climbingtraining.data.helpers.OrmHelper;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Created by KonstantinSysoev on 08.05.15.
 */
public class TypesExercisesFragment extends Fragment {

    private final static String TAG = CategoryFragment.class.getSimpleName();

    private ImageView imageView;
    private TextView typesExercisesListLayoutTitle;
    private TextView typesExercisesListLayoutDescription;
    private TextView typesExercisesListLayoutComments;

    private List<TypeExercise> typeExercises;
    private TypesExercisesListAdapter typesExercisesListAdapter;
    private ListView fragmentTypesExercisesList;
    private ITypesExercisesFragmentCallBack callBack;

    private OrmHelper ormHelper;
    private ConnectionSource connectionSource;
    private CommonDao commonDao;

    public static TypesExercisesFragment newInstance() {
        return new TypesExercisesFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callBack = (ITypesExercisesFragmentCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement ITypesExercisesFragmentCallBack.");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDB();
        typeExercises = getAllCategories();
        typesExercisesListAdapter = new TypesExercisesListAdapter(getActivity(), typeExercises);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_types_exercises, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        typesExercisesListLayoutTitle = (TextView) getActivity().findViewById(R.id.types_exercises_list_layout_title);
        typesExercisesListLayoutDescription = (TextView) getActivity().findViewById(R.id.types_exercises_list_layout_description);
        typesExercisesListLayoutComments = (TextView) getActivity().findViewById(R.id.types_exercises_list_layout_comments);
        fragmentTypesExercisesList = (ListView) getActivity().findViewById(R.id.fragment_types_exercises_list);

        fragmentTypesExercisesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TypeExercise typeExercise = (TypeExercise) parent.getAdapter().getItem(position);
                callBack.editTypeExercise(typeExercise);
            }
        });

        fragmentTypesExercisesList.setAdapter(typesExercisesListAdapter);
    }

    private void initDB() {
        ormHelper = new OrmHelper(getActivity(), ICommonEntities.TYPE_EXERCISES_DATABASE_NAME,
                ICommonEntities.TYPE_EXERCISES_DATABASE_VERSION);
//        ormHelper.clearDatabase();
        connectionSource = ormHelper.getConnectionSource();
        try {
            commonDao = ormHelper.getDaoByClass(TypeExercise.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<TypeExercise> getAllCategories() {
        List<TypeExercise> result = null;
        try {
            result = commonDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result != null ? result : Collections.<TypeExercise>emptyList();
    }

    public interface ITypesExercisesFragmentCallBack {
        void editTypeExercise(TypeExercise typeExercise);
    }
}