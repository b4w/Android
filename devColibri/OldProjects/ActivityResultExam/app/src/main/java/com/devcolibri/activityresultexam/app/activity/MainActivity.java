package com.devcolibri.activityresultexam.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.devcolibri.activityresultexam.app.R;
import com.devcolibri.activityresultexam.app.util.RequestCode;

public class MainActivity extends Activity {

    private TextView txtName;
    private TextView txtLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        txtName = (TextView) findViewById(R.id.txtName);
        txtLanguage = (TextView) findViewById(R.id.txtLanguage);
    }

    public void onShow(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnPresented:
                intent = new Intent(this, PresentedActivity.class);
                startActivityForResult(intent, RequestCode.REQUEST_CODE_PRESENTED);
                break;
            case R.id.btnLanguage:
                intent = new Intent(this, LanguageActivity.class);
                startActivityForResult(intent, RequestCode.REQUEST_CODE_LANGUAGE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCode.REQUEST_CODE_PRESENTED :
                    String name = data.getStringExtra("name");
                    txtName.setText("Рад знакомству: " + name);
                    break;
                case RequestCode.REQUEST_CODE_LANGUAGE :
                    String language = data.getStringExtra("language");
                    txtLanguage.setText("Ваш язык: " + language);
                    break;
            }
        } else {
            Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT).show();
        }
    }
}
