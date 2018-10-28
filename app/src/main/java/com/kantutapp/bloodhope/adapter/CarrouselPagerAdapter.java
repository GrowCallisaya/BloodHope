package com.kantutapp.bloodhope.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kantutapp.bloodhope.R;
import com.kantutapp.bloodhope.models.Cause;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CarrouselPagerAdapter extends PagerAdapter {


    private Context mContext;
    private ArrayList<Cause> mData;
    private LayoutInflater mLayoutInflater;


    public CarrouselPagerAdapter(final Context context, ArrayList<Cause> data) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = mLayoutInflater.inflate(R.layout.item_causes_carrousel, container, false);
        TextView txtCardTitle = (TextView) view.findViewById(R.id.tv_card_title);
        ImageView imgCardImage = (ImageView) view.findViewById(R.id.iv_card_image);
        txtCardTitle.setText(mData.get(position).getName());
        Picasso.get()
                .load(mData.get(position).getUrl())
                .placeholder(R.drawable.profile)
                .into(imgCardImage);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}
