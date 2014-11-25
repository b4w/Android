package com.climbtraining.app.fragments.exercisesActivity;

import android.app.ListFragment;
import android.os.Bundle;
import com.climbtraining.app.adapters.CategoriesAdapter;
import com.climbtraining.app.dbhelpers.exercises.CategorySQLHelper;
import com.climbtraining.app.pojo.exercise.Category;

import java.util.List;

public class CategoryFragment extends ListFragment {

    private CategorySQLHelper categorySQLHelper;
    private CategoriesAdapter categoriesAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        categorySQLHelper = new CategorySQLHelper(getActivity().getApplicationContext());
        List<Category> categories = init();
        categoriesAdapter = new CategoriesAdapter(getActivity().getApplicationContext(), categories);
        setListAdapter(categoriesAdapter);
    }

    private List<Category> init() {
        return categorySQLHelper.getAllCategories();
    }

    public void updateCategoriesAdapter() {
        categoriesAdapter.notifyDataSetChanged();
    }
}
