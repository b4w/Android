package com.courseraAndroid101.javabook.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Surprise extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent();
        intent.setClass(this, NasaActivity.class);
        startActivity(intent);
    }
}
