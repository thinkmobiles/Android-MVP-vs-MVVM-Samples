package com.thinkmobiles.android.samples.mvp.screens.auth;

import android.text.TextUtils;

import com.thinkmobiles.android.samples.domain.models.Credential;
import com.thinkmobiles.android.samples.domain.modules.IAuthModule;
import com.thinkmobiles.android.samples.mvp.utils.Constants;

public class AuthPresenter implements AuthContract.AuthPresenter {

    private AuthContract.AuthView view;
    private IAuthModule authModule;

    public AuthPresenter(final AuthContract.AuthView view, final IAuthModule authModule) {
        this.view = view;
        this.authModule = authModule;

        view.setPresenter(this);
    }

    @Override
    public void signIn() {
        if(isCredentialsValid()) {
            view.showProgress("Sign in...");
            authModule.signIn(view.getEmail(), view.getPassword(), new IAuthModule.Callback() {
                @Override
                public void onComplete(boolean isSuccessful, String _msg) {
                    view.dismissProgress();
                    if(!TextUtils.isEmpty(_msg)) view.showMessage(_msg);
                    if(isSuccessful) {
                        authModule.setCredential(new Credential(view.getEmail(), view.getPassword()));
                    }
                }
            });
        }
    }

    @Override
    public void signUp() {
        if(isCredentialsValid()) {
            view.showProgress("Sign up...");
            authModule.register(view.getEmail(), view.getPassword(), new IAuthModule.Callback() {
                @Override
                public void onComplete(boolean isSuccessful, String _msg) {
                    view.dismissProgress();
                    if(!TextUtils.isEmpty(_msg)) view.showMessage(_msg);
                    if(isSuccessful) {
                        authModule.setCredential(new Credential(view.getEmail(), view.getPassword()));
                    }
                }
            });
        }
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        view = null;
        authModule = null;
    }

    private boolean isCredentialsValid() {
        String email = view.getEmail();
        String password = view.getPassword();

        Constants.ErrorCodes errorCodeLogin;
        Constants.ErrorCodes errorCodePassword;

        if(TextUtils.isEmpty(email)) {
            errorCodeLogin = Constants.ErrorCodes.FIELD_EMPTY;
        } else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorCodeLogin = Constants.ErrorCodes.FIELD_INVALID;
        } else {
            errorCodeLogin = Constants.ErrorCodes.OK;
        }

        if(TextUtils.isEmpty(password)) {
            errorCodePassword = Constants.ErrorCodes.FIELD_EMPTY;
        } else if(password.length() < 6 || password.length() > 15) {
            errorCodePassword = Constants.ErrorCodes.FIELD_INVALID;
        } else {
            errorCodePassword = Constants.ErrorCodes.OK;
        }

        view.displayErrorEmail(errorCodeLogin);
        view.displayErrorPassword(errorCodePassword);

        return errorCodeLogin == Constants.ErrorCodes.OK && errorCodePassword == Constants.ErrorCodes.OK;
    }
}
