package com.tigerlight.registration.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tigerlight.R;
import com.tigerlight.home.BaseFragment;

/**
 * Created by indianic on 21/10/16.
 */
public class ForgotFragment extends BaseFragment {


    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        return view;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void trackScreen() {

    }

    @Override
    public void initActionBar() {

    }
}
