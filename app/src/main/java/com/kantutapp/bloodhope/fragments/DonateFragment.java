package com.kantutapp.bloodhope.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.kantutapp.bloodhope.R;
import com.kantutapp.bloodhope.adapter.CarrouselPagerAdapter;
import com.kantutapp.bloodhope.models.Cause;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DonateFragment extends Fragment {


    @BindView(R.id.card_pager) HorizontalInfiniteCycleViewPager cardPager;
   // @BindView(R.id.buttonCreate) Button btnCreate;
    Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public View mView;
    public Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_donate, container, false);
        mContext = mView.getContext();
        unbinder = ButterKnife.bind(this, mView);


        // TODO Get data from Firebase
        ArrayList<Cause> causes = new ArrayList<>();

        causes.add(new Cause("Cause 1", "http://www.cheloproject.ca/wp-content/uploads/2017/03/patient-family-member-hospital-bed-1.jpg", "","This is some decription This is some decription This is some decription This is some decription This is some decription",3,3,"A+","Florida","AZ","user1","12/12/2018"));

        causes.add(new Cause("Cause 2", "https://hmc.pennstatehealth.org/documents/11396232/11425455/Patients+Families+and+Services/28490a49-bc28-4cb9-a3d3-b483f60385a7?t=1479729028222", "","",3,10,"A+","Florida","AZ","user1","12/12/2018"));
        causes.add(new Cause("Cause 3", "https://secure.i.telegraph.co.uk/multimedia/archive/02122/patientBed_2122575b.jpg", "","",3,10,"A+","Florida","AZ","user1","12/12/2018"));
        causes.add(new Cause("Cause 4", "http://www.cheloproject.ca/wp-content/uploads/2017/03/patient-family-member-hospital-bed-1.jpg", "","",3,100,"A+","Florida","AZ","user1","12/12/2018"));
        causes.add(new Cause("Cause 5", "http://www.cheloproject.ca/wp-content/uploads/2017/03/patient-family-member-hospital-bed-1.jpg", "","This is some decription",3,10,"A+","Florida","AZ","user1","12/12/2018"));
        causes.add(new Cause("Cause 6", "http://www.cheloproject.ca/wp-content/uploads/2017/03/patient-family-member-hospital-bed-1.jpg", "","This is some decription",3,7,"A+","Florida","AZ","user1","12/12/2018"));
        causes.add(new Cause("Cause 7", "http://www.cheloproject.ca/wp-content/uploads/2017/03/patient-family-member-hospital-bed-1.jpg", "","This is some decription",3,5,"A+","Florida","AZ","user1","12/12/2018"));


        CarrouselPagerAdapter carrouselPagerAdapter = new CarrouselPagerAdapter(getContext(), causes);
         cardPager.setAdapter(carrouselPagerAdapter);




        return mView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
