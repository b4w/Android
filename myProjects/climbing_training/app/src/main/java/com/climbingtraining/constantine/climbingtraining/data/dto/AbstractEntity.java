package com.climbingtraining.constantine.climbingtraining.data.dto;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by KonstantinSysoev on 04.05.15.
 */
public abstract class AbstractEntity implements ICommonEntities {

    @DatabaseField(generatedId = true, columnName = COLUMN_NAME_ID)
    private int id;

    @DatabaseField(columnName = COLUMN_NAME_NAME)
    private String name;

    @DatabaseField(columnName = COLUMN_NAME_IMAGE)
    private String imagePath;

    @DatabaseField(columnName = COLUMN_NAME_DESCRIPTION)
    private String description;

    @DatabaseField(columnName = COLUMN_NAME_COMMENT)
    private String comment;

    public AbstractEntity() {
//        need for ormlite
    }

    public AbstractEntity(String name, String imagePath, String description, String comment) {
        this.name = name;
        this.imagePath = imagePath;
        this.description = description;
        this.comment = comment;
    }

    public AbstractEntity(int id, String name, String imagePath, String description, String comment) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.description = description;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
}
