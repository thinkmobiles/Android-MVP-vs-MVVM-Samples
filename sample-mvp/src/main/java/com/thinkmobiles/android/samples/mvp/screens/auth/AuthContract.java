package com.thinkmobiles.android.samples.mvp.screens.auth;

import com.thinkmobiles.android.samples.mvp.base.BasePresenter;
import com.thinkmobiles.android.samples.mvp.base.BaseView;
import com.thinkmobiles.android.samples.mvp.utils.Constants;

/**
 * Created by Lynx on 2/27/2017.
 */

public interface AuthContract {
    interface AuthView extends BaseView<AuthPresenter> {
        String getEmail();
        String getPassword();

        void displayErrorEmail(Constants.ErrorCodes errorCode);
        void displayErrorPassword(Constants.ErrorCodes errorCode);

        void showProgress(String message);
        void dismissProgress();
        void showMessage(String message);
    }
    interface AuthPresenter extends BasePresenter {
        void signIn();
        void signUp();
    }
}
