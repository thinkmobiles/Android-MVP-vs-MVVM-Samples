package com.thinkmobiles.android.samples.data.check_in;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;
import com.thinkmobiles.android.samples.domain.models.CheckIn;

import java.util.HashMap;
import java.util.Map;

public final class EntityTranslator {

    @Exclude
    public static Map<String, Object> toMap(final CheckIn checkIn) {

        final Map<String, Object> result = new HashMap<>();

        result.put("email", checkIn.email);
        result.put("checkInMessage", checkIn.checkInMessage);
        result.put("timestamp", checkIn.timestamp == null ? ServerValue.TIMESTAMP : checkIn.timestamp);

        return result;
    }

}
