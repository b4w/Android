package com.climbingtraining.constantine.climbingtraining.data.dto;

import com.climbingtraining.constantine.climbingtraining.enums.MeasurementMeasure;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by KonstantinSysoev on 10.05.15.
 */
@DatabaseTable(tableName = "accounting_quantities")
public class AccountingQuantity implements ICommonEntities {

    @DatabaseField(generatedId = true, columnName = COLUMN_NAME_ID)
    private int id;

    @DatabaseField(columnName = COLUMN_NAME_NUMBER_APPROACHES)
    private int numberApproaches;

    @DatabaseField(columnName = COLUMN_NAME_NUMBER_TIME_APPROACH)
    private int numberTimeApproach;

    @DatabaseField(columnName = COLUMN_NAME_ADDITIONAL_WEIGHT)
    private float additionalWeight;

    @DatabaseField(columnName = COLUMN_NAME_MEASUREMENT_MEASURE)
    private MeasurementMeasure measurementMeasure;

    @DatabaseField(columnName = COLUMN_NAME_DISTANCE)
    private float distance;

    //    TODO переделать на временной период
    @DatabaseField(columnName = COLUMN_NAME_TIME_BEGIN)
    private Date timeBegin;

    @DatabaseField(columnName = COLUMN_NAME_TIME_END)
    private Date timeEnd;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Exercise exercise;

    @DatabaseField(columnName = COLUMN_NAME_DESCRIPTION)
    private String description;

    @DatabaseField(columnName = COLUMN_NAME_COMMENT)
    private String comment;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Training training;

    public AccountingQuantity() {
//        need for ormlite
    }

    public AccountingQuantity(int numberApproaches, int numberTimeApproach, float additionalWeight,
                              MeasurementMeasure measurementMeasure, float distance, Date timeBegin,
                              Date timeEnd, Exercise exercise, String description, String comment) {
        this.numberApproaches = numberApproaches;
        this.numberTimeApproach = numberTimeApproach;
        this.additionalWeight = additionalWeight;
        this.measurementMeasure = measurementMeasure;
        this.distance = distance;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.exercise = exercise;
        this.description = description;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberApproaches() {
        return numberApproaches;
    }

    public void setNumberApproaches(int numberApproaches) {
        this.numberApproaches = numberApproaches;
    }

    public int getNumberTimeApproach() {
        return numberTimeApproach;
    }

    public void setNumberTimeApproach(int numberTimeApproach) {
        this.numberTimeApproach = numberTimeApproach;
    }

    public float getAdditionalWeight() {
        return additionalWeight;
    }

    public void setAdditionalWeight(float additionalWeight) {
        this.additionalWeight = additionalWeight;
    }

    public MeasurementMeasure getMeasurementMeasure() {
        return measurementMeasure;
    }

    public void setMeasurementMeasure(MeasurementMeasure measurementMeasure) {
        this.measurementMeasure = measurementMeasure;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public Date getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(Date timeBegin) {
        this.timeBegin = timeBegin;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountingQuantity that = (AccountingQuantity) o;

        if (id != that.id) return false;
        if (numberApproaches != that.numberApproaches) return false;
        if (numberTimeApproach != that.numberTimeApproach) return false;
        if (Float.compare(that.additionalWeight, additionalWeight) != 0) return false;
        if (Float.compare(that.distance, distance) != 0) return false;
        if (measurementMeasure != that.measurementMeasure) return false;
        if (timeBegin != null ? !timeBegin.equals(that.timeBegin) : that.timeBegin != null)
            return false;
        if (timeEnd != null ? !timeEnd.equals(that.timeEnd) : that.timeEnd != null) return false;
        if (exercise != null ? !exercise.equals(that.exercise) : that.exercise != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        return !(comment != null ? !comment.equals(that.comment) : that.comment != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + numberApproaches;
        result = 31 * result + numberTimeApproach;
        result = 31 * result + (additionalWeight != +0.0f ? Float.floatToIntBits(additionalWeight) : 0);
        result = 31 * result + (measurementMeasure != null ? measurementMeasure.hashCode() : 0);
        result = 31 * result + (distance != +0.0f ? Float.floatToIntBits(distance) : 0);
        result = 31 * result + (timeBegin != null ? timeBegin.hashCode() : 0);
        result = 31 * result + (timeEnd != null ? timeEnd.hashCode() : 0);
        result = 31 * result + (exercise != null ? exercise.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
