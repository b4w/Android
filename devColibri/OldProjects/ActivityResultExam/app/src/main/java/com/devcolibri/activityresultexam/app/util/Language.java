package com.devcolibri.activityresultexam.app.util;

public enum Language {

    ENGLISH("Английский"),
    RUSSIAN("Русский"),
    UKRAINIAN("Украинский");

    private String language;

    Language(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
