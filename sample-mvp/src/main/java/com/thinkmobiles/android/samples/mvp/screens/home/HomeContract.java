package com.thinkmobiles.android.samples.mvp.screens.home;

import com.thinkmobiles.android.samples.mvp.base.BasePresenter;
import com.thinkmobiles.android.samples.mvp.base.BaseView;
import com.thinkmobiles.android.samples.mvp.utils.Constants;

public interface HomeContract {
    interface HomeView extends BaseView<HomePresenter> {
        void displayUID(String uid);
        void displayEmail(String email);

        String getCheckInMessage();
        void displayErrorCheckInMessage(Constants.ErrorCodes errorCode);

        void showProgress(String message);
        void dismissProgress();
        void showMessage(String message);

        void startCheckInsScreen();
    }
    interface HomePresenter extends BasePresenter {
        void post();
        void browse();
        void signOut();
    }
}
