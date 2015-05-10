package com.climbingtraining.constantine.climbingtraining.data.dto;

/**
 * Created by KonstantinSysoev on 04.05.15.
 */
public interface ICommonEntities {

//  --  FIELDS --
    String COLUMN_NAME_ID = "_id";
    String COLUMN_NAME_NAME = "name";
    String COLUMN_NAME_IMAGE = "image_path";
    String COLUMN_NAME_DESCRIPTION = "description";
    String COLUMN_NAME_COMMENT = "comment";
    String COLUMN_NAME_LOGO = "logo";
    String COLUMN_NAME_TITLE = "title";
    String COLUMN_NAME_TEXT = "text";
    String COLUMN_NAME_CATEGORY = "category";
    String COLUMN_NAME_EQUIPMENT = "equipment";
    String COLUMN_NAME_TYPE_EXERCISE = "type_exercise";

//  --  DB NAMES --
    String CATEGORIES_DATABASE_NAME = "categories";
    int CATEGORIES_DATABASE_VERSION = 2;

    String EQUIPMENTS_DATABASE_NAME = "equipments";
    int EQUIPMENTS_DATABASE_VERSION = 2;

    String TYPE_EXERCISES_DATABASE_NAME = "type_exercises";
    int TYPE_EXERCISES_DATABASE_VERSION = 2;

    String EXERCISES_DATABASE_NAME = "exercises";
    int EXERCISE_DATABASE_VERSION = 2;

    String MAIN_LIST_DATABASE_NAME = "main_list";
    int MAIN_LIST_DATABASE_VERSION = 1;

    int SHEET_ITEM_TRAINING = 0;
    int SHEET_ITEM_EXERCISE = 1;
    int SHEET_ITEM_GRAPH = 2;
    int SHEET_CATEGORY = 3;
}
