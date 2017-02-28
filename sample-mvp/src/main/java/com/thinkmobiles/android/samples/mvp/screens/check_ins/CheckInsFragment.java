package com.thinkmobiles.android.samples.mvp.screens.check_ins;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkmobiles.android.samples.domain.ObjectGraph;
import com.thinkmobiles.android.samples.domain.models.CheckIn;
import com.thinkmobiles.android.samples.mvp.R;
import com.thinkmobiles.android.samples.mvp.adapters.CheckInsAdapter;
import com.thinkmobiles.android.samples.mvp.base.BaseFragment;
import com.thinkmobiles.android.samples.mvp.screens.NavigationActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lynx on 2/28/2017.
 */

public class CheckInsFragment extends BaseFragment<NavigationActivity> implements CheckInsContract.CheckInsView {

    private CheckInsContract.CheckInsPresenter presenter;
    private CheckInsAdapter checkInsAdapter;

    private SwipeRefreshLayout srlContent_FCI;
    private TextView btnGetAll_FCI;
    private TextView btnGetMy_FCI;
    private RecyclerView rvCheckIns_FCI;
    private LinearLayout llEmptyPlaceholder_FCI;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkInsAdapter = new CheckInsAdapter();
        initPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_ins, container, false);

        srlContent_FCI = (SwipeRefreshLayout) view.findViewById(R.id.srlContent_FCI);
        btnGetAll_FCI = (TextView) view.findViewById(R.id.btnGetAll_FCI);
        btnGetMy_FCI = (TextView) view.findViewById(R.id.btnGetMy_FCI);
        rvCheckIns_FCI = (RecyclerView) view.findViewById(R.id.rvCheckIns_FCI);
        llEmptyPlaceholder_FCI = (LinearLayout) view.findViewById(R.id.llEmptyPlaceholder_FCI);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvCheckIns_FCI.setLayoutManager(llm);
        rvCheckIns_FCI.setAdapter(checkInsAdapter);

        srlContent_FCI.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlContent_FCI.setRefreshing(true);
                presenter.refresh();
            }
        });

        btnGetAll_FCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getAllCheckIns();
            }
        });
        btnGetMy_FCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getMyCheckIns();
            }
        });

        mActivity.setToolbarTitle("Check Ins");
        return view;
    }

    @Override
    public void displayCheckIns(List<CheckIn> checkIns) {
        if(srlContent_FCI.isRefreshing()) srlContent_FCI.setRefreshing(false);
        checkInsAdapter.clearData();
        checkInsAdapter.setData(new ArrayList<>(checkIns));
    }

    @Override
    public void displayEmptyPlaceholder(boolean isShown) {
        llEmptyPlaceholder_FCI.setVisibility(isShown ? View.VISIBLE : View.GONE);
        rvCheckIns_FCI.setVisibility(isShown ? View.GONE : View.VISIBLE);
    }

    @Override
    public void initPresenter() {
        new CheckInsPresenter(this, ObjectGraph.getInstance().getCheckInModule(), ObjectGraph.getInstance().getAuthModule());
    }

    @Override
    public void setPresenter(CheckInsContract.CheckInsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }
}
