package com.devcolibri.listviewexam.app.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.devcolibri.listviewexam.app.R;
import com.devcolibri.listviewexam.app.adapter.PhoneModelAdapter;
import com.devcolibri.listviewexam.app.pojo.PhoneModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        List<PhoneModel> items = initData();
//        старая стандартная реализация со строками, а не объектами PhoneModel
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, items);
        PhoneModelAdapter phoneModelAdapter = new PhoneModelAdapter(getApplicationContext(), items);
        listView.setAdapter(phoneModelAdapter);
    }

    private List<PhoneModel> initData() {
        List<PhoneModel> list = new ArrayList<PhoneModel>();
        list.add(new PhoneModel(1,"IPhone", 1000));
        list.add(new PhoneModel(2,"HTC", 2000));
        list.add(new PhoneModel(3,"Samsung", 3000));
        list.add(new PhoneModel(4,"LG", 4000));
        return list;
    }
}
