package com.thinkmobiles.android.samples.mvvm.presentation.auth;

import android.view.View;
import android.widget.EditText;

import com.thinkmobiles.android.samples.domain.models.Credential;
import com.thinkmobiles.android.samples.mvvm.databinding.ViewMainAuthBinding;
import com.thinkmobiles.android.samples.mvvm.util.binding.ObservableFieldAlwaysSet;

public final class ViewHelper implements IAuthView {

    public final ObservableFieldAlwaysSet<Credential> testCredential = new ObservableFieldAlwaysSet<>();

    private final IAuthViewModel viewModel;

    private EditText etEmail, etPassword;

    public ViewHelper(final IAuthViewModel _viewModel, final ViewMainAuthBinding binding) {
        viewModel = _viewModel;
        binding.setHelper(this);

        etEmail = binding.etEmail;
        etPassword = binding.etPassword;
    }

    public final View.OnClickListener clickSignIn = new View.OnClickListener() {
        @Override
        public final void onClick(final View v) {
            viewModel.setCredential(new Credential(etEmail.getText().toString(), etPassword.getText().toString()));
            viewModel.onSignInClicked();
        }
    };

    public final View.OnClickListener clickRegister = new View.OnClickListener() {
        @Override
        public final void onClick(final View v) {
            viewModel.setCredential(new Credential(etEmail.getText().toString(), etPassword.getText().toString()));
            viewModel.onRegisterClicked();
        }
    };

    @Override
    public void setTestCredentials(Credential credential) {
        testCredential.set(credential);
    }
}
