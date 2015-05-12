package com.climbingtraining.constantine.climbingtraining.data.dto;

import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by KonstantinSysoev on 04.05.15.
 */
@DatabaseTable(tableName = "equipments")
public class Equipment extends AbstractEntity {

    public Equipment() {
//        need for OrmLite
    }

    public Equipment(String name, String imagePath, String description, String comment) {
        super(name, imagePath, description, comment);
    }
}
