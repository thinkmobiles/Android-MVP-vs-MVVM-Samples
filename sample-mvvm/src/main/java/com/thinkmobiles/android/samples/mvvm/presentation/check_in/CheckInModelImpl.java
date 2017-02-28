package com.thinkmobiles.android.samples.mvvm.presentation.check_in;

import com.thinkmobiles.android.samples.domain.models.CheckIn;
import com.thinkmobiles.android.samples.domain.models.User;
import com.thinkmobiles.android.samples.domain.modules.ICheckInModule;
import com.thinkmobiles.android.samples.domain.modules.IUserModule;
import com.thinkmobiles.android.samples.mvvm.util.commander.CommanderImpl;
import com.thinkmobiles.android.samples.mvvm.util.commander.ICommand;
import com.thinkmobiles.android.samples.mvvm.util.commander.ICommander;

import java.util.List;

final class CheckInModelImpl implements ICheckInModel {

    private ICheckInViewModel viewModel;

    private final IUserModule userModule;
    private final ICheckInModule checkInModule;

    private final ICommander commander = new CommanderImpl();

    private State stateCheckIn = State.ALL; // Default

    CheckInModelImpl(final IUserModule _userModule, final ICheckInModule _checkInModule) {
        userModule = _userModule;
        checkInModule = _checkInModule;
    }

    @Override
    public final void setViewModel(final ICheckInViewModel _viewModel) {
        viewModel = _viewModel;
        if (commander.hasCommands()) commander.executeCommands();
    }

    @Override
    public final void removeViewModel() {
        viewModel = null;
    }

    @Override
    public void loadAllCheckIns(boolean doReload) {
        stateCheckIn = State.ALL;
        final List<CheckIn> checkInList = checkInModule.getAllCheckInList();

        if (doReload || checkInList.isEmpty()) {
            checkInModule.loadAllCheckIn(new ICheckInModule.Callback() {
                @Override
                public final void onResult(final boolean isSuccess, final String _msg) {
                    final CheckInAllFetchCommand cmd = new CheckInAllFetchCommand(CheckInModelImpl.this, isSuccess, _msg);
                    processCommand(cmd);
                }
            });
        } else {
            viewModel.fetchAllSuccess(checkInList);
        }

    }

    @Override
    public void loadMyCheckIns() {
        stateCheckIn = State.MY;
        final User user = userModule.getUser();
        checkInModule.loadCheckInByEmail(user.email, new ICheckInModule.Callback() {
            @Override
            public void onResult(boolean isSuccess, String _msg) {
                final CheckMyFetchCommand cmd = new CheckMyFetchCommand(CheckInModelImpl.this, isSuccess, _msg);
                processCommand(cmd);
            }
        });
    }

    @Override
    public State getState() {
        return stateCheckIn;
    }

    private static final class CheckInAllFetchCommand implements ICommand {

        private CheckInModelImpl mModel;
        private String msg;
        private boolean isSuccessful;

        CheckInAllFetchCommand(final CheckInModelImpl _model, boolean _isSuccessful, final String _msg) {
            mModel = _model;
            isSuccessful = _isSuccessful;
            msg = _msg;
        }

        @Override
        public final void execute() {
            if (isSuccessful) {
                mModel.viewModel.fetchAllSuccess(mModel.checkInModule.getAllCheckInList());
            } else {
                mModel.viewModel.fetchAllFailed(msg);
            }
        }
    }

    private static final class CheckMyFetchCommand implements ICommand {

        private CheckInModelImpl mModel;
        private String msg;
        private boolean isSuccessful;

        CheckMyFetchCommand(final CheckInModelImpl _model, boolean _isSuccessful, final String _msg) {
            mModel = _model;
            isSuccessful = _isSuccessful;
            msg = _msg;
        }

        @Override
        public final void execute() {
            if (isSuccessful) {
                mModel.viewModel.fetchMySuccess(mModel.checkInModule.getMyCheckInList());
            } else {
                mModel.viewModel.fetchMyFailed(msg);
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
