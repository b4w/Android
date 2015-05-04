package com.climbingtraining.constantine.climbingtraining.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.climbingtraining.constantine.climbingtraining.R;

/**
 * Created by KonstantinSysoev on 03.05.15.
 */
public class OkCancelFragment extends Fragment {

    private IOkCancelFragmentCallBack okCancelCallBack;
    private Button cancelBtn;
    private Button okBtn;

    public static OkCancelFragment newInstance() {
        OkCancelFragment fragment = new OkCancelFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ok_cancel, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cancelBtn = (Button) getActivity().findViewById(R.id.fragment_ok_cancel_cancel_btn);
        okBtn = (Button) getActivity().findViewById(R.id.fragment_ok_cancel_ok_btn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okCancelCallBack.clickCancelBnt();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okCancelCallBack.clickOkBtn();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            okCancelCallBack = (IOkCancelFragmentCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement IOkCancelFragmentCallBack.");
        }
    }

    public static interface IOkCancelFragmentCallBack {
        void clickOkBtn();

        void clickCancelBnt();
    }
}
