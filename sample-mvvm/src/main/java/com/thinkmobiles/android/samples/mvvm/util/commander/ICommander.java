package com.thinkmobiles.android.samples.mvvm.util.commander;

public interface ICommander {

    void storeCommand(final ICommand _command);

    boolean hasCommands();

    void executeCommands();

}
