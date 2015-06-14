package com.climbingtraining.constantine.climbingtraining.data.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by KonstantinSysoev on 10.05.15.
 */
@DatabaseTable(tableName = "trainings")
public class Training implements ICommonEntities {

    @DatabaseField(generatedId = true, columnName = COLUMN_NAME_ID)
    private Integer id;

    @DatabaseField(columnName = COLUMN_NAME_DATE)
    private Date date;

    @DatabaseField(columnName = COLUMN_NAME_PHYSICAL_TRAINING_IMAGE_PATH)
    private String physicalTrainingImagePath;

    @DatabaseField(columnName = COLUMN_NAME_DESCRIPTION)
    private String description;

    @DatabaseField(columnName = COLUMN_NAME_COMMENT)
    private String comment;

    @ForeignCollectionField(eager = true)
//    @ForeignCollectionField(eager = true,maxEagerLevel = 7, foreignFieldName = "training", maxEagerForeignCollectionLevel = 7)
    private Collection<AccountingQuantity> quantities;

    public Training() {
//        need for ormlite
    }

    public Training(Date date, String physicalTrainingImagePath,
                    String description, String comment, List<AccountingQuantity> quantities) {
        this.date = date;
        this.physicalTrainingImagePath = physicalTrainingImagePath;
        this.description = description;
        this.comment = comment;
        this.quantities = quantities;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPhysicalTrainingImagePath() {
        return physicalTrainingImagePath;
    }

    public void setPhysicalTrainingImagePath(String physicalTrainingImagePath) {
        this.physicalTrainingImagePath = physicalTrainingImagePath;
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

    public Collection<AccountingQuantity> getQuantities() {
        return quantities;
    }

    public void setQuantities(Collection<AccountingQuantity> quantities) {
        this.quantities = quantities;
    }
}
