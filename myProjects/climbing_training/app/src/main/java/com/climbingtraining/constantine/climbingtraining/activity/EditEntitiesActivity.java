package com.climbingtraining.constantine.climbingtraining.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.fragments.DescriptionFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.LoadImageFragment;
import com.climbingtraining.constantine.climbingtraining.fragments.OkCancelFragment;

/**
 * Created by KonstantinSysoev on 09.05.15.
 */
public class EditEntitiesActivity extends ActionBarActivity implements OkCancelFragment.IOkCancelFragmentCallBack{

    private Toolbar toolbar;

    private String imageNameAndPath;
    private String name;
    private String description;
    private String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_entities_layout);

        toolbar = (Toolbar) findViewById(R.id.graph_layout_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        imageNameAndPath = intent.getStringExtra(CategoryActivity.IMAGE_NAME_AND_PATH);
        name = intent.getStringExtra(CategoryActivity.NAME);
        description = intent.getStringExtra(CategoryActivity.DESCRIPTION);
        comment = intent.getStringExtra(CategoryActivity.COMMENT);

        loadFragments();
    }

    private void loadFragments() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        LoadImageFragment loadImageFragment = LoadImageFragment.newInstance();
        Bundle bundle = new Bundle();
//        TODO не забыть изменить на путь к файлу + сделать общий интерфейс для полей
        bundle.putString("imageManeAndPath", String.valueOf(imageNameAndPath));
        loadImageFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.edit_entities_container_one, loadImageFragment, LoadImageFragment.class.getSimpleName());

        DescriptionFragment descriptionFragment = DescriptionFragment.newInstance();
        bundle.putString("name", name);
        bundle.putString("description", description);
        bundle.putString("comment", comment);
        descriptionFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.edit_entities_container_two, descriptionFragment, DescriptionFragment.class.getSimpleName());

        OkCancelFragment okCancelFragment = OkCancelFragment.newInstance();
        fragmentTransaction.add(R.id.edit_entities_container_three, okCancelFragment, OkCancelFragment.class.getSimpleName());

        // добавляем в стек, что бы потом по кнопкам ok, cancel вернуться на view
        fragmentTransaction.addToBackStack(DescriptionFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    public void clickOkBtn() {

    }

    @Override
    public void clickCancelBnt() {
        Toast.makeText(this, getString(R.string.cancel), Toast.LENGTH_SHORT).show();
        getFragmentManager().popBackStack();
    }
}
