package com.climbingtraining.constantine.climbingtraining.data.dto;

/**
 * Created by KonstantinSysoev on 04.05.15.
 */
public interface ICommonEntities {

//  --  FIELDS --
    String COLUMN_NAME_ID = "_id";
    String COLUMN_NAME_NAME = "name";
    String COLUMN_NAME_IMAGE = "image";
    String COLUMN_NAME_DESCRIPTION = "description";
    String COLUMN_NAME_COMMENT = "comment";
    String COLUMN_NAME_LOGO = "logo";
    String COLUMN_NAME_TITLE = "title";
    String COLUMN_NAME_TEXT = "text";

//  --  DB NAMES --
    String CATEGORIES_DATABASE_NAME = "categories";
    int CATEGORIES_DATABASE_VERSION = 1;

    String MAIN_LIST_DATABASE_NAME = "main_list";
    int MAIN_LIST_DATABASE_VERSION = 1;

    int SHEET_ITEM_TRAINING = 0;
    int SHEET_ITEM_EXERCISE = 1;
    int SHEET_ITEM_GRAPH = 2;
}
