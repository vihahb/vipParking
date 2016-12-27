package com.xtel.vparking.vip.view.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.google.gson.JsonObject;
import com.xtel.vparking.vip.R;
import com.xtel.vparking.vip.callback.RequestNoResultListener;
import com.xtel.vparking.vip.commons.Constants;
import com.xtel.vparking.vip.commons.GetNewSession;
import com.xtel.vparking.vip.commons.NetWorkInfo;
import com.xtel.vparking.vip.model.entity.Error;
import com.xtel.vparking.vip.model.entity.Favotire;
import com.xtel.vparking.vip.utils.JsonHelper;
import com.xtel.vparking.vip.utils.JsonParse;
import com.xtel.vparking.vip.utils.SharedPreferencesUtils;
import com.xtel.vparking.vip.view.activity.HomeActivity;
import com.xtel.vparking.vip.view.activity.inf.FavoriteView;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Lê Công Long Vũ on 12/5/2016.
 */

public class FavoriteAdapter extends RecyclerSwipeAdapter<FavoriteAdapter.ViewHolder> {
    private ArrayList<Favotire> arrayList;
    private FavoriteView view;
    private ProgressDialog progressDialog;

    public FavoriteAdapter(ArrayList<Favotire> arrayList, FavoriteView view) {
        this.arrayList = arrayList;
        this.view = view;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Favotire favotire = arrayList.get(position);

        holder.txt_name.setText(favotire.getParking_name());
        holder.txt_address.setText(favotire.getAddress());
        holder.txt_money.setText((favotire.getPrice() + " K"));
        holder.txt_time.setText(Constants.getTime(favotire.getBegin(), favotire.getEnd()));

        holder.img_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetWorkInfo.isOnline(view.getActivity())) {
                    view.showShortToast(view.getActivity().getString(R.string.no_internet));
                    return;
                }

                HomeActivity.getView().onViewParkingSelected(favotire.getId());
            }
        });

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetWorkInfo.isOnline(view.getActivity())) {
                    view.showShortToast(view.getActivity().getString(R.string.no_internet));
                    return;
                }

                mItemManger.closeItem(position);
                new RemoveFromFavorite().execute(favotire.getId(), position);
            }
        });

        holder.layout_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mItemManger.isOpen(position))
                    holder.swipeLayout.open();
                else
                    holder.swipeLayout.close();
            }
        });

        mItemManger.bindView(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.item_swipe_favorite;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private SwipeLayout swipeLayout;
        private LinearLayout layout_content;
        private ImageButton img_view, img_delete;
        private TextView txt_name, txt_time, txt_address, txt_money;

        ViewHolder(View itemView) {
            super(itemView);
            layout_content = (LinearLayout) itemView.findViewById(R.id.item_layout_favorite_content);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.item_swipe_favorite);
            img_view = (ImageButton) itemView.findViewById(R.id.item_img_favorite_view);
            img_delete = (ImageButton) itemView.findViewById(R.id.item_img_favorite_delete);
            txt_name = (TextView) itemView.findViewById(R.id.item_txt_favorite_name);
            txt_time = (TextView) itemView.findViewById(R.id.item_txt_favorite_time);
            txt_address = (TextView) itemView.findViewById(R.id.item_txt_favorite_address);
            txt_money = (TextView) itemView.findViewById(R.id.item_txt_favorite_money);
        }
    }

    private class RemoveFromFavorite extends AsyncTask<Integer, Void, String> {
        private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        private int position, id;

        @Override
        protected void onPreExecute() {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(view.getActivity());
                progressDialog.setCancelable(false);
                progressDialog.setMessage(view.getActivity().getString(R.string.doing));
            }
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(Integer... params) {
            this.id = params[0];
            this.position = params[1];
            try {
                OkHttpClient client = new OkHttpClient();

                String session = SharedPreferencesUtils.getInstance().getStringValue(Constants.USER_SESSION);
                String url = Constants.SERVER_PARKING + Constants.PARKING_FAVORITE;

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(Constants.JSON_PARKING_ID, params[0]);

                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .header(Constants.JSON_SESSION, session)
                        .build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            }, 500);

            if (s == null || s.isEmpty()) {
                arrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());

            } else {
                Error error = JsonHelper.getObjectNoException(s, Error.class);

                if (error != null)
                    if (error.getCode() == 2)
                        getNewSessionAddToFavorite(id);
                    else {
                        progressDialog.dismiss();
                        JsonParse.getCodeError(view.getActivity(), null, error.getCode(), "Không thể xóa khỏi danh sách yêu thích");
                    }
                else {
                    progressDialog.dismiss();

                    arrayList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                }
            }

            view.onRemoveItemSuccess();
        }
    }

    private void getNewSessionAddToFavorite(final int id) {
        GetNewSession.getNewSession(view.getActivity(), new RequestNoResultListener() {
            @Override
            public void onSuccess() {
                new RemoveFromFavorite().execute(id);
            }

            @Override
            public void onError() {
                progressDialog.dismiss();
                Toast.makeText(view.getActivity(), "Không thể xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
            }
        });
    }
}