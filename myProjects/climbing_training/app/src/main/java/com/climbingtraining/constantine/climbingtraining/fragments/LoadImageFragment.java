package com.climbingtraining.constantine.climbingtraining.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.climbingtraining.constantine.climbingtraining.R;

/**
 * Created by KonstantinSysoev on 03.05.15.
 */
public class LoadImageFragment extends Fragment {

    public static LoadImageFragment newInstance() {
        LoadImageFragment fragment = new LoadImageFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_load_image, container, false);
        return view;
    }
}
