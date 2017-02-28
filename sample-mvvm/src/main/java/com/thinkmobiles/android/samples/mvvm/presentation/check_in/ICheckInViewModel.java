package com.thinkmobiles.android.samples.mvvm.presentation.check_in;

import com.thinkmobiles.android.samples.domain.models.CheckIn;

import java.util.List;

public interface ICheckInViewModel {

    void onFetchAllCheckInsClicked();

    void fetchAllSuccess(List<CheckIn> checkIns);

    void fetchAllFailed(String _msg);

    void onFetchMyCheckInsClicked();

    void fetchMySuccess(List<CheckIn> checkIns);

    void fetchMyFailed(String _msg);

    void onRefreshList();

}
