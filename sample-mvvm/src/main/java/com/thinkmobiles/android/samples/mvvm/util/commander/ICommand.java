package com.thinkmobiles.android.samples.mvvm.util.commander;

import java.io.Serializable;

public interface ICommand extends Serializable {
    void execute();
}
