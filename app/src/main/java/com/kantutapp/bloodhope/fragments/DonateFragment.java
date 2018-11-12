package com.kantutapp.bloodhope.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.kantutapp.bloodhope.DetailCauseActivity;
import com.kantutapp.bloodhope.R;
import com.kantutapp.bloodhope.adapter.CarrouselPagerAdapter;
import com.kantutapp.bloodhope.models.Cause;
import com.kantutapp.bloodhope.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DonateFragment extends Fragment implements CarrouselPagerAdapter.OnItemCauseClickListener {


    @BindView(R.id.card_pager)
    HorizontalInfiniteCycleViewPager cardPager;
    // @BindView(R.id.buttonCreate) Button btnCreate;
    Unbinder unbinder;
    @BindView(R.id.donate_progressbar)
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View mView;
    public Context mContext;

    public ArrayList<Cause> causes = new ArrayList<>();

    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_donate, container, false);
        mContext = mView.getContext();
        unbinder = ButterKnife.bind(this, mView);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        CarrouselPagerAdapter carrouselPagerAdapter = new CarrouselPagerAdapter(getContext(), causes, this);
        cardPager.setAdapter(carrouselPagerAdapter);


        return mView;
    }


    @Override
    public void onStart() {
        super.onStart();

        Query query = mDatabase.child(Constants.CAUSES);


        progressBar.setVisibility(View.VISIBLE);
        cardPager.setVisibility(View.GONE);

        ChildEventListener eventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Cause cause = dataSnapshot.getValue(Cause.class);
                cause.setKey(dataSnapshot.getKey());
                causes.add(cause);
                progressBar.setVisibility(View.GONE);
                cardPager.setVisibility(View.VISIBLE);
                if (cardPager != null)
                    cardPager.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (cardPager != null)
                    cardPager.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        query.addChildEventListener(eventListener);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCauseClicked(Cause cause) {
        Intent intent = new Intent(mContext, DetailCauseActivity.class);
        intent.putExtra(Constants.CAUSE, cause);
        intent.putExtra(Constants.MODE_VIEW, true);
        startActivity(intent);
    }
}
