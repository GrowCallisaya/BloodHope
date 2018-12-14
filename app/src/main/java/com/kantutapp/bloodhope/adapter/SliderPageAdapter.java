package com.kantutapp.bloodhope.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kantutapp.bloodhope.R;
import com.kantutapp.bloodhope.utils.TextViewMontserratBold;

import java.util.List;

/**
 * Created by growcallisaya on 7/12/18.
 */

public class SliderPageAdapter extends PagerAdapter {

    public Context context;
    public  List<String> phrases;

    public SliderPageAdapter(Context context, List<String> phrases) {
        this.context = context;
        this.phrases= phrases;
    }

    @Override
    public int getCount() {
        return phrases.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewSlider = inflater.inflate(R.layout.item_slider, null);

        TextViewMontserratBold textView = viewSlider.findViewById(R.id.texto);
        textView.setText(phrases.get(position));

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(viewSlider, 0);

        return viewSlider;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
