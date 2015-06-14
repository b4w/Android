package com.climbingtraining.constantine.climbingtraining.fragments.CategoriesFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.climbingtraining.constantine.climbingtraining.data.dto.AbstractEntity;
import com.climbingtraining.constantine.climbingtraining.data.dto.ICommonEntities;
import com.climbingtraining.constantine.climbingtraining.data.helpers.OrmHelper;
import com.climbingtraining.constantine.climbingtraining.utils.EntitiesForEditing;

import java.util.Iterator;
import java.util.List;

/**
 * Created by KonstantinSysoev on 14.06.15.
 */
abstract public class AbstractCategoriesListFragment extends Fragment {

    private final static String TAG = AbstractCategoriesListFragment.class.getSimpleName();

    protected final static Integer CATEGORIES_ORM_LOADER_ID = 1;
    protected final static Integer EQUIPMENT_ORM_LOADER_ID = 2;
    protected final static Integer TYPE_EXERCISE_ORM_LOADER_ID = 3;

    private List<AbstractEntity> entitiesForEditing;
    private EntitiesForEditing instanceEntitiesForEditing;

    private OrmHelper ormHelper;

    @Override
    public void onDetach() {
        super.onDetach();
        // закрываем все подключения к БД.
        if (ormHelper != null)
            ormHelper.close();
        entitiesForEditing = null;
        getInstanceEntitiesForEditing().getEntitiesForEditing().clear();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initXmlFields();
        initListeners();
        updateEntities();
        initOrmCursorLoader();
    }


    abstract protected void initXmlFields();

    abstract protected void initListeners();

    abstract protected void updateEntities();

    abstract protected void initOrmCursorLoader();


    public static AbstractCategoriesListFragment newInstance(int position) {
        if (position == 0) {
            return CategoriesFragment.newInstance();
        } else if (position == 1) {
            return EquipmentsFragment.newInstance();
        } else {
            return TypesExercisesFragment.newInstance();
        }
    }

    public void setOnItemLongClickListener(View view) {
        // TODO: перенести общие методы из фрагментов категорий
    }

    /**
     * Обновление списка выбранных категорий для дальнейшего редактирования.
     *
     * @param isChecked - выбрана или не выбрана категория.
     * @param entity    - сущность категории.
     */
    protected void updateEntitiesForEditing(boolean isChecked, AbstractEntity entity) {
        Log.d(TAG, "updateEntitiesForEditing() start");
        if (isChecked) {
            // если елемент выбран - добавляем в список
            getEntitiesForEditing().add(entity);
        } else {
            // если елемент не выбран - удаляем из списка
            Iterator iterator = getEntitiesForEditing().iterator();
            while (iterator.hasNext()) {
                if (iterator.next().equals(entity)) {
                    iterator.remove();
                }
            }
        }
        Log.d(TAG, "updateEntitiesForEditing() done");
    }

    // -- GET & SET ---

    public OrmHelper getOrmHelper() {
        if (ormHelper == null) {
            ormHelper = new OrmHelper(getActivity(), ICommonEntities.CLIMBING_TRAINING_DB_NAME,
                    ICommonEntities.CLIMBING_TRAINING_DB_VERSION);
        }
        return ormHelper;
    }

    public List<AbstractEntity> getEntitiesForEditing() {
        if (entitiesForEditing == null) {
            entitiesForEditing = getInstanceEntitiesForEditing().getEntitiesForEditing();
        }
        return entitiesForEditing;
    }

    public EntitiesForEditing getInstanceEntitiesForEditing() {
        if (instanceEntitiesForEditing == null) {
            instanceEntitiesForEditing = EntitiesForEditing.getInstance();
        }
        return instanceEntitiesForEditing;
    }
}
