package com.climbtraining.app.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.climbtraining.app.R;
import com.climbtraining.app.dbhelpers.exercises.CategorySQLHelper;
import com.climbtraining.app.dbhelpers.exercises.ExercisesSQLHelper;
import com.climbtraining.app.fragments.exercisesActivity.AddCategoryFragment;
import com.climbtraining.app.fragments.exercisesActivity.ICommunicatorExercises;
import com.climbtraining.app.pojo.Exercise;
import com.climbtraining.app.pojo.exercise.Category;

public class ExercisesActivity extends FragmentActivity implements ICommunicatorExercises {

    private static final String TAG = Exercise.class.getSimpleName();
    private AddCategoryFragment addCategoryFragment;
    private FragmentManager fragmentManager;
    private CategorySQLHelper categorySQLHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        addCategoryFragment = new AddCategoryFragment();
        fragmentManager = getFragmentManager();
        categorySQLHelper = new CategorySQLHelper(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.exercises, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuSettings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
            case R.id.menuWorkouts:
                startActivity(new Intent(getApplicationContext(), WorkoutsActivity.class));
                break;
            case R.id.menuMain:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNewCategory(String nameCategory) {
        Category category = new Category();
        category.setName(nameCategory);
        categorySQLHelper.insertValue(category);
    }

    public void showDialogAddCategory() {
        addCategoryFragment.show(fragmentManager, "AddCategoryFragment");
    }
}
