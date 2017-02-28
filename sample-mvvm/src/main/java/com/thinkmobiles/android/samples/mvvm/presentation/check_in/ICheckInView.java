package com.thinkmobiles.android.samples.mvvm.presentation.check_in;

import com.thinkmobiles.android.samples.domain.models.CheckIn;
import com.thinkmobiles.android.samples.domain.models.User;

import java.util.List;

interface ICheckInView {

    void setUser(User _user);

    void setCheckIns(List<CheckIn> _checkIns);

    void setTitle(String _title);

}
