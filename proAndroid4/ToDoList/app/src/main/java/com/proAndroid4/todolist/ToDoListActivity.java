package com.proAndroid4.todolist;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class ToDoListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Загружаем наше представление
        setContentView(R.layout.activity_to_do_list);
        
        // Получаем ссылки на элементы пользовательского интерфейса
        ListView mylistView = (ListView) findViewById(R.id.myListView);
        final EditText myEditText = (EditText) findViewById(R.id.myEditText); 
        
        // Создаем список для хранения задач
        final ArrayList<String> toDoItems = new ArrayList<String>();
        
        // Создаем ArrayAdapter, что бы привязать массив к ListView.
        final ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDoItems);
        
        // Привязываем массив к ListView.
        mylistView.setAdapter(arrayAdapter);
        
        myEditText.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER) || (keyCode == KeyEvent.KEYCODE_ENTER)) {
						toDoItems.add(0, myEditText.getText().toString());
						arrayAdapter.notifyDataSetChanged();
						myEditText.setText("");
						return true;
					}
				}
				return false;
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
