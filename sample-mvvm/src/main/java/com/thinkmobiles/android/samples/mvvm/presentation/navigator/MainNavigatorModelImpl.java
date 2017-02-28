package com.thinkmobiles.android.samples.mvvm.presentation.navigator;

import com.thinkmobiles.android.samples.domain.modules.IAuthModule;
import com.thinkmobiles.android.samples.domain.modules.IUserModule;
import com.thinkmobiles.android.samples.mvvm.presentation.MainState;

final class MainNavigatorModelImpl implements IMainNavigatorModel {

    private IMainNavigatorViewModel viewModel;
    private MainState mainState;

    private final IAuthModule authModule;

    MainNavigatorModelImpl(final IAuthModule _authModule, final IUserModule _userModule) {
        authModule = _authModule;
        authModule.setSessionListener(new IAuthModule.SessionCallback() {
            @Override
            public void onAuthState(boolean isAuthorized) {
                if (viewModel != null) viewModel.onAuthStateChanged(isAuthorized);

                if(isAuthorized) _userModule.setUser(_authModule.getUser());
            }
        });
    }

    @Override
    public void setViewModel(IMainNavigatorViewModel _viewModel) {
        viewModel = _viewModel;

        authModule.start();
    }

    @Override
    public void removeViewModel() {
        viewModel = null;

        authModule.stop();
    }

    @Override
    public void updateState(MainState state) {
        mainState = state;
    }

    @Override
    public MainState getMainState() {
        return mainState;
    }
}
