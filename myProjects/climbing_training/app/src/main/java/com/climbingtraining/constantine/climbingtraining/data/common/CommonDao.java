package com.climbingtraining.constantine.climbingtraining.data.common;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by KonstantinSysoev on 29.04.15.
 */
public class CommonDao<T, ID> extends BaseDaoImpl<T, ID> {

    public CommonDao(ConnectionSource connectionSource, Class<T> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    /**
     * Создание или обновление коллекции сущностей.
     *
     * @param objects
     * @throws SQLException
     */
    public void createOrUpdate(final Collection<T> objects) throws SQLException {
        callBatchTasks(new Callable<Void>() {
            public Void call() throws Exception {
                for (T item : objects) {
                    createOrUpdate(item);
                }
                return null;
            }
        });
    }

    /**
     * Сохранение в БД множество сущностей.
     *
     * @param objects
     * @return
     * @throws SQLException
     */
    public int createBatch(final Collection<T> objects) throws SQLException {
        callBatchTasks(new Callable<Void>() {
            public Void call() throws Exception {
                for (T item : objects) {
                    create(item);
                }
                return null;
            }
        });
        return 0;
    }

    /**
     * Сохранение в БД сущности-родителя с вложенным списком сущностей-ребенка.
     *
     * @param parentEntity      - родительская сущность.
     * @param childrenEntities  - лист сущностей-ребенка.
     * @param childrenEntityDao - dao сущности-ребенка.
     * @throws SQLException
     */
    public void create(T parentEntity, List<T> childrenEntities, CommonDao<T, ID> childrenEntityDao) throws SQLException {
        create(parentEntity);
        saveChildrenEntities(childrenEntities, childrenEntityDao);
    }

    /**
     * Сохранение в БД списка сущностей.
     *
     * @param childrenEntities  - список сущностей
     * @param childrenEntityDao - dao сущности.
     * @throws SQLException
     */
    private void saveChildrenEntities(List<T> childrenEntities, CommonDao<T, ID> childrenEntityDao) throws SQLException {
        for (T entity : childrenEntities) {
            childrenEntityDao.createOrUpdate(entity);
        }
    }
}
