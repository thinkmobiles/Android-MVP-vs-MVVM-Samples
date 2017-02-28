package com.thinkmobiles.android.samples.mvvm.presentation.navigator;

import com.thinkmobiles.android.samples.mvvm.presentation.MainState;

interface IMainNavigatorView {

    void updateState(MainState state);

    void updateOnlineStatus(boolean isOnline);

}
