package com.kantutapp.bloodhope;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kantutapp.bloodhope.utils.TextViewMontserratBold;
import com.kantutapp.bloodhope.utils.TextViewMontserratRegular;

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

    public int [] slide_bakground ={
            R.drawable.screen_one,
            R.drawable.screen_two,
            R.drawable.screen_three
    };

    public String [] slideTitles ={
           " Donate Blood",
            "Create a cause and find donors",
            "Make a community"
    };

    public String  [] slideDescriptions ={
            "This is your opportunity to give hope to somebody. Have a try, somebody is waiting for you.",
            "If you need blood, you can create your cause and share with others to find help! There is always a good heart",
            "Reach a big community of people who wants to help others!"
    };
    @Override
    public int getCount() {
        return slideTitles.length;
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

        ImageView slideBackground= view.findViewById(R.id.slideImage);
        ImageView slideIcon= view.findViewById(R.id.slideImage);
        TextViewMontserratBold slideTextTitle  = view.findViewById(R.id.slideText1);
        TextViewMontserratRegular slideTextDescription = view.findViewById(R.id.slideText2);

//        slideBackground.setImageResource(slide_bakground[position]);
        slideIcon.setImageResource(slide_images[position]);
        slideTextTitle.setText(slideTitles[position]);
        slideTextDescription.setText(slideDescriptions[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((View) object);
    }
}
