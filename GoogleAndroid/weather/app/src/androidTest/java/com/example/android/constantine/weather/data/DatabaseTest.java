//package com.example.android.constantine.weather.data;
//
//import android.app.Application;
//import android.test.ApplicationTestCase;
//import android.util.Log;
//
//import com.example.android.constantine.weather.ApplicationTest;
//import com.example.android.constantine.weather.data.dao.WeatherDAO;
//import com.example.android.constantine.weather.data.dto.WeatherDTO;
//import com.j256.ormlite.dao.DaoManager;
//import com.j256.ormlite.support.ConnectionSource;
//import com.j256.ormlite.table.TableUtils;
//
//import junit.framework.Assert;
//
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import java.sql.SQLException;
//
///**
// * Created by KonstantinSysoev on 24.04.15.
// */
//public class DatabaseTest extends ApplicationTestCase<Application> {
//
//    static WeatherDAO<WeatherDTO, Integer> weatherDao = null;
//    static ConnectionSource connectionSource = null;
//
//    public DatabaseTest() {
//        super(Application.class);
//    }
//
//    @BeforeClass
//    public static void setUpDatabaseLayer() throws SQLException {
//        Log.d("TAG", "Test. BeforeClass");
//        connectionSource = new TestDBHelper().getConnectionSource();
//        TableUtils.createTableIfNotExists(connectionSource, WeatherDTO.class);
//
//        try {
//            weatherDao = DaoManager.createDao(connectionSource, WeatherDTO.class);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Before
//    public void clearTable() {
//        Log.d("TAG", "Test. Before");
//        try {
//            TableUtils.createTable(connectionSource, WeatherDTO.class);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Test
//    public void weatherCanBeStoredInDB() {
//        // id - autoincrement ????
//        Log.d("TEST", "IN TEST 1");
//        WeatherDTO weatherDto1 = new WeatherDTO(1, "31-01-2015", 123.03, 321.02);
//        WeatherDTO weatherDto2 = new WeatherDTO(2, "23-01-2015", 322.03, 432.02);
//        WeatherDTO read_weatherDto1 = null, read_weatherDto2 = null;
//
//        try {
//            weatherDao.create(weatherDto1);
//            weatherDao.create(weatherDto2);
//
//            read_weatherDto1 = weatherDao.queryForId(1);
//            read_weatherDto2 = weatherDao.queryForId(2);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        Log.d("TEST", "IN TEST 2");
//        Assert.assertEquals("31-01-2015", read_weatherDto1.getDate());
//        Assert.assertEquals("23-01-2015", read_weatherDto2.getDate());
//    }
//}
