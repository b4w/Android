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
import com.climbingtraining.constantine.climbingtraining.adapters.CategoriesListAdapter;
import com.climbingtraining.constantine.climbingtraining.data.common.CommonDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.Category;
import com.j256.ormlite.android.loadercallback.OrmCursorLoaderCallback;
import com.j256.ormlite.stmt.PreparedQuery;
import com.melnykov.fab.FloatingActionButton;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Created by KonstantinSysoev on 08.05.15.
 */
public class CategoriesFragment extends AbstractCategoriesListFragment {

    private final static String TAG = CategoriesFragment.class.getSimpleName();

    private ImageView imageView;
    private TextView fragmentCategoryTitle;
    private TextView fragmentCategoryDescription;
    private TextView fragmentCategoryComments;

    private List<Category> categories;
    private CategoriesListAdapter categoriesListAdapter;
    private ListView fragmentCategoryList;
    private ICategoryFragmentCallBack callBack;
    private FloatingActionButton fragmentCategoryFloatButton;

    private OrmCursorLoaderCallback<Category, Integer> categoryLoaderCallback;

    private CommonDao commonDao;

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
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        categoriesListAdapter = new CategoriesListAdapter(getActivity());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: костыль!
        // т.к. почему-то не работает categoryLoaderCallback (дожен обновляться автоматически),
        // на время поставил restartLoader
        if (categoriesListAdapter.getCursor() != null) {
            getActivity().getLoaderManager().restartLoader(CATEGORIES_ORM_LOADER_ID, null, categoryLoaderCallback);
        }
    }

    public static CategoriesFragment newInstance() {
        return new CategoriesFragment();
    }

    protected void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        fragmentCategoryTitle = (TextView) getActivity().findViewById(R.id.category_list_layout_title);
        fragmentCategoryDescription = (TextView) getActivity().findViewById(R.id.category_list_layout_description);
        fragmentCategoryComments = (TextView) getActivity().findViewById(R.id.category_list_layout_comments);
        fragmentCategoryList = (ListView) getActivity().findViewById(R.id.fragment_category_list);
        fragmentCategoryFloatButton = (FloatingActionButton) getActivity().findViewById(R.id.fragment_category_float_button);
        fragmentCategoryFloatButton.attachToListView(fragmentCategoryList);
        Log.d(TAG, "initXmlFields() done");
    }

    protected void initListeners() {
        Log.d(TAG, "initListeners() start");
        fragmentCategoryFloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.createNewCategory();
            }
        });

        fragmentCategoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = categoriesListAdapter.getTypedItem(position);
                callBack.editCategory(category);
            }
        });

        fragmentCategoryList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CategoriesListAdapter.ViewHolder holder;
                holder = (CategoriesListAdapter.ViewHolder) view.getTag();
                if (holder.isChecked) {
                    holder.isChecked = false;
                    // выделение цветом выбранной позиции
                    view.setBackgroundColor(getResources().getColor(R.color.text_icon));
                } else {
                    holder.isChecked = true;
                    // выделение цветом выбранной позиции
                    view.setBackgroundColor(getResources().getColor(R.color.divider_color));
                }
                Category category = categoriesListAdapter.getTypedItem(position);
                updateEntitiesForEditing(holder.isChecked, category);
                callBack.showHideOptionsMenu(!getEntitiesForEditing().isEmpty());
                return true;
            }
        });
        Log.d(TAG, "initListeners() done");
    }

    public void updateEntities() {
        Log.d(TAG, "updateEntities() start");
        List<Category> result = null;
        try {
            commonDao = getOrmHelper().getDaoByClass(Category.class);
            result = commonDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "updateEntities() done");
        categories = result != null ? result : Collections.<Category>emptyList();
    }

    protected void initOrmCursorLoader() {
        Log.d(TAG, "initOrmCursorLoader() start");
        fragmentCategoryList.setAdapter(categoriesListAdapter);
        try {
            PreparedQuery query = commonDao.queryBuilder().prepare();
            categoryLoaderCallback =
                    new OrmCursorLoaderCallback<Category, Integer>(getActivity(), commonDao, query, categoriesListAdapter);
            getActivity().getLoaderManager().initLoader(CATEGORIES_ORM_LOADER_ID, null, categoryLoaderCallback);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, "initOrmCursorLoader() done");
    }

    public interface ICategoryFragmentCallBack {
        void editCategory(Category category);

        void createNewCategory();

        void showHideOptionsMenu(boolean entitiesIsChecked);
    }
}
