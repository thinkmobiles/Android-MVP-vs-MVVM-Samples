package com.thinkmobiles.android.samples.mvp.screens.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.thinkmobiles.android.samples.domain.ObjectGraph;
import com.thinkmobiles.android.samples.mvp.R;
import com.thinkmobiles.android.samples.mvp.base.BaseFragment;
import com.thinkmobiles.android.samples.mvp.screens.NavigationActivity;
import com.thinkmobiles.android.samples.mvp.utils.Constants;

public class HomeFragment extends BaseFragment<NavigationActivity> implements HomeContract.HomeView {

    private HomeContract.HomePresenter presenter;

    private TextView tvEmail_FH;
    private TextView tvUID_FH;
    private EditText etMessage_FH;
    private TextInputLayout tilMessage_FH;
    private TextView btnBrowse_FH;
    private TextView btnPost_FH;
    private TextView btnSignOut_FH;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tvEmail_FH = (TextView) view.findViewById(R.id.tvEmail_FH);
        tvUID_FH = (TextView) view.findViewById(R.id.tvUID_FH);
        etMessage_FH = (EditText) view.findViewById(R.id.etMessage_FH);
        tilMessage_FH = (TextInputLayout) view.findViewById(R.id.tilMessage_FH);
        btnBrowse_FH = (TextView) view.findViewById(R.id.btnBrowse_FH);
        btnPost_FH = (TextView) view.findViewById(R.id.btnPost_FH);
        btnSignOut_FH = (TextView) view.findViewById(R.id.btnSignOut_FH);

        btnBrowse_FH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.browse();
            }
        });
        btnPost_FH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.post();
                etMessage_FH.setText(null);
            }
        });
        btnSignOut_FH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.signOut();
            }
        });

        mActivity.setToolbarTitle("Welcome");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }

    @Override
    public void displayUID(String uid) {
        tvUID_FH.setText(uid);
    }

    @Override
    public void displayEmail(String email) {
        tvEmail_FH.setText(email);
    }

    @Override
    public String getCheckInMessage() {
        return etMessage_FH.getText().toString().trim();
    }

    @Override
    public void displayErrorCheckInMessage(Constants.ErrorCodes errorCode) {
        switch (errorCode) {
            case FIELD_EMPTY:
                tilMessage_FH.setErrorEnabled(true);
                tilMessage_FH.setError("Message in empty");
                break;
            case FIELD_INVALID:
                tilMessage_FH.setErrorEnabled(true);
                tilMessage_FH.setError("Message should contain only characters and digits and be 2-40 characters long");
                break;
            case OK:
                tilMessage_FH.setErrorEnabled(false);
                tilMessage_FH.setError(null);
                break;
        }
    }

    @Override
    public void startCheckInsScreen() {
        mActivity.displayCheckInsScreen();
    }

    @Override
    public void initPresenter() {
        new HomePresenter(this, ObjectGraph.getInstance().getAuthModule(), ObjectGraph.getInstance().getCheckInModule());
    }

    @Override
    public void setPresenter(HomeContract.HomePresenter presenter) {
        this.presenter = presenter;
    }
}
