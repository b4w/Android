package com.climbingtraining.constantine.climbingtraining.pojo;

import java.util.Date;
import java.util.List;

/**
 * Created by KonstantinSysoev on 01.05.15.
 */
public class Trainings {

    private int idTraining;
    private Date date;
    private int icon;
    private List<String> exercises;
    private String comments;
    private boolean like;

    public Trainings() {
    }

    public Trainings(int idTraining, Date date, int icon, List<String> exercises, String comments, boolean like) {
        this.idTraining = idTraining;
        this.date = date;
        this.icon = icon;
        this.exercises = exercises;
        this.comments = comments;
        this.like = like;
    }

    public int getIdTraining() {
        return idTraining;
    }

    public void setIdTraining(int idTraining) {
        this.idTraining = idTraining;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public List<String> getExercises() {
        return exercises;
    }

    public void setExercises(List<String> exercises) {
        this.exercises = exercises;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}
