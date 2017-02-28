package com.thinkmobiles.android.samples.domain.modules;

import com.thinkmobiles.android.samples.domain.models.CheckIn;

import java.util.List;

public interface ICheckInModule {

    void init();

    void leftNewCheckIn(CheckIn _checkIn, Callback _callback);

    void loadAllCheckIn(Callback _callback);

    List<CheckIn> getAllCheckInList();

    void loadCheckInByEmail(String _email, Callback _callback);

    List<CheckIn> getMyCheckInList();

    interface Callback {
        void onResult(boolean isSuccess, String _msg);
    }

}

