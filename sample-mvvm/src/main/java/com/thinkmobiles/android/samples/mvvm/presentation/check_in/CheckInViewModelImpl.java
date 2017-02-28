package com.thinkmobiles.android.samples.mvvm.presentation.check_in;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkmobiles.android.samples.domain.models.CheckIn;
import com.thinkmobiles.android.samples.domain.models.User;
import com.thinkmobiles.android.samples.mvvm.R;
import com.thinkmobiles.android.samples.mvvm.base.BaseFragment;
import com.thinkmobiles.android.samples.mvvm.databinding.ViewMainCheckInBinding;
import com.thinkmobiles.android.samples.mvvm.presentation.MainState;
import com.thinkmobiles.android.samples.mvvm.presentation.navigator.IMainNavigatorViewModel;

import java.util.ArrayList;
import java.util.List;

public final class CheckInViewModelImpl extends BaseFragment<IMainNavigatorViewModel> implements ICheckInViewModel {

    private ICheckInView view;
    private ICheckInModel model;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (model == null)
            model = new CheckInModelImpl(objectGraph.getUserModule(), objectGraph.getCheckInModule());

        final ViewMainCheckInBinding binding = DataBindingUtil.inflate(inflater, R.layout.view_main_check_in, container, false);
        view = new ViewHelper(this, binding, getContext());
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public final void onStart() {
        super.onStart();
        model.setViewModel(this);
        navigator.setState(MainState.STATE_CHECK_IN);
    }

    @Override
    public final void onResume() {
        super.onResume();

        final User user = objectGraph.getUserModule().getUser();
        view.setUser(user);

    }

    @Override
    public final void onStop() {
        super.onStop();
        model.removeViewModel();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }

    @Override
    public void onFetchAllCheckInsClicked() {
        dialogShower.showProgress(getContext());
        model.loadAllCheckIns(false);

    }

    @Override
    public void onFetchMyCheckInsClicked() {
        dialogShower.showProgress(getContext());
        model.loadMyCheckIns();
    }

    @Override
    public void fetchAllFailed(String _msg) {
        dialogShower.hideProgress();
        view.setTitle("All CheckIns - Failed to load");
        view.setCheckIns(new ArrayList<CheckIn>());
    }

    @Override
    public void fetchAllSuccess(List<CheckIn> checkIns) {
        dialogShower.hideProgress();
        view.setCheckIns(checkIns);
        view.setTitle("All CheckIns");
    }

    @Override
    public void fetchMySuccess(List<CheckIn> checkIns) {
        dialogShower.hideProgress();
        view.setCheckIns(checkIns);
        view.setTitle("My CheckIns");
    }

    @Override
    public void fetchMyFailed(String _msg) {
        dialogShower.hideProgress();
        view.setTitle("My CheckIns - Failed to load");
        view.setCheckIns(new ArrayList<CheckIn>());
    }

    @Override
    public void onRefreshList() {
        switch (model.getState()) {
            case ALL:
                model.loadAllCheckIns(true);
                break;
            case MY:
                model.loadMyCheckIns();
                break;
        }
    }
}
