package com.thinkmobiles.android.samples.mvp.screens.check_ins;

import com.thinkmobiles.android.samples.domain.models.CheckIn;
import com.thinkmobiles.android.samples.mvp.base.BasePresenter;
import com.thinkmobiles.android.samples.mvp.base.BaseView;

import java.util.List;

public interface CheckInsContract {
    interface CheckInsView extends BaseView<CheckInsPresenter> {
        void displayCheckIns(List<CheckIn> checkIns);
        void displayEmptyPlaceholder(boolean isShown);

        void showProgress(String message);
        void dismissProgress();
        void showMessage(String message);
    }
    interface CheckInsPresenter extends BasePresenter {
        void refresh();
        void getMyCheckIns();
        void getAllCheckIns();
    }
}
