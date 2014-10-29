package com.devcolibri.alertdialogexam.app;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
    }

    public void showDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

//        builder.setTitle("Devcolibri.com");
//        builder.setMessage("Привет мир!");

        builder.setTitle("Devcolibri.com")  // сокращенная версия
                .setMessage("Это информационное сообщение")
                .setCancelable(false)
                .setIcon(R.drawable.ic_launcher)
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Вы не согласны с нами", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Вы согласны с нами", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(getApplicationContext(), "Вы проигнорировали нас =(", Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
