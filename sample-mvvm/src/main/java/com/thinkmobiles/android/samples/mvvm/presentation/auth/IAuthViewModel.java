package com.thinkmobiles.android.samples.mvvm.presentation.auth;

import com.thinkmobiles.android.samples.domain.models.Credential;

public interface IAuthViewModel {

    void setCredential(Credential credential);

    void onSignInClicked();
    void onSignSuccess();
    void onSignFailed(String _msg);

    void onRegisterClicked();
    void onRegisterSuccess();
    void onRegisterFailed(String _msg);

}
