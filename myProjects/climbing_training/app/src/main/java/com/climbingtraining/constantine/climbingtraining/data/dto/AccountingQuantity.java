package com.climbingtraining.constantine.climbingtraining.data.dto;

import com.climbingtraining.constantine.climbingtraining.enums.MeasurementMeasure;
import com.climbingtraining.constantine.climbingtraining.enums.PhysicalTraining;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by KonstantinSysoev on 10.05.15.
 */
@DatabaseTable(tableName = "accounting_quantities")
public class AccountingQuantity implements ICommonEntities {

    @DatabaseField(generatedId = true, columnName = COLUMN_NAME_ID)
    private Integer id;

    @DatabaseField(columnName = COLUMN_NAME_NUMBER_APPROACHES)
    private int numberApproaches;

    @DatabaseField(columnName = COLUMN_NAME_NUMBER_TIME_APPROACH)
    private int numberTimeApproach;

    @DatabaseField(columnName = COLUMN_NAME_ADDITIONAL_WEIGHT)
    private float additionalWeight;

    @DatabaseField(columnName = COLUMN_NAME_MEASUREMENT_MEASURE)
    private MeasurementMeasure measurementMeasure;

    @DatabaseField(columnName = COLUMN_NAME_PHYSICAL_TRAINING)
    private PhysicalTraining physicalTraining;

    @DatabaseField(columnName = COLUMN_NAME_DISTANCE)
    private float distance;

    //    TODO переделать на временной период
    @DatabaseField(columnName = COLUMN_NAME_TIME_BEGIN)
    private Date timeBegin;

    @DatabaseField(columnName = COLUMN_NAME_TIME_END)
    private Date timeEnd;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 3)
    private Exercise exercise;

    @DatabaseField(columnName = COLUMN_NAME_DESCRIPTION)
    private String description;

    @DatabaseField(columnName = COLUMN_NAME_COMMENT)
    private String comment;

    @DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 3)
    private Training training;

    public AccountingQuantity() {
//        need for ormlite
    }

    public AccountingQuantity(int numberApproaches, int numberTimeApproach, float additionalWeight,
                              MeasurementMeasure measurementMeasure, PhysicalTraining physicalTraining,
                              float distance, Date timeBegin, Date timeEnd, Exercise exercise,
                              String description, String comment, Training training) {
        this.numberApproaches = numberApproaches;
        this.numberTimeApproach = numberTimeApproach;
        this.additionalWeight = additionalWeight;
        this.measurementMeasure = measurementMeasure;
        this.physicalTraining = physicalTraining;
        this.distance = distance;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
        this.exercise = exercise;
        this.description = description;
        this.comment = comment;
        this.training = training;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public PhysicalTraining getPhysicalTraining() {
        return physicalTraining;
    }

    public void setPhysicalTraining(PhysicalTraining physicalTraining) {
        this.physicalTraining = physicalTraining;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }
}
