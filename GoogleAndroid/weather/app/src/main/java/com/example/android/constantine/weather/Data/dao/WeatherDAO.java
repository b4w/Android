package com.example.android.constantine.weather.data.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by KonstantinSysoev on 23.04.15.
 */
public class WeatherDAO<WeatherDTO, Integer> extends BaseDaoImpl<WeatherDTO, Integer> {

    private WeatherDAO weatherDAO;

    public WeatherDAO(ConnectionSource connectionSource, Class<WeatherDTO> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<WeatherDTO> getWeatherList() throws SQLException {
        return weatherDAO.queryForAll();
    }

    public void deleteWeatherAtId(int id) throws SQLException {
        weatherDAO.deleteById(id);
    }

    public WeatherDTO getWeatherById(int id) throws SQLException {
        return (WeatherDTO) weatherDAO.queryForId(id);
    }

    public void addWeather(WeatherDTO weatherDTO) throws SQLException {
        weatherDAO.create(weatherDTO);
    }

//    public WeatherDAO getWeatherDAO() {
//        return weatherDAO;
//    }
//
//    public void setWeatherDAO(WeatherDAO weatherDAO) {
//        this.weatherDAO = weatherDAO;
//    }
}
