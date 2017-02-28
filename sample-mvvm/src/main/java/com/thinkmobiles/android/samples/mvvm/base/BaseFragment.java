package com.thinkmobiles.android.samples.mvvm.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.thinkmobiles.android.samples.domain.IObjectGraph;
import com.thinkmobiles.android.samples.domain.ObjectGraph;
import com.thinkmobiles.android.samples.mvvm.util.dialog.DialogShower;
import com.thinkmobiles.android.samples.mvvm.util.dialog.IDialogShower;

public abstract class BaseFragment<T extends IBaseNavigator> extends Fragment {

    protected T navigator;
    protected IObjectGraph objectGraph;
    protected IDialogShower dialogShower;

    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        navigator = ((INavigatorProvider<T>) getActivity()).getNavigator();
    }

    @Override
    public final void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        objectGraph = ObjectGraph.getInstance();
        dialogShower = new DialogShower();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        objectGraph = null;
        dialogShower = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigator = null;
    }
}
