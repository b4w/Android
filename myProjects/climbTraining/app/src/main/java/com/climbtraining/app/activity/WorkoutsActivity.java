package com.climbtraining.app.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.climbtraining.app.R;
import com.climbtraining.app.adapters.TrainingAdapter;
import com.climbtraining.app.dbhelpers.WorkoutSQLHelper;
import com.climbtraining.app.pojo.Training;
import com.climbtraining.app.utils.DateFormatting;
import com.climbtraining.app.utils.RequestCodes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WorkoutsActivity extends ActionBarActivity {

    private final String LOG = "WorkoutActivityLog";

    private List<Training> listTraining;
    private ListView listViewWorkout;
    private WorkoutSQLHelper workoutSQLHelper;
    private SQLiteDatabase sqLiteDatabase;

    private String workoutName;
    private String workoutComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        // todo подключаемся к БД и смотрим записи - обновляем лист
        updateViewList();
        listViewWorkout = (ListView) findViewById(R.id.listViewWorkout);
        TrainingAdapter trainingAdapter = new TrainingAdapter(this, getListTraining());
        listViewWorkout.setAdapter(trainingAdapter);
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

    public void onCreateNewWorkout(View view) {
        Log.d(LOG, "add new workout");
        Intent intent = new Intent(this, WorkoutActivity.class);
        startActivityForResult(intent, RequestCodes.REQUEST_CODE_WORKOUT.ordinal());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RequestCodes.REQUEST_CODE_WORKOUT.ordinal()) {
                setWorkoutName(data.getStringExtra("etWorkoutName"));
                setWorkoutComments(data.getStringExtra("etWorkoutComments"));
                addNewEntry();
                // todo часто обращаюсь к БД, надо переделать
                updateViewList();
            }
        } else {
            Toast.makeText(getApplicationContext(), "ERROR!", Toast.LENGTH_SHORT).show();
        }
    }

    private void addNewEntry() {
        Log.d(LOG, "addNewEntry() start");

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
        Log.d(LOG, "addNewEntry() done");
    }

    private void updateViewList() {
        Log.d(LOG, "updateViewList() start");
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
        Log.d(LOG, "updateViewList() done. ListTraining size = " + getListTraining().size());
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
