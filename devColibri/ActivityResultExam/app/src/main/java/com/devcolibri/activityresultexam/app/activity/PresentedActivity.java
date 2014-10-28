package com.devcolibri.activityresultexam.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.devcolibri.activityresultexam.app.R;

public class PresentedActivity extends Activity {

    private EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presented_layout);
        editName = (EditText) findViewById(R.id.editName);
    }

    public void onPresented(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name", editName.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
