package com.thinkmobiles.android.samples.mvp.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.thinkmobiles.android.samples.domain.ObjectGraph;
import com.thinkmobiles.android.samples.mvp.R;
import com.thinkmobiles.android.samples.mvp.screens.auth.AuthFragment;
import com.thinkmobiles.android.samples.mvp.screens.check_ins.CheckInsFragment;
import com.thinkmobiles.android.samples.mvp.screens.home.HomeFragment;

public final class NavigationActivity extends AppCompatActivity implements NavigationContract.NavigationView {

    private NavigationContract.NavigationPresenter presenter;

    protected Toolbar toolbar_TWI;
    protected ImageView ivOnlineIndicator_TWI;
    protected FrameLayout llMainContainer_AH;

    @Override
    protected final void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        setContentView(R.layout.activity_home);

        toolbar_TWI = (Toolbar) findViewById(R.id.toolbar_TWI);
        ivOnlineIndicator_TWI = (ImageView) findViewById(R.id.ivOnlineIndicator_TWI);
        llMainContainer_AH = (FrameLayout) findViewById(R.id.llMainContainer_AH);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }

    @Override
    public void setToolbarTitle(String title) {
        toolbar_TWI.setTitle(title);
    }

    @Override
    public void displayIndicatorStatus(boolean isAuthorized) {
        ivOnlineIndicator_TWI.setImageResource(isAuthorized ?
                R.drawable.shape_round_green :
                R.drawable.shape_round_red);
    }

    @Override
    public void displayAuthScreen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.llMainContainer_AH, new AuthFragment())
                .commit();
    }

    @Override
    public void displayHomeScreen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.llMainContainer_AH, new HomeFragment())
                .commit();
    }

    @Override
    public void displayCheckInsScreen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.llMainContainer_AH, new CheckInsFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void initPresenter() {
        new NavigationPresenter(this, ObjectGraph.getInstance().getAuthModule());
    }

    @Override
    public void setPresenter(NavigationContract.NavigationPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        else
            super.onBackPressed();
    }
}
