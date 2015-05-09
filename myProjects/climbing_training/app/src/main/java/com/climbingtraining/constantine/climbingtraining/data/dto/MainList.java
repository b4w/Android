package com.climbingtraining.constantine.climbingtraining.data.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by KonstantinSysoev on 29.04.15.
 */
@DatabaseTable(tableName = "main_list")
public class MainList implements ICommonEntities {

    @DatabaseField(generatedId = true, columnName = COLUMN_NAME_ID)
    private int id;

    @DatabaseField(columnName = COLUMN_NAME_LOGO)
    private int logo;

    @DatabaseField(columnName = COLUMN_NAME_TITLE)
    private String title;

    @DatabaseField(columnName = COLUMN_NAME_TEXT)
    private String text;

    public MainList() {
        // need for ormLite
    }

    public MainList(int logo, String title, String text) {
        this.logo = logo;
        this.title = title;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}