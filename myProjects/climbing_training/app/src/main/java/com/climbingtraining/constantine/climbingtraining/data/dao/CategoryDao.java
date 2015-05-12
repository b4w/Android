package com.climbingtraining.constantine.climbingtraining.data.dao;

import com.climbingtraining.constantine.climbingtraining.data.common.CommonDao;
import com.climbingtraining.constantine.climbingtraining.data.dto.Category;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by KonstantinSysoev on 04.05.15.
 */
public class CategoryDao extends CommonDao <Category, Integer> {

    protected CategoryDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Category.class);
    }

    public List<Category> searchByName(String name) throws SQLException {
        return queryBuilder().where().like(Category.COLUMN_NAME_NAME, name + "%").query();
    }
}
