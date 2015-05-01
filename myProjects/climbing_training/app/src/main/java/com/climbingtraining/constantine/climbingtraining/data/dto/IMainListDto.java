package com.climbingtraining.constantine.climbingtraining.data.dto;

/**
 * Created by KonstantinSysoev on 29.04.15.
 */
public interface IMainListDto {

    String COLUMN_NAME_ID = "_id";
    String COLUMN_NAME_LOGO = "logo";
    String COLUMN_NAME_TITLE = "title";
    String COLUMN_NAME_TEXT = "text";

    String DATABASE_NAME = "main_list";
    int DATABASE_VERSION = 1;

    int SHEET_ITEM_TRAINING = 0;
    int SHEET_ITEM_EXERCISE = 1;
    int SHEET_ITEM_GRAPH = 2;
}
