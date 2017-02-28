package com.thinkmobiles.android.samples.domain.modules;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.thinkmobiles.android.samples.data.FirebaseDataManager;
import com.thinkmobiles.android.samples.domain.models.CheckIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class CheckInModuleImpl implements ICheckInModule {

    private FirebaseDataManager firebaseDataManager;

    private final List<CheckIn> allCheckIn = new ArrayList<>();

    private final List<CheckIn> myCheckIn = new ArrayList<>();

    @Override
    public void init() {
        firebaseDataManager = new FirebaseDataManager();
    }

    @Override
    public void leftNewCheckIn(CheckIn _checkIn, final Callback callback) {
        firebaseDataManager.writeCheckIn(_checkIn, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                final Exception exception = task.getException();
                printStackTrace(exception);
                if (callback != null) {
                    callback.onResult(task.isSuccessful(), exception != null ? exception.getMessage() : "");
                }
            }
        });
    }

    @Override
    public void loadAllCheckIn(final Callback _callback) {

        firebaseDataManager.getAllCheckIns(new FirebaseDataManager.OnLoadCallback<CheckIn>() {
            @Override
            public void onDataChange(List<CheckIn> _data) {
                allCheckIn.clear();
                allCheckIn.addAll(sortCheckInsByTimestamp(_data));
                if (_callback != null) _callback.onResult(true, "");
            }

            @Override
            public void onCancelled() {
                allCheckIn.clear();
                if (_callback != null) _callback.onResult(false, "Cancelled");

            }
        });
    }

    @Override
    public List<CheckIn> getAllCheckInList() {
        return allCheckIn;
    }

    @Override
    public List<CheckIn> getMyCheckInList() {
        return myCheckIn;
    }

    @Override
    public void loadCheckInByEmail(final String _email, final Callback _callback) {
        firebaseDataManager.getCheckInByEmail(_email, new FirebaseDataManager.OnLoadCallback<CheckIn>() {
            @Override
            public void onDataChange(List<CheckIn> _data) {
                myCheckIn.clear();
                myCheckIn.addAll(sortCheckInsByTimestamp(_data));
                if (_callback != null) _callback.onResult(true, "");
            }

            @Override
            public void onCancelled() {
                myCheckIn.clear();
                if (_callback != null) _callback.onResult(false, "Cancelled");
            }
        });
    }

    private void printStackTrace(@Nullable final Exception exception) {
        if (exception != null) {
            try {
                throw exception;
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }


    private List<CheckIn> sortCheckInsByTimestamp(final List<CheckIn> _checkInsUnsorted) {

        if(_checkInsUnsorted == null) return new ArrayList<>();

        final Comparator<CheckIn> checkInComparator = new Comparator<CheckIn>() {
            @Override
            public final int compare(final CheckIn o1, final CheckIn o2) {

                final long t1 = o1.timestamp == null ? 0 : o1.timestamp;
                final long t2 = o2.timestamp == null ? 0 : o2.timestamp;

                return Long.signum(t2 - t1);

            }
        };
        Collections.sort(_checkInsUnsorted, checkInComparator);
        return _checkInsUnsorted;
    }

}
