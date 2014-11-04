package com.climbtraining.app.pojo;

import java.util.Date;

public class Training {

    private int id;
    private Date date;
    private String nameTraining;
    private String comments;

    public Training(int id, Date date, String nameTraining, String comments) {
        this.id = id;
        this.date = date;
        this.nameTraining = nameTraining;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNameTraining() {
        return nameTraining;
    }

    public void setNameTraining(String nameTraining) {
        this.nameTraining = nameTraining;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
