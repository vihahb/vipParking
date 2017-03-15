package com.xtel.vparking.vip.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.xtel.vparking.vip.R;
import com.xtel.vparking.vip.commons.Constants;
import com.xtel.vparking.vip.view.widget.TouchImageView;

/**
 * Created by Lê Công Long Vũ on 11/10/2016.
 */

public class ViewImageBottomSheetFragment extends Fragment {
    private TouchImageView imageView;
    private ProgressBar progressBar;
    private String url;

    public static ViewImageBottomSheetFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(Constants.PK_IMAGE, url);
        ViewImageBottomSheetFragment fragment = new ViewImageBottomSheetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_bottom_sheet_imageview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (imageView == null)
            imageView = (TouchImageView) view.findViewById(R.id.item_bottom_sheet_img_imageview);
        if (progressBar == null)
            progressBar = (ProgressBar) view.findViewById(R.id.item_bottom_sheet_progress);

        url = getArguments().getString(Constants.PK_IMAGE);

        if (progressBar != null)
            progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#5c5ca7"), android.graphics.PorterDuff.Mode.MULTIPLY);

        if (url != null)
            Picasso.with(getContext())
                    .load(url)
                    .noPlaceholder()
                    .error(R.mipmap.ic_parking_background)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
        else
            imageView.setImageResource(R.mipmap.ic_parking_background);
    }
}
