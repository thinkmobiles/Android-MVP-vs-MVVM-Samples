package com.thinkmobiles.android.samples.mvp.screens;

import android.text.TextUtils;

import com.thinkmobiles.android.samples.domain.modules.IAuthModule;

/**
 * Created by Lynx on 2/27/2017.
 */

public class NavigationPresenter implements NavigationContract.NavigationPresenter {

    private NavigationContract.NavigationView view;
    private IAuthModule authModule;

    public NavigationPresenter(NavigationContract.NavigationView view, IAuthModule authModule) {
        this.view = view;
        this.authModule = authModule;

        view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        authModule.start();
        authModule.setSessionListener(new IAuthModule.SessionCallback() {
            @Override
            public void onAuthState(boolean isAuthorized) {
                view.displayIndicatorStatus(isAuthorized);
                if (authModule.getUser() != null && !TextUtils.isEmpty(authModule.getUser().uid)) {
                    view.displayHomeScreen();
                } else
                    view.displayAuthScreen();
            }
        });
    }

    @Override
    public void unsubscribe() {
        authModule.stop();
        authModule = null;
        view = null;
    }
}
