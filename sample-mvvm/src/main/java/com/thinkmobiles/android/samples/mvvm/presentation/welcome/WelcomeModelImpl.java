package com.thinkmobiles.android.samples.mvvm.presentation.welcome;

import com.thinkmobiles.android.samples.domain.models.CheckIn;
import com.thinkmobiles.android.samples.domain.models.User;
import com.thinkmobiles.android.samples.domain.modules.IAuthModule;
import com.thinkmobiles.android.samples.domain.modules.ICheckInModule;
import com.thinkmobiles.android.samples.domain.modules.IUserModule;
import com.thinkmobiles.android.samples.mvvm.util.commander.CommanderImpl;
import com.thinkmobiles.android.samples.mvvm.util.commander.ICommand;
import com.thinkmobiles.android.samples.mvvm.util.commander.ICommander;

final class WelcomeModelImpl implements IWelcomeModel {

    private IWelcomeViewModel viewModel;

    private final IAuthModule authModule;
    private final IUserModule userModule;
    private final ICheckInModule checkInModule;

    private final ICommander commander = new CommanderImpl();

    WelcomeModelImpl(final IAuthModule _authModule, final IUserModule _userModule, final ICheckInModule _checkInModule) {
        authModule = _authModule;
        userModule = _userModule;
        checkInModule = _checkInModule;
    }

    @Override
    public final void setViewModel(final IWelcomeViewModel _viewModel) {
        viewModel = _viewModel;

        if (commander.hasCommands()) commander.executeCommands();
    }

    @Override
    public final void removeViewModel() {
        viewModel = null;
    }

    @Override
    public void performSignOut() {
        authModule.signOut(null);
        viewModel.onSignedOut();
    }

    @Override
    public void postCheckIn(String _msg) {
        final User user = userModule.getUser();
        final CheckIn checkIn = new CheckIn(user.email, _msg);
        checkInModule.leftNewCheckIn(checkIn, new ICheckInModule.Callback() {
            @Override
            public void onResult(boolean isSuccess, String _msg) {
                final NewCheckInCommand checkInCommand = new NewCheckInCommand(WelcomeModelImpl.this, isSuccess, _msg);
                processCommand(checkInCommand);
            }
        });
    }


    private static final class NewCheckInCommand implements ICommand {

        private WelcomeModelImpl mModel;
        private String msg;
        private boolean isSuccessful;

        NewCheckInCommand(final WelcomeModelImpl _model, boolean _isSuccessful, final String _msg) {
            mModel = _model;
            isSuccessful = _isSuccessful;
            msg = _msg;
        }

        @Override
        public final void execute() {
            if (isSuccessful) {
                mModel.viewModel.onPostSuccess(msg);
            } else {
                mModel.viewModel.onPostFailed(msg);
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
