package com.devcolibri.dynamicfragmentexample.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.devcolibri.dynamicfragmentexample.app.R;

public class TwoFragment extends Fragment {

    public static final String TAG = "TwoFragmentTag";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.two_fragment, null);
    }
}
