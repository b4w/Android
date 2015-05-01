package com.climbingtraining.constantine.climbingtraining.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.climbingtraining.constantine.climbingtraining.data.dto.MainListDto;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by KonstantinSysoev on 29.04.15.
 */
public class OrmHelper extends OrmLiteSqliteOpenHelper {

    private final static String TAG = OrmHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;

    private Class[] classes = {
            MainListDto.class
    };

    /**
     * Конструктор для создания подключения к БД.
     *
     * @param context
     * @param databaseName
     * @param databaseVersion
     */
    public OrmHelper(Context context, String databaseName, int databaseVersion) {
        super(context, databaseName, null, databaseVersion);
    }

    /**
     * Метод начального создания таблицы
     *
     * @param database
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(TAG, "onCreate() started");
            createAllTables(connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(TAG, "onUpgrade() started");
            dropAllTables(connectionSource);
            onCreate(database, connectionSource);
            Log.i(TAG, "onUpgrade() done");
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
     * Очистка базы данных - удаление предыдущей и создание новой.
     */
    public void clearDatabase() {
        try {
//            TODO сделать перенос данных с одной БД в другую
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
