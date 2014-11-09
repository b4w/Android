package com.devcolibri.asynctaskgenerics.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;


public class MainActivity extends Activity {

    MyAsyncTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        task = new MyAsyncTask();
    }

    public void onShowMessage(View view) throws ExecutionException, InterruptedException {
        task.execute();
        String text = task.get();
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    class MyAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            return "Hello";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
