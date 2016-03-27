package com.devcolibri.mymenuexam.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    public void onAbout(MenuItem menuItem) {
        Toast.makeText(getApplicationContext(), "Вы выбрали об авторе", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Toast.makeText(getApplicationContext(), "Вы выбрали настройки", Toast.LENGTH_SHORT).show();
                break;
//            case R.id.about:
//                Toast.makeText(getApplicationContext(), "Вы выбрали об авторе", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.site:
                Toast.makeText(getApplicationContext(), "Вы выбрали переход на сайт", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
