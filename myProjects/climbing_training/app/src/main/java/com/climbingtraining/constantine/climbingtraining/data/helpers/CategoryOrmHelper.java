package com.climbingtraining.constantine.climbingtraining.data.helpers;

import android.content.Context;

/**
 * Created by KonstantinSysoev on 04.05.15.
 */
public class CategoryOrmHelper extends OrmHelper {

    private final static String TAG = CategoryOrmHelper.class.getSimpleName();

    /**
     * Конструктор для создания подключения к БД.
     *
     * @param context
     * @param databaseName
     * @param databaseVersion
     */
    public CategoryOrmHelper(Context context, String databaseName, int databaseVersion) {
        super(context, databaseName, databaseVersion);
    }

}
