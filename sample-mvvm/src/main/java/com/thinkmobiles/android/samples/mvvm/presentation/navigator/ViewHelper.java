package com.thinkmobiles.android.samples.mvvm.presentation.navigator;

import android.databinding.ObservableBoolean;

import com.thinkmobiles.android.samples.mvvm.presentation.MainState;
import com.thinkmobiles.android.samples.mvvm.util.binding.ObservableString;

public final class ViewHelper implements IMainNavigatorView {

    public final ObservableString title = new ObservableString();
    public final ObservableBoolean isOnline = new ObservableBoolean(false);


    @Override
    public void updateState(MainState state) {
        switch (state) {
            case STATE_AUTH:
                title.set("Auth");
                break;
            case STATE_WELCOME:
                title.set("Welcome");
                break;
            case STATE_CHECK_IN:
                title.set("CheckIns");
                break;
        }
    }

    @Override
    public void updateOnlineStatus(boolean _isOnline) {
        isOnline.set(_isOnline);
    }
}
