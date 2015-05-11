package com.climbingtraining.constantine.climbingtraining.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by KonstantinSysoev on 10.05.15.
 */
public enum PhysicalTraining {
    OFP("ОФП", "OFP"),
    SFP("СФП", "SFP"),
    OFP_SFP("ОФП/СФП", "OFP/SFP"),
    CARDIO("Кардио", "Cardio");

    private String name;
    private String englishName;

    PhysicalTraining(String name, String englishName) {
        this.name = name;
        this.englishName = englishName;
    }

    public static List<String> getNameByLocale(Locale locale) {
        List<String> result = new ArrayList<>();
        if (locale.equals(new Locale("ru"))) {
            for (PhysicalTraining item : PhysicalTraining.values()) {
                result.add(item.name);
            }
        } else {
            for (PhysicalTraining item : PhysicalTraining.values()) {
                result.add(item.englishName);
            }
        }
        return result;
    }

    public static PhysicalTraining getPhysicalTrainingByName(String name) {
        if (name.isEmpty()) {
            return null;
        }
        for (PhysicalTraining item : PhysicalTraining.values()) {
            if (item.name.equals(name) || item.englishName.equals(name)) {
                return item;
            }
        }
        return null;
    }
}
