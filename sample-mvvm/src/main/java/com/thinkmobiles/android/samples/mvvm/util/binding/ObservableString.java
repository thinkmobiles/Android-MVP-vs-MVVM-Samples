package com.thinkmobiles.android.samples.mvvm.util.binding;

import android.databinding.ObservableField;
import android.text.TextUtils;

import java.io.Serializable;

public final class ObservableString extends ObservableField<String> implements Serializable {

    private String mValue;

    @Override
    public final String get() {
        return mValue;
    }

    @Override
    public final void set(final String _value) {
        if (!TextUtils.equals(mValue, _value)) {
            mValue = _value;
            notifyChange();
        }
    }

}