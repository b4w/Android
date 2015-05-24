package com.climbingtraining.constantine.climbingtraining.data.dto;

import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by KonstantinSysoev on 04.05.15.
 */
@DatabaseTable(tableName = "categories")
public class Category extends AbstractEntity {

    public Category() {
//        need for OrmLite
    }

    public Category(String name, String imagePath, String description, String comment) {
        super(name, imagePath, description, comment);
    }

    public Category(Integer id, String name, String imagePath, String description, String comment) {
        super(id, name, imagePath, description, comment);
    }
}
