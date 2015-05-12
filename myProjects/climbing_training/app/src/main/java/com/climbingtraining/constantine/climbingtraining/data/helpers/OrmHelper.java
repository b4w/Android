package com.climbingtraining.constantine.climbingtraining.data.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;

import com.climbingtraining.constantine.climbingtraining.data.common.CommonDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.AccountingQuantity;
import com.climbingtraining.constantine.climbingtraining.data.dto.Category;
import com.climbingtraining.constantine.climbingtraining.data.dto.Equipment;
import com.climbingtraining.constantine.climbingtraining.data.dto.Exercise;
import com.climbingtraining.constantine.climbingtraining.data.dto.Training;
import com.climbingtraining.constantine.climbingtraining.data.dto.TypeExercise;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * Created by KonstantinSysoev on 29.04.15.
 */
public class OrmHelper extends OrmLiteSqliteOpenHelper implements ICommonOrmHelper {

    private final static String TAG = OrmHelper.class.getSimpleName();

    private SparseArray<CommonDao> daos;

    private Class[] classes = {
            Category.class,
            Equipment.class,
            Exercise.class,
            TypeExercise.class,
            Training.class,
            AccountingQuantity.class
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
        daos = new SparseArray<>();
    }

    /**
     * Метод начального создания таблицы.
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

    /**
     * Обновление БД со старой на новую версию.
     *
     * @param database
     * @param connectionSource
     * @param oldVersion
     * @param newVersion
     */
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
     *
     * @param connectionSource
     * @throws SQLException
     */
    private void createAllTables(ConnectionSource connectionSource) throws SQLException {
        for (Class clazz : classes) {
            TableUtils.createTable(connectionSource, clazz);
        }
    }

    /**
     * Удаляем таблицы перечисленных классов - classes.
     *
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

    /**
     * Возвращает Dao подключения к БД.
     * @param classInstance
     * @return
     * @throws SQLException
     */
    public CommonDao getDaoByClass(Class<?> classInstance) throws SQLException {

//        TODO не получает dto по этому методу
//        if (classInstance.equals(Category.class)) {
//            return getCustomDaoByNum(CategoryDao.class, CATEGORY_DAO_NUMBER);
//        }

        if (classInstance.equals(Category.class)) {
            return getDaoByNum(Category.class, CATEGORY_DAO_NUMBER);
        } else if (classInstance.equals(Equipment.class)) {
            return getDaoByNum(Equipment.class, EQUIPMENT_DAO_NUMBER);
        } else if (classInstance.equals(Exercise.class)) {
            return getDaoByNum(Exercise.class, EXERCISE_DAO_NUMBER);
        } else if (classInstance.equals(TypeExercise.class)) {
            return getDaoByNum(TypeExercise.class, TYPE_EXERCISE_DAO_NUMBER);
        } else if (classInstance.equals(Training.class)) {
            return getDaoByNum(Training.class, TRAINING_DAO_NUMBER);
        } else if (classInstance.equals(AccountingQuantity.class)) {
            return getDaoByNum(AccountingQuantity.class, ACCOUNTING_QUANTITY_DAO_NUMBER);
        }
        return null;
    }

    /**
     * Возвращаем объект dao найденный по dao.class
     * @param className
     * @param daoNum
     * @return
     * @throws SQLException
     */
    private CommonDao getCustomDaoByNum(Class<? extends CommonDao> className, int daoNum) throws SQLException {
        CommonDao dao = daos.get(daoNum);
        if (dao == null) {
            try {
                dao = className.getDeclaredConstructor(ConnectionSource.class).newInstance(getConnectionSource());
                daos.put(daoNum, dao);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return dao;
    }

    /**
     * Возвращаем объект dao найденный по dto класса.
     * @param className
     * @param daoNum
     * @return
     * @throws SQLException
     */
    private CommonDao getDaoByNum(Class<?> className, int daoNum) throws SQLException {
        CommonDao dao = daos.get(daoNum);
        if (dao == null) {
            dao = new CommonDao(getConnectionSource(), className);
            daos.put(daoNum, dao);
        }
        return dao;
    }
}
