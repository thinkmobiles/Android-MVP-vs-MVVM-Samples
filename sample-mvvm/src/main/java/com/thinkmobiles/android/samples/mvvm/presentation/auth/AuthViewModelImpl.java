package com.thinkmobiles.android.samples.mvvm.presentation.auth;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thinkmobiles.android.samples.domain.models.Credential;
import com.thinkmobiles.android.samples.mvvm.R;
import com.thinkmobiles.android.samples.mvvm.base.BaseFragment;
import com.thinkmobiles.android.samples.mvvm.databinding.ViewMainAuthBinding;
import com.thinkmobiles.android.samples.mvvm.presentation.MainState;
import com.thinkmobiles.android.samples.mvvm.presentation.navigator.IMainNavigatorViewModel;

public final class AuthViewModelImpl extends BaseFragment<IMainNavigatorViewModel> implements IAuthViewModel {

    private IAuthView view;
    private IAuthModel model;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (model == null)
            model = new AuthModelImpl(objectGraph.getAuthModule(), objectGraph.getUserModule());

        final ViewMainAuthBinding binding = DataBindingUtil.inflate(inflater, R.layout.view_main_auth, container, false);
        view = new ViewHelper(this, binding);
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View _view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(_view, savedInstanceState);

        if(savedInstanceState == null) view.setTestCredentials(objectGraph.getUserModule().getTestCredential());

    }

    @Override
    public final void onStart() {
        super.onStart();
        model.setViewModel(this);
        navigator.setState(MainState.STATE_AUTH);
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
    public void setCredential(Credential credential) {
        model.setCredential(credential);
    }

    @Override
    public void onSignInClicked() {
        dialogShower.showProgress(getContext());
        model.performSignIn();
    }

    @Override
    public void onSignSuccess() {
        dialogShower.hideProgress();
        Toast.makeText(getContext(), "Sign Success", Toast.LENGTH_SHORT).show();

        navigator.openWelcome();

    }

    @Override
    public void onSignFailed(String _msg) {
        dialogShower.hideProgress();
        Toast.makeText(getContext(), "Sign Failed: " + _msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterClicked() {
        dialogShower.showProgress(getContext());
        model.performRegister();
    }

    @Override
    public void onRegisterSuccess() {
        dialogShower.hideProgress();
        Toast.makeText(getContext(), "Register Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterFailed(String _msg) {
        dialogShower.hideProgress();
        Toast.makeText(getContext(), "Register Failed: " + _msg, Toast.LENGTH_SHORT).show();
    }

}
