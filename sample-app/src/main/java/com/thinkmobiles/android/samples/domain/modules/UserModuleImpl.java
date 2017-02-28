package com.thinkmobiles.android.samples.domain.modules;

import com.thinkmobiles.android.samples.domain.models.Credential;
import com.thinkmobiles.android.samples.domain.models.User;

public final class UserModuleImpl implements IUserModule {

    private User user;

    @Override
    public final Credential getTestCredential() {
        return new Credential("test-user@test-mail.com", "testpass");
    }

    @Override
    public final User getUser() {
        return user;
    }

    @Override
    public final void setUser(final User _user) {
        user = _user;
    }
}
