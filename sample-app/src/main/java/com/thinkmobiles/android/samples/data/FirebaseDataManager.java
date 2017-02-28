package com.thinkmobiles.android.samples.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;
import com.thinkmobiles.android.samples.BuildConfig;
import com.thinkmobiles.android.samples.data.check_in.EntityTranslator;
import com.thinkmobiles.android.samples.domain.models.CheckIn;

import java.util.ArrayList;
import java.util.List;

public final class FirebaseDataManager {

    private final String TAG = "FirebaseDataManager";

    private final String TABLE_CHECK_IN = "check_in_table";

    // 'root/' DB Ref.
    private final DatabaseReference mDatabase;

    // 'root/check_in_table' DB Ref.
    private final DatabaseReference mDatabaseCheckIn;

    public FirebaseDataManager() {
        super();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setLogLevel(BuildConfig.DEBUG ? Logger.Level.DEBUG : Logger.Level.NONE);
        database.setPersistenceEnabled(false);

        mDatabase = database.getReference();

        mDatabaseCheckIn = mDatabase.child(TABLE_CHECK_IN);


    }

    public final void writeCheckIn(final CheckIn _checkIn, final @Nullable OnCompleteListener<Void> onCompleteListener) {

        final DatabaseReference dbRef = mDatabaseCheckIn.push();
        final String key = dbRef.getKey();

        final  Task<Void> task = dbRef.setValue(EntityTranslator.toMap(_checkIn));

        if(onCompleteListener != null) task.addOnCompleteListener(onCompleteListener);
        else task.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, TABLE_CHECK_IN + " | writeCheckIn: " + key + " | isSuccessful: " + task.isSuccessful());
            }
        });
    }

    public final void getAllCheckIns(final OnLoadCallback<CheckIn> loadCallback) {
        mDatabaseCheckIn.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public final void onDataChange(final DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                Log.d(TAG, TABLE_CHECK_IN + " | AllCheckIns | Count is: " + count);
                final List<CheckIn> checkIns = new ArrayList<CheckIn>();
                for (final DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    final CheckIn checkIn = dataSnapshot1.getValue(CheckIn.class);
                    checkIns.add(checkIn);
                    Log.d(TAG, TABLE_CHECK_IN + " | AllCheckIns | Value " + checkIn.checkInMessage);
                }
                loadCallback.onDataChange(checkIns);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, TABLE_CHECK_IN + " | AllCheckIns | Failed to read: ", databaseError.toException());
                loadCallback.onCancelled();
            }
        });
    }

    public final void getCheckInByEmail(final String _email, final OnLoadCallback<CheckIn> loadCallback) {

        mDatabaseCheckIn.orderByChild("email").equalTo(_email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                Log.d(TAG, TABLE_CHECK_IN + " | MyCheckIns | Count is: " + count);
                final List<CheckIn> checkIns = new ArrayList<CheckIn>();
                for (final DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    final CheckIn checkIn = dataSnapshot1.getValue(CheckIn.class);
                    checkIns.add(checkIn);
                    Log.d(TAG, TABLE_CHECK_IN + " | MyCheckIns | Value " + checkIn.checkInMessage);
                }
                loadCallback.onDataChange(checkIns);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, TABLE_CHECK_IN + " | MyCheckIns | Failed to read: ", databaseError.toException());
                loadCallback.onCancelled();
            }
        });

    }

    public interface OnLoadCallback<T> {
        void onDataChange(List<T> _data);
        void onCancelled();
    }

}
