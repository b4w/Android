package com.startandroid.p0341_simplesqlite.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private final String LOG_TAG = "myLogs";

    private Button btnAdd;
    private Button btnRead;
    private Button btnClear;
    private Button btnUpd;
    private Button btnDel;

    private EditText etName;
    private EditText etEmail;
    private EditText etID;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        btnUpd = (Button) findViewById(R.id.btnUpd);
        btnUpd.setOnClickListener(this);

        btnDel = (Button) findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etID = (EditText) findViewById(R.id.etID);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(getApplicationContext());
    }

    @Override
    public void onClick(View view) {
        // создаем объект для данных
        ContentValues contentValues = new ContentValues();

        // получаем данные из полей ввода
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String id = etID.getText().toString();

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (view.getId()) {
            case R.id.btnAdd:
                Log.d(LOG_TAG, "--- Insert in mytable: ---");

                // подготовим данные для вставки в виде пар: наименования столбца - значение
                contentValues.put("name", name);
                contentValues.put("email", email);

                // вставляем запись и получаем ее ID
                long rowID = db.insert("mytable", null, contentValues);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                break;
            case R.id.btnRead:
                Log.d(LOG_TAG, "--- Rows in mytable: ---");

                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                Cursor cursor = db.query("mytabe", null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (cursor.moveToNext()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = cursor.getColumnIndex("id");
                    int nameColIndex = cursor.getColumnIndex("name");
                    int emailColIndex = cursor.getColumnIndex("email");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + cursor.getInt(idColIndex) + ", name = " +
                                        cursor.getString(nameColIndex) + ", email = " +
                                        cursor.getString(emailColIndex));

                        // переход на следующую строку, а если следующей нет (текущая последняя),
                        // то false - выходим из цикла
                    } while (cursor.moveToNext());
                } else {
                    Log.d(LOG_TAG, "0 rows");
                }
                cursor.close();
                break;
            case R.id.btnClear:
                Log.d(LOG_TAG, "--- Clear mytable ---");

                // удаляем все записи
                int clearCount = db.delete("mytable", null, null);
                Log.d(LOG_TAG, "delete rows count = " + clearCount);
                break;
            case R.id.btnUpd:
                if (id.equalsIgnoreCase("")) {
                    break;
                }
                Log.d(LOG_TAG, "--- Update mytable ---");

                // подготовим значения для обновления
                contentValues.put("name", name);
                contentValues.put("email", email);

                // обновляем по id
                int updCount = db.update("mytable", contentValues, "id = ?", new String[]{id});
                Log.d(LOG_TAG, "updated rows count = " + updCount);
                break;
            case R.id.btnDel:
                if (id.equalsIgnoreCase("")) {
                    break;
                }
                Log.d(LOG_TAG, "--- Delete from mytable ---");

                // удаляем по id
                int delCount = db.delete("mytable", "id = " + id, null);
                Log.d(LOG_TAG, "deleted rows count = " + delCount);
                break;
        }

        // закрываем подключение к БД
        db.close();
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            // конструктор суперкласса
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate database ---");
            // создаем таблицу с полями
            db.execSQL("create table mytable (" +
                    "id integer primary key autoincrement," +
                    "name text," +
                    "email text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
