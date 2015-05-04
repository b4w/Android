package com.climbingtraining.constantine.climbingtraining.data.util;

import android.os.AsyncTask;

import com.climbingtraining.constantine.climbingtraining.data.dto.Category;

/**
 * Created by KonstantinSysoev on 04.05.15.
 */
public class AsyncSaveDB {

    private final static String TAG = AsyncSaveDB.class.getSimpleName();

    private AsyncTaskSaveCategory asyncTaskSaveCategory;

    public void saveCategoryToDb(Category categoryDto) {
        asyncTaskSaveCategory = new AsyncTaskSaveCategory();
        asyncTaskSaveCategory.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, categoryDto);
    }

    private class AsyncTaskSaveCategory extends AsyncTask<Category, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Override
        protected Void doInBackground(Category... params) {
            return null;
        }
    }
}
