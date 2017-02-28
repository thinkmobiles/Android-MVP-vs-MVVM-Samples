package com.thinkmobiles.android.samples.mvvm.presentation.auth;

import com.thinkmobiles.android.samples.domain.models.Credential;
import com.thinkmobiles.android.samples.domain.modules.IAuthModule;
import com.thinkmobiles.android.samples.domain.modules.IUserModule;
import com.thinkmobiles.android.samples.mvvm.util.commander.CommanderImpl;
import com.thinkmobiles.android.samples.mvvm.util.commander.ICommand;
import com.thinkmobiles.android.samples.mvvm.util.commander.ICommander;

final class AuthModelImpl implements IAuthModel {

    private IAuthViewModel viewModel;

    private final IAuthModule authModule;
    private final IUserModule userModule;

    private final ICommander commander = new CommanderImpl();

    AuthModelImpl(final IAuthModule _authModule, final IUserModule _userModule) {
        authModule = _authModule;
        userModule = _userModule;
    }

    @Override
    public final void setViewModel(final IAuthViewModel _viewModel) {
        viewModel = _viewModel;
        if (commander.hasCommands()) commander.executeCommands();
    }

    @Override
    public final void removeViewModel() {
        viewModel = null;
    }

    @Override
    public void performSignIn() {
        final Credential credential = authModule.getCredential();
        authModule.signIn(credential.email, credential.pass, new IAuthModule.Callback() {
            @Override
            public final void onComplete(boolean isSuccessful, String _msg) {
                final SignInCommand cmd = new SignInCommand(AuthModelImpl.this, isSuccessful, _msg);
                processCommand(cmd);
            }
        });
    }

    @Override
    public void performRegister() {
        final Credential credential = authModule.getCredential();
        authModule.register(credential.email, credential.pass, new IAuthModule.Callback() {
            @Override
            public void onComplete(boolean isSuccessful, String _msg) {
                final RegisterCommand cmd = new RegisterCommand(AuthModelImpl.this, isSuccessful, _msg);
                processCommand(cmd);
            }
        });
    }

    @Override
    public void performSignOut() {
        authModule.signOut(null);
    }

    @Override
    public void setCredential(Credential credential) {
        authModule.setCredential(credential);
    }

    private static final class RegisterCommand implements ICommand {

        private AuthModelImpl mModel;
        private String msg;
        private boolean isSuccessful;

        RegisterCommand(final AuthModelImpl _model, boolean _isSuccessful, final String _msg) {
            mModel = _model;
            isSuccessful = _isSuccessful;
            msg = _msg;
        }

        @Override
        public final void execute() {
            if (isSuccessful) {
                mModel.viewModel.onRegisterSuccess();
            } else {
                mModel.viewModel.onRegisterFailed(msg);
            }

        }
    }

    private static final class SignInCommand implements ICommand {

        private AuthModelImpl mModel;
        private String msg;
        private boolean isSuccessful;

        SignInCommand(final AuthModelImpl _model, boolean _isSuccessful, final String _msg) {
            mModel = _model;
            isSuccessful = _isSuccessful;
            msg = _msg;
        }

        @Override
        public final void execute() {
            if (isSuccessful) {
                mModel.viewModel.onSignSuccess();
            } else {
                mModel.viewModel.onSignFailed(msg);
            }
        }
    }

    private void processCommand(final ICommand _command) {
        if (viewModel != null) {
            _command.execute();
        } else {
            commander.storeCommand(_command);
        }
    }
}
