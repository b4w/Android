package com.climbingtraining.constantine.climbingtraining.data.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by KonstantinSysoev on 04.05.15.
 */
@DatabaseTable(tableName = "exercises")
public class Exercise extends AbstractEntity {

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Category category;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Equipment equipment;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private TypeExercise typeExercise;

    public Exercise() {
//        need for ormlite
    }

    public Exercise(String name, String imagePath, String description, String comment, Category category, Equipment equipment, TypeExercise typeExercise) {
        super(name, imagePath, description, comment);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Exercise exercise = (Exercise) o;

        if (category != null ? !category.equals(exercise.category) : exercise.category != null)
            return false;
        if (equipment != null ? !equipment.equals(exercise.equipment) : exercise.equipment != null)
            return false;
        return !(typeExercise != null ? !typeExercise.equals(exercise.typeExercise) : exercise.typeExercise != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (equipment != null ? equipment.hashCode() : 0);
        result = 31 * result + (typeExercise != null ? typeExercise.hashCode() : 0);
        return result;
    }
}
