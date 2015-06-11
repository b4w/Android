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

//  --  DB NAME --
    String CLIMBING_TRAINING_DB_NAME = "climbing_training_db";
    int CLIMBING_TRAINING_DB_VERSION = 1;

//  --  MAIN LIST ITEMS --
    int SHEET_ITEM_TRAINING = 0;
    int SHEET_ITEM_EXERCISE = 1;
    int SHEET_CATEGORY = 2;
}
