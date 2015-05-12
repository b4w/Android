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
import com.climbingtraining.constantine.climbingtraining.adapters.CategoryListAdapter;
import com.climbingtraining.constantine.climbingtraining.data.common.CommonDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.Category;
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
public class CategoryFragment extends Fragment {

    private final static String TAG = CategoryFragment.class.getSimpleName();

    private ImageView imageView;
    private TextView fragmentCategoryTitle;
    private TextView fragmentCategoryDescription;
    private TextView fragmentCategoryComments;

    private List<Category> categories;
    private CategoryListAdapter categoryListAdapter;
    private ListView fragmentCategoryList;
    private ICategoryFragmentCallBack callBack;
    private FloatingActionButton fragmentCategoryFloatButton;

    private OrmHelper ormHelper;
    private ConnectionSource connectionSource;
    private CommonDao commonDao;

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callBack = (ICategoryFragmentCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement ICategoryFragmentCallBack.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        initDB();
        categories = getAllCategories();
        categoryListAdapter = new CategoryListAdapter(getActivity(), categories);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragmentCategoryTitle = (TextView) getActivity().findViewById(R.id.category_list_layout_title);
        fragmentCategoryDescription = (TextView) getActivity().findViewById(R.id.category_list_layout_description);
        fragmentCategoryComments = (TextView) getActivity().findViewById(R.id.category_list_layout_comments);
        fragmentCategoryList = (ListView) getActivity().findViewById(R.id.fragment_category_list);
        fragmentCategoryFloatButton = (FloatingActionButton)getActivity().findViewById(R.id.fragment_category_float_button);
        fragmentCategoryFloatButton.attachToListView(fragmentCategoryList);

        fragmentCategoryFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.createNewCategory();
            }
        });

        fragmentCategoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = (Category) parent.getAdapter().getItem(position);
                callBack.editCategory(category);
            }
        });

        fragmentCategoryList.setAdapter(categoryListAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        initDB();
        categories = getAllCategories();
        categoryListAdapter = new CategoryListAdapter(getActivity(), categories);
    }

    private void initDB() {
        ormHelper = new OrmHelper(getActivity(), ICommonEntities.CATEGORIES_DATABASE_NAME,
                ICommonEntities.CATEGORIES_DATABASE_VERSION);
//        ormHelper.clearDatabase();
        connectionSource = ormHelper.getConnectionSource();
        try {
            commonDao = ormHelper.getDaoByClass(Category.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Category> getAllCategories() {
        List<Category> result = null;
        try {
            result = commonDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result != null ? result : Collections.<Category>emptyList();
    }


    public interface ICategoryFragmentCallBack {
        void editCategory(Category category);
        void createNewCategory();
    }
}
