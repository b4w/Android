package com.climbingtraining.constantine.climbingtraining.data.util;

import android.content.Context;
import android.util.Log;

import com.climbingtraining.constantine.climbingtraining.data.helpers.OrmHelper;
import com.climbingtraining.constantine.climbingtraining.data.dao.MainListDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.MainList;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by KonstantinSysoev on 01.05.15.
 */
public class MainListUtils {

    private static final String TAG = MainListUtils.class.getSimpleName();

    private OrmHelper ormHelper;

    private MainListDao mainListDao;

    /**
     * Иниализация подключения к БД "Main_list".
     */
    public void initMainListDB(Context context, String databaseName, int databaseVersion) {
        Log.i(TAG, "initMainListDB() started");
        ormHelper = new OrmHelper(context, databaseName, databaseVersion);
        ormHelper.clearDatabase();
        try {
            mainListDao = new MainListDao(ormHelper.getConnectionSource());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "initMainListDB() done");
    }

    /**
     * Запись данных в БД.
     *
     * @param mainLists
     */
    public void fillDataMainList(List<com.climbingtraining.constantine.climbingtraining.pojo.MainList> mainLists) {
        Log.i(TAG, "fillDataMainList() started");
        for (com.climbingtraining.constantine.climbingtraining.pojo.MainList item : mainLists) {
            try {
                mainListDao.create(new MainList(item.getLogo(), item.getTitle(), item.getText()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, "fillDataMainList() done");
    }

    public MainListDao getMainListDao() {
        return mainListDao;
    }
}
