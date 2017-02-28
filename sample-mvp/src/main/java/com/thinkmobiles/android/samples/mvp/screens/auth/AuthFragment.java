package com.thinkmobiles.android.samples.mvp.screens.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.thinkmobiles.android.samples.domain.ObjectGraph;
import com.thinkmobiles.android.samples.mvp.screens.NavigationActivity;
import com.thinkmobiles.android.samples.mvp.R;
import com.thinkmobiles.android.samples.mvp.base.BaseFragment;
import com.thinkmobiles.android.samples.mvp.utils.Constants;

/**
 * Created by Lynx on 2/27/2017.
 */

public class AuthFragment extends BaseFragment<NavigationActivity> implements AuthContract.AuthView {

    private AuthContract.AuthPresenter presenter;

    private TextInputLayout tilEmail_FA;
    private TextInputLayout tilPassword_FA;
    private EditText etEmail_FA;
    private EditText etPassword_FA;
    private TextView btnSignUp_FA;
    private TextView btnSignIn_FA;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth, container, false);
        tilEmail_FA = (TextInputLayout) view.findViewById(R.id.tilEmail_FA);
        tilPassword_FA = (TextInputLayout) view.findViewById(R.id.tilPassword_FA);
        etEmail_FA = (EditText) view.findViewById(R.id.etEmail_FA);
        etPassword_FA = (EditText) view.findViewById(R.id.etPassword_FA);
        btnSignUp_FA = (TextView) view.findViewById(R.id.btnSignUp_FA);
        btnSignIn_FA = (TextView) view.findViewById(R.id.btnSignIn_FA);

        btnSignIn_FA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.signIn();
            }
        });
        btnSignUp_FA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.signUp();
            }
        });
        mActivity.setToolbarTitle("Auth");
        return view;
    }

    @Override
    public String getEmail() {
        return etEmail_FA.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return etPassword_FA.getText().toString().trim();
    }

    @Override
    public void displayErrorEmail(Constants.ErrorCodes errorCode) {
        switch (errorCode) {
            case FIELD_EMPTY:
                tilEmail_FA.setErrorEnabled(true);
                tilEmail_FA.setError("Field is empty");
                break;
            case FIELD_INVALID:
                tilEmail_FA.setErrorEnabled(true);
                tilEmail_FA.setError("Email is invalid");
                break;
            case OK:
                tilEmail_FA.setErrorEnabled(false);
                tilEmail_FA.setError(null);
                break;
        }
    }

    @Override
    public void displayErrorPassword(Constants.ErrorCodes errorCode) {
        switch (errorCode) {
            case FIELD_EMPTY:
                tilPassword_FA.setErrorEnabled(true);
                tilPassword_FA.setError("Field is empty");
                break;
            case FIELD_INVALID:
                tilPassword_FA.setErrorEnabled(true);
                tilPassword_FA.setError("Password should be 6-15 characters long");
                break;
            case OK:
                tilPassword_FA.setErrorEnabled(false);
                tilPassword_FA.setError(null);
                break;
        }
    }

    @Override
    public void initPresenter() {
        new AuthPresenter(this, ObjectGraph.getInstance().getAuthModule());
    }

    @Override
    public void setPresenter(AuthContract.AuthPresenter presenter) {
        this.presenter = presenter;
    }
}
