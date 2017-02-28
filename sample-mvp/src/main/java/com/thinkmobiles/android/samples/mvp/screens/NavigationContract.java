package com.thinkmobiles.android.samples.mvp.screens;

import com.thinkmobiles.android.samples.mvp.base.BasePresenter;
import com.thinkmobiles.android.samples.mvp.base.BaseView;

public interface NavigationContract {
    interface NavigationView extends BaseView<NavigationPresenter> {
        void setToolbarTitle(String title);
        void displayIndicatorStatus(boolean isAuthorized);

        void displayAuthScreen();
        void displayHomeScreen();
        void displayCheckInsScreen();
    }
    interface NavigationPresenter extends BasePresenter {

    }
}
