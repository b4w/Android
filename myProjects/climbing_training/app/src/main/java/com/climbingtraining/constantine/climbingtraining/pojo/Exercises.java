package com.climbingtraining.constantine.climbingtraining.pojo;

/**
 * Created by KonstantinSysoev on 02.05.15.
 */
public class Exercises {

    private int idExercise;
    private int image;
    private String name;
    private String nameCategory;
    private String typeExercise;
    private String equipment;
    private float rating;
    private String comment;

    public Exercises() {
    }

    // конструктор если есть id упражнения из БД, что бы было удобно потом открывать
    public Exercises(int idExercise, int image, String name, String nameCategory, String typeExercise, String equipment, float rating, String comment) {
        this.idExercise = idExercise;
        this.image = image;
        this.name = name;
        this.nameCategory = nameCategory;
        this.typeExercise = typeExercise;
        this.equipment = equipment;
        this.rating = rating;
        this.comment = comment;
    }

    public Exercises(int image, String name, String nameCategory, String typeExercise, String equipment, float rating, String comment) {
        this.image = image;
        this.name = name;
        this.nameCategory = nameCategory;
        this.typeExercise = typeExercise;
        this.equipment = equipment;
        this.rating = rating;
        this.comment = comment;
    }

    public int getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(int idExercise) {
        this.idExercise = idExercise;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getTypeExercise() {
        return typeExercise;
    }

    public void setTypeExercise(String typeExercise) {
        this.typeExercise = typeExercise;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
