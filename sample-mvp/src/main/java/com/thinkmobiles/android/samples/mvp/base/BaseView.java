package com.thinkmobiles.android.samples.mvp.base;


public interface BaseView<T extends BasePresenter> {
    void initPresenter();
    void setPresenter(T presenter);
}
