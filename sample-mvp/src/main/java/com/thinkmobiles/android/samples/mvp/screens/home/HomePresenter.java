package com.thinkmobiles.android.samples.mvp.screens.home;

import android.text.TextUtils;

import com.thinkmobiles.android.samples.domain.models.CheckIn;
import com.thinkmobiles.android.samples.domain.modules.IAuthModule;
import com.thinkmobiles.android.samples.domain.modules.ICheckInModule;
import com.thinkmobiles.android.samples.mvp.utils.Constants;

public class HomePresenter implements HomeContract.HomePresenter {

    private HomeContract.HomeView view;
    private IAuthModule authModule;
    private ICheckInModule checkInModule;

    public HomePresenter(HomeContract.HomeView view, IAuthModule authModule, ICheckInModule checkInModule) {
        this.view = view;
        this.authModule = authModule;
        this.checkInModule = checkInModule;

        view.setPresenter(this);
    }

    @Override
    public void post() {
        String msg = view.getCheckInMessage();
        if(isMessageValid(msg)) {
            view.showProgress("Send message...");
            CheckIn checkIn = new CheckIn(authModule.getUser().email, msg);
            checkInModule.leftNewCheckIn(checkIn, new ICheckInModule.Callback() {
                @Override
                public void onResult(boolean isSuccess, String _msg) {
                    view.dismissProgress();
                    view.showMessage(isSuccess ? "Sent" : _msg);
                }
            });
        }
    }

    @Override
    public void browse() {
        view.startCheckInsScreen();
    }

    @Override
    public void signOut() {
        authModule.signOut(null);
    }

    @Override
    public void subscribe() {
        view.displayUID(authModule.getUser().uid);
        view.displayEmail(authModule.getUser().email);
    }

    @Override
    public void unsubscribe() {
        view = null;
        authModule = null;
        checkInModule = null;
    }

    private boolean isMessageValid(String message) {
        Constants.ErrorCodes errorCodeMsg;
        if(TextUtils.isEmpty(message)) {
            errorCodeMsg = Constants.ErrorCodes.FIELD_EMPTY;
        } else if(message.length() < 2 || message.length() > 40) {
            errorCodeMsg = Constants.ErrorCodes.FIELD_INVALID;
        } else {
            errorCodeMsg = Constants.ErrorCodes.OK;
        }
        view.displayErrorCheckInMessage(errorCodeMsg);
        return errorCodeMsg == Constants.ErrorCodes.OK;
    }
}
