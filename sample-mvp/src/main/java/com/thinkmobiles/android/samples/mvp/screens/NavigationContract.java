package com.thinkmobiles.android.samples.mvp.screens;

import android.support.v4.app.Fragment;

import com.thinkmobiles.android.samples.mvp.base.BasePresenter;
import com.thinkmobiles.android.samples.mvp.base.BaseView;
import com.thinkmobiles.android.samples.mvp.utils.Constants;

/**
 * Created by Lynx on 2/27/2017.
 */

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
