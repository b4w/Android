package com.climbingtraining.constantine.climbingtraining.data.dao;

import com.climbingtraining.constantine.climbingtraining.data.common.CommonDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.MainList;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by KonstantinSysoev on 29.04.15.
 */
public class MainListDao extends CommonDao<MainList, Integer> {

    private MainListDao mainListDao;

    public MainListDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, MainList.class);
    }

    /**
     * Вернуть все записи из главного списка.
     * @return
     * @throws SQLException
     */
    public List<MainList> getMainList() throws SQLException {
        return mainListDao.queryForAll();
    }

    /**
     * Удалить запись в главном списке по его id.
     * @param id
     * @throws SQLException
     */
    public void deleteMainListItemAtId(int id) throws SQLException {
        mainListDao.deleteById(id);
    }

    /**
     * Вернуть запись из списка по его id.
     * @param id
     * @return
     * @throws SQLException
     */
    public MainList getMainListItemById(int id) throws SQLException {
        return (MainList) mainListDao.queryForId(id);
    }

    /**
     * Добавить пункт в список.
     * @param mainList
     * @throws SQLException
     */
    public void addMainListItem(MainList mainList) throws SQLException {
        mainListDao.create(mainList);
    }

    public PreparedQuery getTestQuery() throws SQLException {
        QueryBuilder<MainList, Integer> queryBuilder = queryBuilder();
//        queryBuilder.where()
        PreparedQuery<MainList> preparedQuery = queryBuilder.prepare();
        return preparedQuery;
    }

    public List<MainList> searchByName(String name) throws SQLException {
        return queryBuilder().where().like(MainList.COLUMN_NAME_TITLE, name + "%").query();
    }


}
