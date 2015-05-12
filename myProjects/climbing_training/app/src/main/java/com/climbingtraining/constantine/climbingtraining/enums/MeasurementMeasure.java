package com.climbingtraining.constantine.climbingtraining.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by KonstantinSysoev on 10.05.15.
 */
public enum MeasurementMeasure {
    KILOGRAM ("Килограм", "Kilogram"),
    KILOMETER ("Километр", "Kilometer"),
    METER ("Метр", "Meter"),
    CENTIMETER ("Сантиметр", "Centimeter");

    private String name;
    private String englishName;

    MeasurementMeasure(String name, String englishName) {
        this.name = name;
        this.englishName = englishName;
    }

    public static List<String> getNameByLocale(Locale locale) {
        List<String> result = new ArrayList<>();
        if (locale.equals(new Locale("ru"))) {
            for (MeasurementMeasure item : MeasurementMeasure.values()) {
                result.add(item.name);
            }
        } else {
            for (MeasurementMeasure item : MeasurementMeasure.values()) {
                result.add(item.englishName);
            }
        }
        return result;
    }

    public static MeasurementMeasure getMeasurementMeasureByName(String name) {
        if (name.isEmpty()) {
            return null;
        }
        for (MeasurementMeasure item : MeasurementMeasure.values()) {
            if (item.name.equals(name) || item.englishName.equals(name)) {
                return item;
            }
        }
        return null;
    }
}
