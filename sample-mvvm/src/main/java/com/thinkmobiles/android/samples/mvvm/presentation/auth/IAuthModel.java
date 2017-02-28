package com.thinkmobiles.android.samples.mvvm.presentation.auth;

import com.thinkmobiles.android.samples.domain.models.Credential;

interface IAuthModel {

    void setViewModel(IAuthViewModel _viewModel);

    void removeViewModel();

    void performSignIn();

    void performRegister();

    void performSignOut();

    void setCredential(Credential credential);

}
