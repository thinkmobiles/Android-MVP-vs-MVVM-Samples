package com.thinkmobiles.android.samples.mvp.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.thinkmobiles.android.samples.domain.models.CheckIn;
import com.thinkmobiles.android.samples.mvp.R;

/**
 * Created by Lynx on 2/28/2017.
 */

public class CheckInVH extends RecyclerView.ViewHolder {

    private TextView tvMsg_LICI;
    private TextView tvAuthor_LICI;

    public CheckInVH(View itemView) {
        super(itemView);

        tvMsg_LICI = (TextView) itemView.findViewById(R.id.tvMsg_LICI);
        tvAuthor_LICI = (TextView) itemView.findViewById(R.id.tvAuthor_LICI);
    }

    public void bindData(CheckIn checkIn) {
        tvMsg_LICI.setText(TextUtils.isEmpty(checkIn.checkInMessage) ? "Empty" : checkIn.checkInMessage);
        tvAuthor_LICI.setText(TextUtils.isEmpty(checkIn.email) ? "Anonymous" : checkIn.email);
    }
}
