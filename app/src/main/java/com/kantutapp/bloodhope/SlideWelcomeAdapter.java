package com.kantutapp.bloodhope;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SlideWelcomeAdapter extends PagerAdapter{

    Context context;
    LayoutInflater layoutInflater;

    public SlideWelcomeAdapter(Context context){
        this.context = context;
    }

    public int [] slide_images ={
            R.drawable.screen_one_icon,
            R.drawable.screen_two_icon,
            R.drawable.screen_three_icon
    };

    public String [] slide_text1 ={
           " Where ",
            "Why",
            "When"
    };

    public String  [] slide_text2 ={
            "Here ....",
            "Why not .... ",
            "Now ..."
    };
    @Override
    public int getCount() {
        return slide_text1.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)  context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_welcome_layout, container, false);

        ImageView slide_images_1= view.findViewById(R.id.slideImage);
        TextView slide_text1_1  = view.findViewById(R.id.slideText1);
        TextView slide_text2_1  = view.findViewById(R.id.slideText2);

        slide_images_1.setImageResource(slide_images[position]);
        slide_text1_1.setText(slide_text1[position]);
        slide_text2_1.setText(slide_text2[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((View) object);
    }
}
