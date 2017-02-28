package com.thinkmobiles.android.samples.mvp.screens.check_ins;

import com.thinkmobiles.android.samples.domain.models.CheckIn;
import com.thinkmobiles.android.samples.domain.modules.IAuthModule;
import com.thinkmobiles.android.samples.domain.modules.ICheckInModule;
import com.thinkmobiles.android.samples.mvp.utils.Constants;

import java.util.ArrayList;

public class CheckInsPresenter implements CheckInsContract.CheckInsPresenter {

    private CheckInsContract.CheckInsView view;
    private Constants.CheckInType checkInType;
    private ICheckInModule checkInModule;
    private IAuthModule authModule;

    public CheckInsPresenter(CheckInsContract.CheckInsView view, ICheckInModule checkInModule, IAuthModule authModule) {
        this.view = view;
        this.checkInModule = checkInModule;
        this.authModule = authModule;
        checkInType = Constants.CheckInType.EMPTY;

        view.setPresenter(this);
    }

    @Override
    public void refresh() {
        switch (checkInType) {
            case EMPTY:
                view.showMessage("Choose check ins");
                view.displayCheckIns(new ArrayList<CheckIn>());
                break;
            case MY:
                loadMyCheckIns();
                break;
            case ALL:
                loadAllCheckIns();
                break;
        }
    }

    @Override
    public void getMyCheckIns() {
        view.showProgress("Loading...");
        loadMyCheckIns();
    }

    @Override
    public void getAllCheckIns() {
        view.showProgress("Loading...");
        loadAllCheckIns();
    }

    @Override
    public void subscribe() {
        view.displayEmptyPlaceholder(true);
    }

    @Override
    public void unsubscribe() {
        view = null;
        checkInType = null;
        checkInModule = null;
        authModule = null;
    }

    private void loadAllCheckIns() {
        checkInType = Constants.CheckInType.ALL;
        checkInModule.loadAllCheckIn(new ICheckInModule.Callback() {
            @Override
            public void onResult(boolean isSuccess, String _msg) {
                view.dismissProgress();
                if (isSuccess) {
                    view.displayCheckIns(checkInModule.getAllCheckInList());
                    view.displayEmptyPlaceholder(checkInModule.getAllCheckInList().isEmpty());
                } else
                    view.displayEmptyPlaceholder(true);
            }
        });
    }

    private void loadMyCheckIns() {
        checkInType = Constants.CheckInType.MY;
        checkInModule.loadCheckInByEmail(authModule.getUser().email, new ICheckInModule.Callback() {
            @Override
            public void onResult(boolean isSuccess, String _msg) {
                view.dismissProgress();
                if (isSuccess) {
                    view.displayCheckIns(checkInModule.getMyCheckInList());
                    view.displayEmptyPlaceholder(checkInModule.getMyCheckInList().isEmpty());
                } else
                    view.displayEmptyPlaceholder(true);
            }
        });
    }
}
