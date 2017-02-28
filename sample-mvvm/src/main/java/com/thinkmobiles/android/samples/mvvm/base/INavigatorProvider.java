package com.thinkmobiles.android.samples.mvvm.base;

public interface INavigatorProvider<T extends IBaseNavigator> {

    T getNavigator();

}