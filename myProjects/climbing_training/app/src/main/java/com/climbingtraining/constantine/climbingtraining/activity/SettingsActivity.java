package com.climbingtraining.constantine.climbingtraining.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.climbingtraining.constantine.climbingtraining.R;

/**
 * Created by KonstantinSysoev on 17.05.15.
 */
public class SettingsActivity extends PreferenceActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        addPreferencesFromResource();

//        setContentView(R.layout.settings_layout);

//        toolbar = (Toolbar) findViewById(R.id.settings_layout_toolbar);

//        initToolbar();
    }

//    private void initToolbar() {
//        if (toolbar != null) {
//            setSupportActionBar(toolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setTitle(getString(R.string.settings));
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
