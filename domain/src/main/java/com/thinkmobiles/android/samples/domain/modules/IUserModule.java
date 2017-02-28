package com.thinkmobiles.android.samples.domain.modules;

import com.thinkmobiles.android.samples.domain.models.Credential;
import com.thinkmobiles.android.samples.domain.models.User;

public interface IUserModule {

    Credential getTestCredential();

    void setUser(User _user);

    User getUser();

}
