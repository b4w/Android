package com.climbtraining.app.pojo.exercise;

public class Category {

    private int id;
    private String name;
    private String description;
    private int image;
    private int icon;
    private String comments;

    public Category() {
    }

    public Category(int id, String name, String description, int image, int icon, String comments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.icon = icon;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
