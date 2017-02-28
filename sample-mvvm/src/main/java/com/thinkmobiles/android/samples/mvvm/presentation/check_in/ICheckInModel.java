package com.thinkmobiles.android.samples.mvvm.presentation.check_in;

interface ICheckInModel {

    void setViewModel(ICheckInViewModel _viewModel);

    void removeViewModel();

    void loadAllCheckIns(boolean doReload);

    void loadMyCheckIns();

    State getState();

}
