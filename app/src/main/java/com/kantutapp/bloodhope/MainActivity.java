package com.kantutapp.bloodhope;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ViewPager m_slideView;
    private LinearLayout m_taps; // layout inferior
    private TextView[] mDots;
    private SlideWelcomeAdapter sliderAdapter;
    private Button m_btnNext, m_btnPrev;
    private int mCurrentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_slideView = findViewById(R.id.slideView);
        m_taps =      findViewById(R.id.taps);
        m_btnNext = findViewById(R.id.button2next);
        m_btnPrev = findViewById(R.id.button1prev);

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseUser acct = FirebaseAuth.getInstance().getCurrentUser();
        if (acct != null){
            startActivity(new Intent(MainActivity.this, GeneralActivity.class));
            finish();
        }

        sliderAdapter = new SlideWelcomeAdapter(this);

        m_slideView.setAdapter(sliderAdapter);
        addDots(0);

        m_slideView.addOnPageChangeListener(viewListener);

        //Clicks listener
        m_btnNext.setOnClickListener(view -> {

            if(m_btnNext.getText().toString().equals(getResources().getString(R.string.button_welcome_finish))){
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }else {
                m_slideView.setCurrentItem(mCurrentPage +1);
            }

        });

        m_btnPrev.setOnClickListener(view -> m_slideView.setCurrentItem(mCurrentPage -1));
    }

    public void addDots (int position){
        mDots = new TextView[3];
        m_taps.removeAllViews();

        for(int i = 0 ; i < mDots.length; i++){
            mDots[i] = new TextView( this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            m_taps.addView(mDots[i]);
        }
        if (mDots.length >0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrolled (int i, float v, int i1){

        }
        @Override
        public void onPageSelected (int i ){
            addDots(i);
            mCurrentPage = i;

            if (i==0){
                m_btnNext.setEnabled(true);
                m_btnPrev.setEnabled(false);
                m_btnPrev.setVisibility(View.INVISIBLE);
                m_btnNext.setText(getResources().getString(R.string.button_welcome_next));
                m_btnPrev.setText("");
            }
            else
                if (i==mDots.length - 1){
                m_btnNext.setEnabled(true);
                m_btnPrev.setEnabled(true);
                m_btnPrev.setVisibility(View.VISIBLE);
                m_btnNext.setText(getResources().getString(R.string.button_welcome_finish));
                m_btnPrev.setText(getResources().getString(R.string.button_welcome_previous));
            }
                else {
                    m_btnNext.setEnabled(true);
                    m_btnPrev.setEnabled(true);
                    m_btnPrev.setVisibility(View.VISIBLE);
                    m_btnNext.setText(getResources().getString(R.string.button_welcome_next));
                    m_btnPrev.setText(getResources().getString(R.string.button_welcome_previous));
                }

        }
        @Override
        public void onPageScrollStateChanged (int t){

        }
    };
}
