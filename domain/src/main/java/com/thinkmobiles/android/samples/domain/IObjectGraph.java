package com.thinkmobiles.android.samples.domain;

import com.thinkmobiles.android.samples.domain.modules.IAuthModule;
import com.thinkmobiles.android.samples.domain.modules.ICheckInModule;
import com.thinkmobiles.android.samples.domain.modules.IUserModule;

public interface IObjectGraph {

    IUserModule getUserModule();

    IAuthModule getAuthModule();

    ICheckInModule getCheckInModule();

}
