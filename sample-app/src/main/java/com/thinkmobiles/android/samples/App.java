package com.thinkmobiles.android.samples;

import android.app.Application;

import com.thinkmobiles.android.samples.domain.ObjectGraphImpl;

public final class App extends Application {

    @Override
    public final void onCreate() {
        super.onCreate();
        ObjectGraphImpl.init(this);
    }

    /**
     * This method is for use in emulated process environments.
     */
    @Override
    public final void onTerminate() {
        super.onTerminate();
        ObjectGraphImpl.clean();
    }
}
