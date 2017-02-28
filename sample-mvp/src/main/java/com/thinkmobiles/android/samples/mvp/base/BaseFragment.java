package com.thinkmobiles.android.samples.mvp.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.thinkmobiles.android.samples.mvp.screens.NavigationActivity;

/**
 * Created by Lynx on 2/27/2017.
 */

public abstract class BaseFragment<T extends NavigationActivity> extends Fragment {

    protected T mActivity;

    private ProgressDialog progressDialog;

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try {
            mActivity = (T) context;
        } catch (ClassCastException e) {
            throw new RuntimeException("This fragment should have NavigationActivity instance");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
    }

    public void showProgress(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void dismissProgress() {
        if(progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
