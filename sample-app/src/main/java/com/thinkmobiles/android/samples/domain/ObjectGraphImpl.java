package com.thinkmobiles.android.samples.domain;

import android.content.Context;

import com.thinkmobiles.android.samples.data.FirebaseAuthManager;
import com.thinkmobiles.android.samples.domain.modules.AuthModuleImpl;
import com.thinkmobiles.android.samples.domain.modules.CheckInModuleImpl;
import com.thinkmobiles.android.samples.domain.modules.IAuthModule;
import com.thinkmobiles.android.samples.domain.modules.ICheckInModule;
import com.thinkmobiles.android.samples.domain.modules.IUserModule;
import com.thinkmobiles.android.samples.domain.modules.UserModuleImpl;

public final class ObjectGraphImpl extends ObjectGraph {

    public static void init(final Context _context) {
        graph = new ObjectGraphImpl(_context);
    }

    public static void clean() {
        graph = null;
    }

    private final IUserModule userModule;
    private final IAuthModule authModule;
    private final ICheckInModule checkInModule;

    private ObjectGraphImpl(final Context _context) {

        userModule = new UserModuleImpl();

        authModule = new AuthModuleImpl(new FirebaseAuthManager());

        checkInModule = new CheckInModuleImpl();
        checkInModule.init();

    }

    @Override
    public final IUserModule getUserModule() {
        return userModule;
    }

    @Override
    public final IAuthModule getAuthModule() {
        return authModule;
    }

    @Override
    public ICheckInModule getCheckInModule() {
        return checkInModule;
    }
}
