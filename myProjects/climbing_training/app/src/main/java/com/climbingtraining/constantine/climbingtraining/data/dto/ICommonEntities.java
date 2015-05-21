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
    String COLUMN_NAME_NUMBER_APPROACHES = "number_approaches";
    String COLUMN_NAME_NUMBER_TIME_APPROACH = "number_time_approach";
    String COLUMN_NAME_ADDITIONAL_WEIGHT = "additional_weight";
    String COLUMN_NAME_MEASUREMENT_MEASURE = "measurement_measure";
    String COLUMN_NAME_DISTANCE = "distance";
    String COLUMN_NAME_TIME_BEGIN = "time_begin";
    String COLUMN_NAME_TIME_END = "time_end";
    String COLUMN_NAME_DATE = "date";
    String COLUMN_NAME_PHYSICAL_TRAINING = "physical_training";
    String COLUMN_NAME_PHYSICAL_TRAINING_IMAGE_PATH = "physical_training_image_path";

//  --  DB NAMES --
    String CATEGORIES_DATABASE_NAME = "categories";
    int CATEGORIES_DATABASE_VERSION = 3;

    String EQUIPMENTS_DATABASE_NAME = "equipments";
    int EQUIPMENTS_DATABASE_VERSION = 3;

    String TYPE_EXERCISES_DATABASE_NAME = "type_exercises";
    int TYPE_EXERCISES_DATABASE_VERSION = 3;

    String EXERCISES_DATABASE_NAME = "exercises";
    int EXERCISE_DATABASE_VERSION = 3;

    String TRAINING_DATABASE_NAME = "trainings";
    int TRAINING_DATABASE_VERSION = 3;

    String ACCOUNTING_QUANTITY_DATABASE_NAME = "accounting_quantities";
    int ACCOUNTING_QUANTITY_DATABASE_VERSION = 3;

    String MAIN_LIST_DATABASE_NAME = "main_list";
    int MAIN_LIST_DATABASE_VERSION = 3;

    int SHEET_ITEM_TRAINING = 0;
    int SHEET_ITEM_EXERCISE = 1;
    int SHEET_CATEGORY = 2;
}
