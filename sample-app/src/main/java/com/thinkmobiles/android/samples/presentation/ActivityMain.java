package com.thinkmobiles.android.samples.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.thinkmobiles.android.samples.R;
import com.thinkmobiles.android.samples.mvp.screens.NavigationActivity;
import com.thinkmobiles.android.samples.mvvm.presentation.navigator.MainNavigatorViewModelImpl;

public final class ActivityMain extends AppCompatActivity {

    private Button btnMVVM, btnMVP;

    private final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public final void onClick(final View v) {
            switch (v.getId()) {
                case R.id.btn_MVP:
                    final Intent mvpIntent = new Intent(ActivityMain.this, NavigationActivity.class);
                    startActivity(mvpIntent);
                    break;
                case R.id.btn_MVVM:
                    final Intent mvvmIntent = new Intent(ActivityMain.this, MainNavigatorViewModelImpl.class);
                    startActivity(mvvmIntent);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMVP = (Button) findViewById(R.id.btn_MVP);
        btnMVVM = (Button) findViewById(R.id.btn_MVVM);

        btnMVP.setOnClickListener(clickListener);
        btnMVVM.setOnClickListener(clickListener);
    }

    @Override
    protected final void onDestroy() {
        super.onDestroy();
        btnMVP = null;
        btnMVVM = null;
    }
}
