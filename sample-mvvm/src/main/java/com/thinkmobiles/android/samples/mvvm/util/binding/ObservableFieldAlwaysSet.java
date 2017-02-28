package com.thinkmobiles.android.samples.mvvm.util.binding;

import android.databinding.ObservableField;

public class ObservableFieldAlwaysSet<T> extends ObservableField<T> {

    private T mValue;

    @Override
    public final T get() {
        return mValue;
    }

    @Override
    public final void set(final T _value) {
        mValue = _value;
        notifyChange();
    }

}