package com.climbingtraining.constantine.climbingtraining.data.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by KonstantinSysoev on 29.04.15.
 */
public class MainListDao<MainListDto, Integer> extends BaseDaoImpl<MainListDto, Integer> {

    private MainListDao mainListDao;

    public MainListDao(ConnectionSource connectionSource, Class<MainListDto> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<MainListDto> getWeatherList() throws SQLException {
        return mainListDao.queryForAll();
    }

    public void deleteWeatherAtId(int id) throws SQLException {
        mainListDao.deleteById(id);
    }

    public MainListDto getWeatherById(int id) throws SQLException {
        return (MainListDto) mainListDao.queryForId(id);
    }

    public void addWeather(MainListDto mainListDto) throws SQLException {
        mainListDao.create(mainListDto);
    }

    public PreparedQuery getTestQuery() throws SQLException {
        QueryBuilder<MainListDto, Integer> queryBuilder = queryBuilder();
//        queryBuilder.where()
        PreparedQuery<MainListDto> preparedQuery = queryBuilder.prepare();
        return preparedQuery;
    }
}
