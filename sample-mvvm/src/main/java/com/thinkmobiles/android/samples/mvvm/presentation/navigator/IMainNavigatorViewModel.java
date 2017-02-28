package com.thinkmobiles.android.samples.mvvm.presentation.navigator;

import com.thinkmobiles.android.samples.mvvm.base.IBaseNavigator;
import com.thinkmobiles.android.samples.mvvm.presentation.MainState;

public interface IMainNavigatorViewModel extends IBaseNavigator {

    void setState(MainState state);

    void onAuthStateChanged(boolean isAuthorized);

    void openAuth();

    void openWelcome();

    void openCheckIn();


}
