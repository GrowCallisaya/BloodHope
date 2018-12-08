package com.kantutapp.bloodhope.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kantutapp.bloodhope.R;
import com.kantutapp.bloodhope.adapter.SliderPageAdapter;
import com.kantutapp.bloodhope.models.Phrase;
import com.kantutapp.bloodhope.utils.EditTextMontserratRegular;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mehdi.sakout.fancybuttons.FancyButton;

import static com.kantutapp.bloodhope.utils.Constants.PHRASES;

public class PricesFragment extends Fragment {

    @BindView(R.id.btn_claim_gift)
    FancyButton btnClaimGift;
    Unbinder unbinder;


    public Context mContext;
    public List<String> phrases;

    ViewPager viewPager;
    TabLayout indicator;


    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null)
            container.removeAllViews();
        View view = inflater.inflate(R.layout.fragment_prices, container, false);
        mContext = view.getContext();
        unbinder = ButterKnife.bind(this, view);


        viewPager=(ViewPager) view.findViewById(R.id.viewPager);
        indicator=(TabLayout) view.findViewById(R.id.indicator);

        phrases = new ArrayList<>();
        SliderPageAdapter adapter = new SliderPageAdapter(mContext, phrases);
        viewPager.setAdapter(adapter);
        indicator.setupWithViewPager(viewPager, true);


        mDatabaseReference.child(PHRASES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot phraseDataSnapshot :dataSnapshot.getChildren()){

                    Phrase phrase = phraseDataSnapshot.getValue(Phrase.class);
                    if (phrase != null){
                        phrases.add(phrase.getMessage());
                        adapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);



        return view;

    }

    @OnClick(R.id.btn_claim_gift)
    public void claimMyGift(){

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        View mDialogView = getLayoutInflater().inflate(R.layout.dialog_price_contact, null);
        View mDialogView2 = getLayoutInflater().inflate(R.layout.activity_register_user, null);
        EditTextMontserratRegular etAddress = mDialogView.findViewById(R.id.et_address);
        EditTextMontserratRegular etZip= mDialogView.findViewById(R.id.et_zip);
        FancyButton btnSend= mDialogView.findViewById(R.id.btn_send_confirmation);

        builder.setView(mDialogView);
        final AlertDialog dialogContact = builder.create();
        dialogContact.show();

        btnSend.setOnClickListener(v -> {
                dialogContact.dismiss();
                // TODO set in Firebase
        });









    }



    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            getActivity().runOnUiThread(() -> {
                if (viewPager.getCurrentItem() < phrases.size() - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    viewPager.setCurrentItem(0);
                }
            });
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
