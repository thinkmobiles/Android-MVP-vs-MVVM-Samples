package com.thinkmobiles.android.samples.mvvm.presentation.welcome;

public interface IWelcomeViewModel {

    void onSignOutClicked();

    void onViewCheckInsClicked();

    void onPostItClicked(String _msgForCheckIn);

    void onPostSuccess(String _msgForCheckIn);

    void onPostFailed(String _msgForCheckIn);

    void onSignedOut();

}
