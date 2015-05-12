package com.climbingtraining.constantine.climbingtraining.data.dto;

import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by KonstantinSysoev on 04.05.15.
 */
@DatabaseTable(tableName = "types_exercise")
public class TypeExercise extends AbstractEntity {

    public TypeExercise() {
//        need for OrmLite
    }

    public TypeExercise(String name, String imagePath, String description, String comment) {
        super(name, imagePath, description, comment);
    }
}
