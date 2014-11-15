package com.climbtraining.app.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.climbtraining.app.R;
import com.climbtraining.app.dbhelpers.WorkoutSQLHelper;
import com.climbtraining.app.fragments.workoutsActivity.ICommunicatorWorkouts;
import com.climbtraining.app.fragments.workoutsActivity.ListViewWorkoutFragment;
import com.climbtraining.app.pojo.Training;
import com.climbtraining.app.utils.DateFormatting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WorkoutsActivity extends FragmentActivity implements ICommunicatorWorkouts {

    private final String TAG = WorkoutsActivity.class.getSimpleName();

    private List<Training> listTraining;
    private ListView listViewWorkout;
    private WorkoutSQLHelper workoutSQLHelper;
    private SQLiteDatabase sqLiteDatabase;

    private String workoutName;
    private String workoutComments;

    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        // todo подключаемся к БД и смотрим записи - обновляем лист
//        updateViewList();
//        listViewWorkout = (ListView) findViewById(R.id.listViewWorkout);
//        TrainingAdapter trainingAdapter = new TrainingAdapter(this, getListTraining());
//        listViewWorkout.setAdapter(trainingAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.workouts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSettings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
            case R.id.menuMain:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
            case R.id.menuExercises:
                startActivity(new Intent(getApplicationContext(), ExercisesActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addItemToDataList(String item) {
        fragmentManager = getFragmentManager();
        ListViewWorkoutFragment fragment = (ListViewWorkoutFragment) fragmentManager.findFragmentById(R.id.listViewWorkoutFragment);
        fragment.addItemToDataList(item);
    }

    private void addNewEntry() {
        Log.d(TAG, "addNewEntry() start");

        // todo перенести в onCreate?
        ContentValues contentValues = new ContentValues();

        // получаем данные из полей ввода
        String exercise = getWorkoutName();
        String comments = getWorkoutComments();

        // подключаемся к БД
        if (sqLiteDatabase == null || (!sqLiteDatabase.isOpen() && sqLiteDatabase.isReadOnly())) {
            sqLiteDatabase = getWorkoutSQLHelper().getWritableDatabase();
        }

        // добавляем запись в БД
        contentValues.put("date", DateFormatting.dateToString(Calendar.getInstance().getTime()));
        contentValues.put("workout", exercise);
        contentValues.put("comments", comments);

        sqLiteDatabase.insert(WorkoutSQLHelper.TABLE_NAME, null, contentValues);
        Log.d(TAG, "addNewEntry() done");
    }

    private void updateViewList() {
        Log.d(TAG, "updateViewList() start");
        //  делаем запрос к БД, вытаскиваем все записи
        if (sqLiteDatabase == null || (sqLiteDatabase.isReadOnly() && !sqLiteDatabase.isOpen())) {
            sqLiteDatabase = getWorkoutSQLHelper().getWritableDatabase();
        }
        Cursor cursor = sqLiteDatabase.query(WorkoutSQLHelper.TABLE_NAME, null, null, null, null, null, null);
        // ставим позицию курсора на первую строку выборки,
        // если в выборке нет строк, вернется false
        if (cursor.moveToNext()) {
            // определим номера столбцов по имени в выборке
            int idColIndex = cursor.getColumnIndex("id");
            int dateColIndex = cursor.getColumnIndex("date");
            int workoutColIndex = cursor.getColumnIndex("workout");
            int commentsColIndex = cursor.getColumnIndex("comments");
            do {
                // получаем значения по номерам столбцов и создаем новую запись
                Training training = new Training(cursor.getInt(idColIndex),
                        DateFormatting.stringToDate(cursor.getString(dateColIndex)),
                        cursor.getString(workoutColIndex),
                        cursor.getString(commentsColIndex));

                getListTraining().add(training);
            } while (cursor.moveToNext());
        }
        cursor.close();
        Log.d(TAG, "updateViewList() done. ListTraining size = " + getListTraining().size());
    }

    // ------------- GETTERS AND SETTERS -----------------

    public WorkoutSQLHelper getWorkoutSQLHelper() {
        if (workoutSQLHelper == null) {
            workoutSQLHelper = new WorkoutSQLHelper(getApplicationContext());
        }
        return workoutSQLHelper;
    }

    public List<Training> getListTraining() {
        if (listTraining == null) {
            listTraining = new ArrayList<Training>();
        }
        return listTraining;
    }

    public void setListTraining(List<Training> listTraining) {
        this.listTraining = listTraining;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getWorkoutComments() {
        return workoutComments;
    }

    public void setWorkoutComments(String workoutComments) {
        this.workoutComments = workoutComments;
    }
}
