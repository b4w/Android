package com.climbingtraining.constantine.climbingtraining.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.activity.ExercisesActivity;
import com.climbingtraining.constantine.climbingtraining.data.dto.Category;
import com.climbingtraining.constantine.climbingtraining.data.dto.Equipment;
import com.climbingtraining.constantine.climbingtraining.data.dto.Exercise;
import com.climbingtraining.constantine.climbingtraining.data.dto.TypeExercise;
import com.climbingtraining.constantine.climbingtraining.pojo.ExerciseParcelable;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KonstantinSysoev on 25.05.15.
 */
public class ExerciseFragment extends Fragment {

    private final String TAG = ExerciseFragment.class.getSimpleName();
    private final int EXERCISE_GALLERY_REQUEST = 1;

    private ImageView image;
    private Spinner spinnerCategory;
    private Spinner spinnerTypeExercise;
    private Spinner spinnerEquipment;
    private EditText name;
    private EditText description;
    private EditText comment;
    private Button saveBtn;
    private Button cancelBtn;

    private Category choseCategory;
    private Equipment choseEquipment;
    private TypeExercise choseTypeExercise;

    private ExerciseParcelable exerciseParcelable;
    private IExerciseFragmentCallBack callBack;

    private List<Category> categories;
    private List<Equipment> equipments;
    private List<TypeExercise> typeExercises;

    public static ExerciseFragment newInstance() {
        return new ExerciseFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callBack = (IExerciseFragmentCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement IExerciseFragmentCallBack.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initXmlFields();
        initListeners();
        initFields();
        initSpinners();
        callBack.showHideOptionsMenu(true);
    }

    private void initXmlFields() {
        Log.d(TAG, "initXmlFields() start");
        image = (ImageView) getActivity().findViewById(R.id.fragment_exercise_image);
        spinnerCategory = (Spinner) getActivity().findViewById(R.id.fragment_exercise_spinner_category);
        spinnerTypeExercise = (Spinner) getActivity().findViewById(R.id.fragment_exercise_spinner_type_exercise);
        spinnerEquipment = (Spinner) getActivity().findViewById(R.id.fragment_exercise_spinner_equipment);
        name = (EditText) getActivity().findViewById(R.id.fragment_exercise_name_edit_text);
        description = (EditText) getActivity().findViewById(R.id.fragment_exercise_description_edit_text);
        comment = (EditText) getActivity().findViewById(R.id.fragment_exercise_comment_edit_text);
        saveBtn = (Button) getActivity().findViewById(R.id.fragment_exercise_save_btn);
        cancelBtn = (Button) getActivity().findViewById(R.id.fragment_exercise_cancel_btn);
        Log.d(TAG, "initXmlFields() done");
    }

    private void initListeners() {
        Log.d(TAG, "initListeners() start");
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.saveExercise(loadExercise(), image.getDrawable());
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.cancel();
            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choseCategory = categories.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTypeExercise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choseTypeExercise = typeExercises.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerEquipment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choseEquipment = equipments.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, EXERCISE_GALLERY_REQUEST);
            }
        });
        Log.d(TAG, "initListeners() done");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap galleryPic = null;
        switch (requestCode) {
            case EXERCISE_GALLERY_REQUEST:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        galleryPic = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    image.setImageBitmap(galleryPic);
                }
        }
    }

    private void initFields() {
        Log.d(TAG, "initFields() start");
        exerciseParcelable = getArguments().getParcelable(ExercisesActivity.EXERCISE_PARCELABLE);

        equipments = exerciseParcelable.getEquipments();
        categories = exerciseParcelable.getCategories();
        typeExercises = exerciseParcelable.getTypeExercises();

        if (exerciseParcelable.getName() != null) {
            if (exerciseParcelable.getImageNameAndPath() != null && !exerciseParcelable.getImageNameAndPath().isEmpty()) {
                File file = new File(exerciseParcelable.getImageNameAndPath());
                Picasso.with(getActivity()).load(file).into(image);
            }
            name.setText(exerciseParcelable.getName());
            description.setText(exerciseParcelable.getDescription());
            comment.setText(exerciseParcelable.getComment());
            initSpinnerValues();
        }
        Log.d(TAG, "initFields() start");
    }

    private void initSpinnerValues() {
        Log.d(TAG, "initSpinnerValues() start");
//        TODO переделать на  array.getPosition()
        for (int i = 0; i < equipments.size(); i++) {
            if (equipments.get(i).getId() == exerciseParcelable.getEquipmentId()) {
                spinnerEquipment.setSelection(i);
            }
        }
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId() == exerciseParcelable.getCategoryId()) {
                spinnerCategory.setSelection(i);
            }
        }
        for (int i = 0; i < typeExercises.size(); i++) {
            if (typeExercises.get(i).getId() == exerciseParcelable.getTypeExerciseId()) {
                spinnerTypeExercise.setSelection(i);
            }
        }
        Log.d(TAG, "initSpinnerValues() done");
    }

    private void initSpinners() {
        Log.d(TAG, "initSpinners() start");
//        TODO сделать свой адаптер
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getNamesCategories());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(arrayAdapter);
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getNamesEquipments());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEquipment.setAdapter(arrayAdapter);
        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getNamesTypeExercise());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeExercise.setAdapter(arrayAdapter);
        Log.d(TAG, "initSpinners() done");
    }

    private Exercise loadExercise() {
        Log.d(TAG, "loadExercise() start");
        Exercise exercise = new Exercise();
        exercise.setId(exerciseParcelable.getName() != null ? exerciseParcelable.getExerciseId() : null);
        exercise.setName(name.getText().toString());
        exercise.setDescription(description.getText().toString());
        exercise.setComment(description.getText().toString());
        exercise.setEquipment(choseEquipment);
        exercise.setCategory(choseCategory);
        exercise.setTypeExercise(choseTypeExercise);
        Log.d(TAG, "loadExercise() done");
        return exercise;
    }

    private List<String> getNamesCategories() {
        Log.d(TAG, "getNamesCategories() start");
        List<String> result = new ArrayList<>();
        for (Category item : categories) {
            if (item.getName() != null) {
                result.add(item.getName());
            }
        }
        Log.d(TAG, "getNamesCategories() done");
        return result;
    }

    private List<String> getNamesEquipments() {
        Log.d(TAG, "getNamesEquipments() start");
        List<String> result = new ArrayList<>();
        for (Equipment item : equipments) {
            if (item.getName() != null) {
                result.add(item.getName());
            }
        }
        Log.d(TAG, "getNamesEquipments() done");
        return result;
    }

    private List<String> getNamesTypeExercise() {
        Log.d(TAG, "getNamesTypeExercise() start");
        List<String> result = new ArrayList<>();
        for (TypeExercise item : typeExercises) {
            if (item.getName() != null) {
                result.add(item.getName());
            }
        }
        Log.d(TAG, "getNamesTypeExercise() done");
        return result;
    }

    /**
     * Интерфейс для передачи данных в ExerciseActivity.
     */
    public interface IExerciseFragmentCallBack {
        // Передаем drawable, что бы после успешного сохранения сущности в БД, сохранять изображение.
        void saveExercise(Exercise exercise, Drawable drawable);

        void cancel();

        void showHideOptionsMenu(boolean entitiesIsChecked);
    }
}
