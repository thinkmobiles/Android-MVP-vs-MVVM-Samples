package com.thinkmobiles.android.samples.mvvm.util.commander;

import java.util.ArrayList;
import java.util.List;

public final class CommanderImpl implements ICommander {

    private List<ICommand> mCommands;

    public CommanderImpl() {
        mCommands = new ArrayList<>();
    }

    @Override
    public void storeCommand(final ICommand _command) {
        mCommands.add(_command);
    }

    @Override
    public boolean hasCommands() {
        return !mCommands.isEmpty();
    }

    @Override
    public void executeCommands() {
        for (final ICommand command : mCommands) command.execute();
        mCommands.clear();
    }

}
