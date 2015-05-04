package com.climbingtraining.constantine.climbingtraining.data.dto;

import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by KonstantinSysoev on 04.05.15.
 */
@DatabaseTable(tableName = "exercises")
public class Exercise extends AbstractEntity {

    private Category category;
    private Equipment equipment;
    private TypeExercise typeExercise;

    public Exercise() {
    }

    public Exercise(String name, int image, String description, String comment, Category category, Equipment equipment, TypeExercise typeExercise) {
        super(name, image, description, comment);
        this.category = category;
        this.equipment = equipment;
        this.typeExercise = typeExercise;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public TypeExercise getTypeExercise() {
        return typeExercise;
    }

    public void setTypeExercise(TypeExercise typeExercise) {
        this.typeExercise = typeExercise;
    }
}
