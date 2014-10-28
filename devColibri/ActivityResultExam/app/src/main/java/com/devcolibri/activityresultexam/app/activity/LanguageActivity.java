package com.devcolibri.activityresultexam.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.devcolibri.activityresultexam.app.R;
import com.devcolibri.activityresultexam.app.util.Language;

public class LanguageActivity extends Activity {
    private static final String LANGUAGE = "language";

    private Button btnEnglish;
    private Button btnUkrainian;
    private Button btnRussian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.languege_layout);

        btnEnglish = (Button) findViewById(R.id.btnEnglish);
        btnRussian = (Button) findViewById(R.id.btnRussian);
        btnUkrainian = (Button) findViewById(R.id.btnUkrainian);

    }

    public void onSelectedLanguage(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnEnglish:
                intent = new Intent();
                intent.putExtra(LANGUAGE, Language.ENGLISH.getLanguage());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btnUkrainian:
                intent = new Intent();
                intent.putExtra(LANGUAGE, Language.UKRAINIAN.getLanguage());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btnRussian:
                intent = new Intent();
                intent.putExtra(LANGUAGE, Language.RUSSIAN.getLanguage());
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
