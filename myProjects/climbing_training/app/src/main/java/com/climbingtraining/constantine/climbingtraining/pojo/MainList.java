package com.climbingtraining.constantine.climbingtraining.pojo;

/**
 * Created by KonstantinSysoev on 28.04.15.
 */

public class MainList {

    private int logo;
    private String title;
    private String text;

    public MainList(int logo, String title, String text) {
        this.logo = logo;
        this.title = title;
        this.text = text;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
