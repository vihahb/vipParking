package com.xtel.vparking.vip.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtel.vparking.vip.R;
import com.xtel.vparking.vip.commons.Constants;
import com.xtel.vparking.vip.commons.NetWorkInfo;
import com.xtel.vparking.vip.model.entity.CheckIn;
import com.xtel.vparking.vip.view.activity.inf.CheckedView;

import java.util.ArrayList;

/**
 * Created by Lê Công Long Vũ on 12/10/2016
 */

public class CheckedAdapter extends RecyclerView.Adapter<CheckedAdapter.ViewHolder> {
    private ArrayList<CheckIn> arrayList;
    private CheckedView checkedView;

    public CheckedAdapter(ArrayList<CheckIn> arrayList, CheckedView checkedView) {
        this.arrayList = arrayList;
        this.checkedView = checkedView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checked, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CheckIn checkIn = arrayList.get(position);

        if (checkIn.getCheckin_type() == 1) {
            holder.img_icon.setImageResource(R.drawable.ic_action_car);
        } else if (checkIn.getCheckin_type() == 2) {
            holder.img_icon.setImageResource(R.drawable.ic_action_moto);
        } else {
            holder.img_icon.setImageResource(R.drawable.ic_action_bike);
        }

        holder.txt_name.setText(checkIn.getParking().getParking_name());
        holder.txt_plate_number.setText(checkIn.getVehicle().getPlate_number());
        holder.txt_time.setText(Constants.convertDate(checkIn.getCheckin_time()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetWorkInfo.isOnline(checkedView.getActivity())) {
                    checkedView.showShortToast(checkedView.getActivity().getString(R.string.no_internet));
                    return;
                }

                checkedView.onItemClicked(checkIn);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_name, txt_plate_number, txt_time;
        private ImageView img_icon;

        ViewHolder(View itemView) {
            super(itemView);

            img_icon = (ImageView) itemView.findViewById(R.id.item_img_checked_icon);
            txt_time = (TextView) itemView.findViewById(R.id.item_txt_checked_time);
            txt_name = (TextView) itemView.findViewById(R.id.item_txt_checked_name);
            txt_plate_number = (TextView) itemView.findViewById(R.id.item_txt_checked_car_number_plate);
        }
    }

    public void removeItem(int position) {
        arrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position, getItemCount());
    }
}