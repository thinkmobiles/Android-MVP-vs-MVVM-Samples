package com.thinkmobiles.android.samples.mvvm.presentation.check_in;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkmobiles.android.samples.domain.models.CheckIn;
import com.thinkmobiles.android.samples.mvvm.R;
import com.thinkmobiles.android.samples.domain.models.User;
import com.thinkmobiles.android.samples.mvvm.databinding.ItemViewCheckInBinding;
import com.thinkmobiles.android.samples.mvvm.databinding.ViewMainCheckInBinding;
import com.thinkmobiles.android.samples.mvvm.util.binding.ObservableFieldAlwaysSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class ViewHelper implements ICheckInView {

    public final ObservableBoolean isRefreshing = new ObservableBoolean(false);
    public final ObservableFieldAlwaysSet<User> user = new ObservableFieldAlwaysSet<>();
    public final ObservableFieldAlwaysSet<String> title = new ObservableFieldAlwaysSet<>();

    private final ICheckInViewModel viewModel;

    public final LinearLayoutManager layoutManager;
    public final CheckInAdapter adapter;

    public ViewHelper(final ICheckInViewModel _viewModel, final ViewMainCheckInBinding binding, final Context _context) {
        viewModel = _viewModel;
        binding.setHelper(this);

        layoutManager = new LinearLayoutManager(_context, LinearLayoutManager.VERTICAL, false);
        adapter = new CheckInAdapter();
    }

    @Override
    public void setUser(User _user) {
        user.set(_user);
    }

    @Override
    public void setTitle(String _title) {
        title.set(_title);
    }

    @Override
    public void setCheckIns(final List<CheckIn> _checkIns) {
        adapter.updateCheckIns(_checkIns);

        if(isRefreshing.get()){
            isRefreshing.set(false);
        }

    }

    public final View.OnClickListener clickFetchAllCheckIns = new View.OnClickListener() {
        @Override
        public final void onClick(final View v) {
            viewModel.onFetchAllCheckInsClicked();
        }
    };

    public final View.OnClickListener clickFetchMyCheckIns = new View.OnClickListener() {
        @Override
        public final void onClick(final View v) {
            viewModel.onFetchMyCheckInsClicked();
        }
    };

    public final SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            isRefreshing.set(true);
            viewModel.onRefreshList();
        }
    };

    public static final class CheckInViewHolder extends RecyclerView.ViewHolder {

        public final ItemViewCheckInBinding binding;

        public final ObservableField<CheckIn> checkInObservableField = new ObservableField<>();

        public final ObservableField<String> checkInDate = new ObservableField<>();


        public CheckInViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.findBinding(itemView);
        }

        public void setBindingVariable() {
            binding.setViewHolder(this);
            binding.executePendingBindings();
        }

        public void setCheckIn(final CheckIn checkIn) {
            checkInObservableField.set(checkIn);
        }

    }

    public static final class CheckInAdapter extends RecyclerView.Adapter<CheckInViewHolder> {

        final List<CheckIn> items = new ArrayList<>();
        final DateFormatter dateFormatter = new DateFormatter();

        final void updateCheckIns(final List<CheckIn> _items) {
            items.clear();
            items.addAll(_items);
            notifyDataSetChanged();
        }

        @Override
        public CheckInViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final ItemViewCheckInBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_view_check_in, parent, false);
            return new CheckInViewHolder(binding.getRoot());
        }

        @Override
        public void onBindViewHolder(CheckInViewHolder holder, int position) {
            final CheckIn checkIn = items.get(position);
            holder.setCheckIn(checkIn);
            holder.checkInDate.set(dateFormatter.formatToDisplayDate(checkIn.timestamp));
            holder.setBindingVariable();
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }


    public static final class DateFormatter {

        private final SimpleDateFormat simpleDateFormat;

        public DateFormatter() {
            simpleDateFormat = new SimpleDateFormat();
        }

        final String formatToDisplayDate(final Long _timestamp){
            if(_timestamp == null) return "";
            final Date date = new Date(_timestamp);

            final String displayDate  = simpleDateFormat.format(date);

            return displayDate;
        }

    }
}
