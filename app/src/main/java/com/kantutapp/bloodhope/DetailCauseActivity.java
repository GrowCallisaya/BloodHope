package com.kantutapp.bloodhope;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.kantutapp.bloodhope.models.Cause;
import com.kantutapp.bloodhope.utils.Constants;
import com.kantutapp.bloodhope.utils.TextViewMontserratBold;
import com.kantutapp.bloodhope.utils.TextViewMontserratRegular;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.pawelkleczkowski.customgauge.CustomGauge;

public class DetailCauseActivity extends AppCompatActivity {

    @BindView(R.id.detail_toolbar)
    Toolbar detailToolbar;
    @BindView(R.id.detail_cause_image)
    ImageView detailCauseImage;
    @BindView(R.id.detail_cause_title)
    TextViewMontserratBold detailCauseTitle;
    @BindView(R.id.detail_cause_blood_type)
    TextViewMontserratBold detailCauseBloodType;
    @BindView(R.id.detail_cause_description)
    TextViewMontserratRegular detailCauseDescription;
    @BindView(R.id.detail_cause_city_desc)
    TextViewMontserratRegular detailCauseCityDesc;
    @BindView(R.id.detail_cause_hospital_desc)
    TextViewMontserratRegular detailCauseHospitalDesc;
    @BindView(R.id.detail_cause_deadline_desc)
    TextViewMontserratRegular detailCauseDeadlineDesc;
    @BindView(R.id.detail_cause_gauge)
    CustomGauge detailCauseGauge;
    @BindView(R.id.detail_cause_number_donations)
    TextViewMontserratBold detailCauseNumberOfDonations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cause_detail);
        ButterKnife.bind(this);



        final Drawable upArrow =  ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(detailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getIntent().getParcelableExtra(Constants.CAUSE) != null) {
            Cause cause = getIntent().getParcelableExtra(Constants.CAUSE);
            setupCauseUI(cause);
        }


    }

    private void setupCauseUI(Cause cause) {
        Picasso.get()
                .load(cause.getImage())
                .placeholder(R.drawable.cause)
                .into(detailCauseImage);

        detailCauseTitle.setText(cause.getTitle());
        detailCauseDescription.setText(cause.getDescription());
        detailCauseBloodType.setText(cause.getBlood_type());
        detailCauseCityDesc.setText(cause.getCity());
        detailCauseHospitalDesc.setText(cause.getHospital());

        // Gauge
        int valueOfDonations = 0;
        if (cause.getTotal_donations()>0){
            valueOfDonations = cause.getNumber_donations() * (360 / cause.getTotal_donations());
        }
        detailCauseGauge.setPointSize(valueOfDonations);
        detailCauseNumberOfDonations.setText(String.valueOf(cause.getNumber_donations()));
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
