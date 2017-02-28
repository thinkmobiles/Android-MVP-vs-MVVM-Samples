package com.thinkmobiles.android.samples.mvvm.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.thinkmobiles.android.samples.domain.IObjectGraph;
import com.thinkmobiles.android.samples.domain.ObjectGraph;
import com.thinkmobiles.android.samples.mvvm.util.dialog.DialogShower;
import com.thinkmobiles.android.samples.mvvm.util.dialog.IDialogShower;

public abstract class BaseActivity extends AppCompatActivity {

    protected IObjectGraph objectGraph;
    protected IDialogShower dialogShower;

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        objectGraph = ObjectGraph.getInstance();
        dialogShower = new DialogShower();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        objectGraph = null;
        dialogShower = null;
    }

}
