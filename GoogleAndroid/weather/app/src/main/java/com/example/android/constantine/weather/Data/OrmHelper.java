package com.example.android.constantine.weather.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;

import com.example.android.constantine.weather.data.dao.WeatherDAO;
import com.example.android.constantine.weather.data.dto.WeatherDTO;
import com.example.android.constantine.weather.enums.WeatherDBUtilsEnum;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by KonstantinSysoev on 23.04.15.
 */
public class OrmHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = OrmHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;

    private SparseArray<WeatherDAO> daos;

    private Class[] classes = {
            WeatherDTO.class
    };

    /**
     * Конструктор для создания подключения к БД.
     * @param context
     */
    public OrmHelper(Context context) {
        super(context, WeatherDBUtilsEnum.DATABASE_NAME.getName(), null, DATABASE_VERSION);
        daos = new SparseArray<>();
    }

    /**
     * Метод начального создания таблицы.
     * @param database
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(TAG, "onCreate() started");
            createAllTables(connectionSource);
        } catch (SQLException e) {
//            TODO
        }
    }

    /**
     * Метод обновления баз данных.
     * @param db - имя БД.
     * @param connectionSource
     * @param oldVersion - номер старой версии БД.
     * @param newVersion - номер новой версии БД.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(TAG, "onUpgrade");
            dropAllTables(connectionSource);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Закрытие подключения к БД.
     */
    @Override
    public void close() {
        super.close();
        daos = null;
    }

    /**
     * Удаляем таблицы перечисленных классов - classes.
     * @param connectionSource
     * @throws SQLException
     */
    private void dropAllTables(ConnectionSource connectionSource) throws SQLException {
        for (Class clazz : classes) {
            TableUtils.dropTable(connectionSource, clazz, true);
        }
    }

    /**
     * Создаем таблицы для перечисленных классов - classes.
     * @param connectionSource
     * @throws SQLException
     */
    private void createAllTables(ConnectionSource connectionSource) throws SQLException{
        for (Class clazz : classes) {
            TableUtils.createTable(connectionSource, clazz);
        }
    }

    /**
     * Очистка базы данных - удаление предыдущей и создание новой.
     */
    public void clearDatabase() {
        try {
            Log.i(TAG, "clearDatabase() started");
            dropAllTables(connectionSource);
            createAllTables(connectionSource);
            Log.i(TAG, "clearDatabase() done");
        } catch (SQLException e) {
            Log.e(TAG, "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }
}
