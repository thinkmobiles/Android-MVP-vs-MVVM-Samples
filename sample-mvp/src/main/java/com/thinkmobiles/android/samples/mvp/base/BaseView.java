package com.thinkmobiles.android.samples.mvp.base;

/**
 * Created by Lynx on 2/27/2017.
 */

public interface BaseView<T extends BasePresenter> {
    void initPresenter();
    void setPresenter(T presenter);
}
