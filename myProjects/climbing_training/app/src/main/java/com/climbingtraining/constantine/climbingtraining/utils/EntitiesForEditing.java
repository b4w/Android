package com.climbingtraining.constantine.climbingtraining.utils;

import com.climbingtraining.constantine.climbingtraining.data.dto.AbstractEntity;
import com.climbingtraining.constantine.climbingtraining.data.dto.Exercise;
import com.climbingtraining.constantine.climbingtraining.data.dto.Training;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KonstantinSysoev on 13.06.15.
 */
public class EntitiesForEditing {

    private static EntitiesForEditing instance;

    // список сущностей (категория, оборудование, вид упражнения) для редактирования
    private List<AbstractEntity> entitiesForEditing;
    // список упражнений для редактирования
    private List<Exercise> exercisesForEditing;
    // списо тренировок для редактирования
    private List<Training> trainingsForEditing;

    public static EntitiesForEditing getInstance() {
        if (instance == null) {
            instance = new EntitiesForEditing();
        }
        return instance;
    }

    public List<AbstractEntity> getEntitiesForEditing() {
        if (entitiesForEditing == null) {
            entitiesForEditing = new ArrayList<>();
        }
        return entitiesForEditing;
    }

    public List<Exercise> getExercisesForEditing() {
        if (exercisesForEditing == null) {
            exercisesForEditing = new ArrayList<>();
        }
        return exercisesForEditing;
    }

    public List<Training> getTrainingsForEditing() {
        if (trainingsForEditing == null) {
            trainingsForEditing = new ArrayList<>();
        }
        return trainingsForEditing;
    }
}
