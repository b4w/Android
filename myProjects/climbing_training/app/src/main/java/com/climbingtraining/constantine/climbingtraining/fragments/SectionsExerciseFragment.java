package com.climbingtraining.constantine.climbingtraining.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.climbingtraining.constantine.climbingtraining.R;
import com.climbingtraining.constantine.climbingtraining.data.dto.Category;
import com.climbingtraining.constantine.climbingtraining.data.dto.Equipment;
import com.climbingtraining.constantine.climbingtraining.data.dto.TypeExercise;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KonstantinSysoev on 05.05.15.
 */
public class SectionsExerciseFragment extends Fragment {

    private Spinner spinnerCategory;
    private Spinner spinnerTypeExercise;
    private Spinner spinnerEquipment;

    private List<Category> categories;
    private List<Equipment> equipments;
    private List<TypeExercise> typeExercise;

    private Category choseCategory;
    private Equipment choseEquipment;
    private TypeExercise choseTypeExercise;

    public static SectionsExerciseFragment newInstance() {
        SectionsExerciseFragment fragment = new SectionsExerciseFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sections_exercise, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        spinnerCategory = (Spinner) getActivity().findViewById(R.id.fragment_sections_spinner_category);
        spinnerTypeExercise = (Spinner) getActivity().findViewById(R.id.fragment_sections_spinner_type_exercise);
        spinnerEquipment = (Spinner) getActivity().findViewById(R.id.fragment_sections_spinner_equipment);
        updateData();

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
                choseTypeExercise = typeExercise.get(position);
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
    }

    private void updateData() {
//        TODO сделать свой адаптер
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, namesCategories());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(arrayAdapter);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, namesEquipments());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEquipment.setAdapter(arrayAdapter);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, namesTypeExercise());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeExercise.setAdapter(arrayAdapter);
    }

    private List<String> namesCategories() {
        List<String> result = new ArrayList<>();
        for (Category item : categories) {
            if (item.getName() != null) {
                result.add(item.getName());
            }
        }
        return result;
    }

    private List<String> namesEquipments() {
        List<String> result = new ArrayList<>();
        for (Equipment item : equipments) {
            if (item.getName() != null) {
                result.add(item.getName());
            }
        }
        return result;
    }

    private List<String> namesTypeExercise() {
        List<String> result = new ArrayList<>();
        for (TypeExercise item : typeExercise) {
            if (item.getName() != null) {
                result.add(item.getName());
            }
        }
        return result;
    }

//    GET & SET

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public void setTypeExercise(List<TypeExercise> typeExercise) {
        this.typeExercise = typeExercise;
    }

    public Category getChoseCategory() {
        return choseCategory;
    }

    public Equipment getChoseEquipment() {
        return choseEquipment;
    }

    public TypeExercise getChoseTypeExercise() {
        return choseTypeExercise;
    }
}
