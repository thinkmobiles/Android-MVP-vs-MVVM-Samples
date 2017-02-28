package com.thinkmobiles.android.samples.domain.modules;

import com.thinkmobiles.android.samples.domain.models.Credential;
import com.thinkmobiles.android.samples.domain.models.User;

public interface IAuthModule {

    void setCredential(Credential credential);
    Credential getCredential();

    void start();

    void stop();

    void register(String email, String password, Callback callback);

    void signIn(String email, String password, Callback callback);

    void signOut(Callback callback);

    interface Callback {
        void onComplete(boolean isSuccessful, String _msg);
    }

    void setSessionListener(SessionCallback sessionCallback);

    interface SessionCallback {
        void onAuthState(boolean isAuthorized);
    }

    User getUser();
}
