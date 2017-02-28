package com.thinkmobiles.android.samples.mvvm.presentation.navigator;

import android.support.v4.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.thinkmobiles.android.samples.mvvm.R;
import com.thinkmobiles.android.samples.mvvm.base.BaseActivity;
import com.thinkmobiles.android.samples.mvvm.base.INavigatorProvider;
import com.thinkmobiles.android.samples.mvvm.databinding.ViewMainNavigatorBinding;
import com.thinkmobiles.android.samples.mvvm.presentation.MainState;
import com.thinkmobiles.android.samples.mvvm.presentation.auth.AuthViewModelImpl;
import com.thinkmobiles.android.samples.mvvm.presentation.auth.IAuthViewModel;
import com.thinkmobiles.android.samples.mvvm.presentation.check_in.CheckInViewModelImpl;
import com.thinkmobiles.android.samples.mvvm.presentation.check_in.ICheckInViewModel;
import com.thinkmobiles.android.samples.mvvm.presentation.welcome.IWelcomeViewModel;
import com.thinkmobiles.android.samples.mvvm.presentation.welcome.WelcomeViewModelImpl;

public final class MainNavigatorViewModelImpl extends BaseActivity implements IMainNavigatorViewModel, INavigatorProvider<IMainNavigatorViewModel> {

    private IMainNavigatorView view;
    private IMainNavigatorModel model;

    @Override
    public void setState(final MainState state) {
        model.updateState(state);
        view.updateState(state);
    }

    @Override
    public void onAuthStateChanged(boolean isAuthorized) {
        final MainState state = model.getMainState();

        if(state != MainState.STATE_AUTH && !isAuthorized) openAuth();
        else if (state == null) openWelcome();

        view.updateOnlineStatus(isAuthorized);
    }

    @Override
    public final void openAuth() {
        final IAuthViewModel authViewModel = new AuthViewModelImpl();
        final FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.flFragmentContainer, (AuthViewModelImpl) authViewModel);
        fm.commit();
    }

    @Override
    public void openWelcome() {
        final IWelcomeViewModel welcomeViewModel = new WelcomeViewModelImpl();
        final FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.flFragmentContainer, (WelcomeViewModelImpl) welcomeViewModel);
        fm.commit();
    }

    @Override
    public void openCheckIn() {
        final ICheckInViewModel checkInViewModel = new CheckInViewModelImpl();
        final FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
        fm.replace(R.id.flFragmentContainer, (CheckInViewModelImpl) checkInViewModel);
        fm.addToBackStack(null);
        fm.commit();
    }

    @Override
    public final IMainNavigatorViewModel getNavigator() {
        return this;
    }

    @Override
    protected final void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new ViewHelper();

        final ViewMainNavigatorBinding binding = DataBindingUtil.setContentView(this, R.layout.view_main_navigator);
        binding.setHelper((ViewHelper) view);

        setSupportActionBar(binding.toolbarMain);

        if (model == null) model = new MainNavigatorModelImpl(objectGraph.getAuthModule(), objectGraph.getUserModule());

//        if (savedInstanceState == null) openAuth();
    }

    @Override
    protected final void onStart() {
        super.onStart();
        model.setViewModel(this);
    }

    @Override
    protected final void onStop() {
        super.onStop();
        model.removeViewModel();
    }

}
