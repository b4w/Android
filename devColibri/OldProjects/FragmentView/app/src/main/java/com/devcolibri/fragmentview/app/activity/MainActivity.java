package com.devcolibri.fragmentview.app.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.devcolibri.fragmentview.app.R;
import com.devcolibri.fragmentview.app.fragment.LastFragment;

public class MainActivity extends FragmentActivity {

    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        manager = getSupportFragmentManager();
        initFragmentLast();
    }

    private void initFragmentLast() {
        transaction = manager.beginTransaction();
        transaction.add(R.id.container, new LastFragment());
        transaction.commit();
    }

//    public void onClickButtonFragment(View view) {
//        EditText editText = (EditText) findViewById(R.id.text);
//        TextView textView = (TextView) findViewById(R.id.textView);
//        textView.setText(editText.getText().toString());
//    }
}
