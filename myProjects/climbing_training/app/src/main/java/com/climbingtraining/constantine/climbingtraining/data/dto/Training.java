package com.climbingtraining.constantine.climbingtraining.data.dto;

import com.climbingtraining.constantine.climbingtraining.enums.PhysicalTraining;
import com.j256.ormlite.field.DataType;
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
    private int id;

    @DatabaseField(columnName = COLUMN_NAME_DATE)
    private Date date;

    @DatabaseField(columnName = COLUMN_NAME_PHYSICAL_TRAINING)
    private PhysicalTraining physicalTraining;

    @DatabaseField(columnName = COLUMN_NAME_PHYSICAL_TRAINING_IMAGE_PATH)
    private String physicalTrainingImagePath;

    @DatabaseField(columnName = COLUMN_NAME_DESCRIPTION)
    private String description;

    @DatabaseField(columnName = COLUMN_NAME_COMMENT)
    private String comment;

    @ForeignCollectionField(eager = true)
    private Collection<AccountingQuantity> quantities;

    public Training() {
//        need for ormlite
    }

    public Training(Date date, PhysicalTraining physicalTraining, String physicalTrainingImagePath,
                    String description, String comment, List<AccountingQuantity> quantities) {
        this.date = date;
        this.physicalTraining = physicalTraining;
        this.physicalTrainingImagePath = physicalTrainingImagePath;
        this.description = description;
        this.comment = comment;
        this.quantities = quantities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PhysicalTraining getPhysicalTraining() {
        return physicalTraining;
    }

    public void setPhysicalTraining(PhysicalTraining physicalTraining) {
        this.physicalTraining = physicalTraining;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Training training = (Training) o;

        if (id != training.id) return false;
        if (date != null ? !date.equals(training.date) : training.date != null) return false;
        if (physicalTraining != training.physicalTraining) return false;
        if (physicalTrainingImagePath != null ? !physicalTrainingImagePath.equals(training.physicalTrainingImagePath) : training.physicalTrainingImagePath != null)
            return false;
        if (description != null ? !description.equals(training.description) : training.description != null)
            return false;
        if (comment != null ? !comment.equals(training.comment) : training.comment != null)
            return false;
        return !(quantities != null ? !quantities.equals(training.quantities) : training.quantities != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (physicalTraining != null ? physicalTraining.hashCode() : 0);
        result = 31 * result + (physicalTrainingImagePath != null ? physicalTrainingImagePath.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (quantities != null ? quantities.hashCode() : 0);
        return result;
    }
}
