package com.xtel.vparking.vip.view.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xtel.vparking.vip.R;
import com.xtel.vparking.vip.commons.Constants;
import com.xtel.vparking.vip.commons.NetWorkInfo;
import com.xtel.vparking.vip.model.entity.ParkingCheckIn;
import com.xtel.vparking.vip.view.MyApplication;
import com.xtel.vparking.vip.view.activity.inf.IViewCheckIn;

import java.util.ArrayList;

/**
 * Created by Lê Công Long Vũ on 12/10/2016.
 */

public class ViewCheckInAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ParkingCheckIn> arrayList;
    private IViewCheckIn checkedView;
    private boolean isLoadMore = true;

    private static final int view_item = 1, view_progress = 2;

    public ViewCheckInAdapter(ArrayList<ParkingCheckIn> arrayList, IViewCheckIn checkedView) {
        this.arrayList = arrayList;
        this.checkedView = checkedView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == view_item)
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_check_in, parent, false));
        else
            return new ViewProgress(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progressbar, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder view = (ViewHolder) holder;
            final ParkingCheckIn checkIn = arrayList.get(position);

            if (checkIn.getCheckin_type() == 1) {
                view.img_icon.setImageResource(R.drawable.ic_action_car);
            } else if (checkIn.getCheckin_type() == 2) {
                view.img_icon.setImageResource(R.drawable.ic_action_moto);
            } else {
                view.img_icon.setImageResource(R.drawable.ic_action_bike);
            }

//            if (checkIn.getUser().getFullname() == null || checkIn.getUser().getFullname().isEmpty())
//                view.txt_name.setText(MyApplication.context.getString(R.string.not_update));
//            else
            view.txt_name.setText(Constants.getUserName(""));

            view.txt_time.setText(Constants.convertDataTime(checkIn.getCheckin_time()));
            view.txt_plate_number.setText(checkIn.getVehicle().getPlate_number());

//            if (checkIn.getUser().getPhone() == null)
//                view.txt_phone.setText(checkedView.getActivity().getString(R.string.not_update));
//            else
            view.txt_phone.setText(Constants.getUserPhone(""));

            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!NetWorkInfo.isOnline(checkedView.getActivity())) {
                        checkedView.showShortToast(checkedView.getActivity().getString(R.string.no_internet));
                        return;
                    }

                    checkedView.onItemClicked(checkIn);
                }
            });
        } else {
            ViewProgress view = (ViewProgress) holder;
            view.progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#5c5ca7"), android.graphics.PorterDuff.Mode.MULTIPLY);
        }
    }

    @Override
    public int getItemCount() {
        if (isLoadMore && arrayList.size() > 0)
            return arrayList.size() + 1;
        else
            return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == arrayList.size())
            return view_progress;
        else
            return view_item;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_name, txt_time, txt_phone, txt_plate_number;
        private ImageView img_icon;

        ViewHolder(View itemView) {
            super(itemView);

            img_icon = (ImageView) itemView.findViewById(R.id.item_img_view_check_in_icon);
            txt_time = (TextView) itemView.findViewById(R.id.item_txt_view_check_in_time);
            txt_name = (TextView) itemView.findViewById(R.id.item_txt_view_check_in_name);
            txt_phone = (TextView) itemView.findViewById(R.id.item_txt_view_check_in_phone);
            txt_plate_number = (TextView) itemView.findViewById(R.id.item_view_check_in_txt_number_plate);
        }
    }

    private class ViewProgress extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        ViewProgress(View itemView) {
            super(itemView);

            progressBar = (ProgressBar) itemView.findViewById(R.id.item_progress_bar);
        }
    }

    public void setLoadMore(boolean isLoad) {
        isLoadMore = isLoad;
    }
}