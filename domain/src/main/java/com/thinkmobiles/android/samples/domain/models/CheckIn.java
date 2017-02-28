package com.thinkmobiles.android.samples.domain.models;

public final class CheckIn {

    public String email;
    public String checkInMessage;
    public Long timestamp;

    public CheckIn(final String _email, final String _checkInMessage) {
        email = _email;
        checkInMessage = _checkInMessage;
    }

    public CheckIn() {
        super();
    }

}
