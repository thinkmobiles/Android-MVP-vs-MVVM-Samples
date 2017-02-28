package com.thinkmobiles.android.samples.mvvm.presentation.welcome;

interface IWelcomeModel {

    void setViewModel(IWelcomeViewModel _viewModel);

    void removeViewModel();

    void performSignOut();

    void postCheckIn(String _msg);
}
