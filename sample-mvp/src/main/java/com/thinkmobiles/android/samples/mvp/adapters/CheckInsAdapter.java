package com.thinkmobiles.android.samples.mvp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thinkmobiles.android.samples.domain.models.CheckIn;
import com.thinkmobiles.android.samples.mvp.R;

import java.util.ArrayList;

/**
 * Created by Lynx on 2/28/2017.
 */

public class CheckInsAdapter extends RecyclerView.Adapter<CheckInVH> {

    private ArrayList<CheckIn> data = new ArrayList<>();

    @Override
    public CheckInVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new CheckInVH(inflater.inflate(R.layout.list_item_check_in, parent, false));
    }

    @Override
    public void onBindViewHolder(CheckInVH holder, int position) {
        holder.bindData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<CheckIn> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void clearData() {
        data.clear();
        notifyDataSetChanged();
    }
}
