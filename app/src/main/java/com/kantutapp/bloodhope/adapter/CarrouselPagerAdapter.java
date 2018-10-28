package com.kantutapp.bloodhope.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.kantutapp.bloodhope.R;
import com.kantutapp.bloodhope.models.Cause;
import com.kantutapp.bloodhope.utils.TextViewMontserratBold;
import com.kantutapp.bloodhope.utils.TextViewMontserratRegular;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarrouselPagerAdapter extends PagerAdapter {


    @BindView(R.id.card_image)
    ImageView cardImage;
    @BindView(R.id.card_title)
    TextViewMontserratBold cardTitle;
    @BindView(R.id.card_description)
    TextViewMontserratRegular cardDescription;
    @BindView(R.id.card_deadline)
    TextViewMontserratRegular cardDeadline;
    @BindView(R.id.card_porcentage)
    TextViewMontserratBold cardPorcentage;
    @BindView(R.id.card_progress)
    RoundCornerProgressBar cardProgress;
    @BindView(R.id.card_cause)
    CardView cardCause;


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
        ButterKnife.bind(this, view);

        Cause cause = mData.get(position);
        setupCauseCardUI(cause);

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


    private void setupCauseCardUI(Cause cause) {

        cardTitle.setText(cause.getTitle());
        cardDescription.setText(cause.getDescription());
        cardDeadline.setText(cause.getDeadline());

        Picasso.get()
                .load(cause.getUrl())
                .placeholder(R.drawable.cause)
                .into(cardImage);

        String porcentageValue = String.valueOf(cause.getNumber_donations())
                +"/"+ String.valueOf(cause.getTotal_donations());
        cardPorcentage.setText(porcentageValue);

        cardProgress.setProgress((float) cause.getNumber_donations());
        cardProgress.setMax((float) cause.getTotal_donations());
    }
}
