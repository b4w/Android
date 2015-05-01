package com.climbingtraining.constantine.climbingtraining.data.dto;

/**
 * Created by KonstantinSysoev on 01.05.15.
 */
public interface ITrainingsDto {

    String COLUMN_NAME_ID = "_id";
    String COLUMN_NAME_ID_TRAINING = "id_training";
    String COLUMN_NAME_DATE = "date";
    String COLUMN_NAME_ICON = "icon";
    String COLUMN_NAME_EXERCISES = "exercises";
    String COLUMN_NAME_COMMENTS = "comments";
    String COLUMN_NAME_LIKE = "like";

    String DATABASE_NAME = "trainings_list";
    int DATABASE_VERSION = 1;
}
