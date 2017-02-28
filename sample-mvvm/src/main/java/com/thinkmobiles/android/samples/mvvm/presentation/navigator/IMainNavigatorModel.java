package com.thinkmobiles.android.samples.mvvm.presentation.navigator;

import com.thinkmobiles.android.samples.mvvm.presentation.MainState;

interface IMainNavigatorModel {

    void setViewModel(IMainNavigatorViewModel _viewModel);

    void removeViewModel();

    void updateState(MainState state);

    MainState getMainState();

}
