//package com.example.android.constantine.weather.data;
//
//import com.j256.ormlite.jdbc.JdbcConnectionSource;
//import com.j256.ormlite.support.ConnectionSource;
//
//import java.sql.SQLException;
//
///**
// * Created by KonstantinSysoev on 24.04.15.
// */
//public class TestDBHelper {
//
//    private static final String DB_NAME = "unit_test.db";
//    private ConnectionSource connectionSource;
//    private String databaseUrl = "jdbc:sqlite" + DB_NAME;
//
//    /**
//     * Create connection to DB.
//     * @return - connection or null
//     */
//    public ConnectionSource getConnectionSource() {
//        try {
//            connectionSource = new JdbcConnectionSource(databaseUrl);
//            return connectionSource;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
