package com.thinkmobiles.android.samples.mvvm.presentation.welcome;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thinkmobiles.android.samples.domain.models.CheckIn;
import com.thinkmobiles.android.samples.domain.models.User;
import com.thinkmobiles.android.samples.mvvm.R;
import com.thinkmobiles.android.samples.mvvm.base.BaseFragment;
import com.thinkmobiles.android.samples.mvvm.databinding.ViewMainWelcomeBinding;
import com.thinkmobiles.android.samples.mvvm.presentation.MainState;
import com.thinkmobiles.android.samples.mvvm.presentation.navigator.IMainNavigatorViewModel;

public final class WelcomeViewModelImpl extends BaseFragment<IMainNavigatorViewModel> implements IWelcomeViewModel {

    private IWelcomeView view;
    private IWelcomeModel model;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (model == null)
            model = new WelcomeModelImpl(objectGraph.getAuthModule(), objectGraph.getUserModule(), objectGraph.getCheckInModule());

        final ViewMainWelcomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.view_main_welcome, container, false);
        view = new ViewHelper(this, binding);
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public final void onStart() {
        super.onStart();
        model.setViewModel(this);
        navigator.setState(MainState.STATE_WELCOME);
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
    public void onSignOutClicked() {
        model.performSignOut();
    }

    @Override
    public void onSignedOut() {
        Toast.makeText(getContext(), "Signed Out", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostItClicked(String _msgForCheckIn) {
        dialogShower.showProgress(getContext());
        model.postCheckIn(_msgForCheckIn);
    }

    @Override
    public void onPostSuccess(String _msgForCheckIn) {
        dialogShower.hideProgress();
        Toast.makeText(getContext(), "PostSuccess", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostFailed(String _msgForCheckIn) {
        dialogShower.hideProgress();
        Toast.makeText(getContext(), "PostFailed: " + _msgForCheckIn, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCheckInsClicked() {
        navigator.openCheckIn();
    }
 
}
