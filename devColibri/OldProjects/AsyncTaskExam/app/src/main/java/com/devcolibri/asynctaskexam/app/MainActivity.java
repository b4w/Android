package com.devcolibri.asynctaskexam.app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ProgressBar progressBar;
    private TextView txtStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        txtStates = (TextView) findViewById(R.id.txtState);
    }

    public void onProgressButton(View view) {
        new MyProgressBarAsyncTask().execute();
    }

    class MyProgressBarAsyncTask extends AsyncTask<Void, Integer, Void> {

        private int progressBarValue = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Начало процесса", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(MainActivity.this, "Процесс окончен", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
            txtStates.setText(values[0] + " %");
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (progressBarValue < 100) {
                progressBarValue++;
                publishProgress(progressBarValue);
                SystemClock.sleep(200);
            }

            return null;
        }
    }
}
