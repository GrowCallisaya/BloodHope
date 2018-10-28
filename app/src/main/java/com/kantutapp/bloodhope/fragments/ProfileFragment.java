package com.kantutapp.bloodhope.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kantutapp.bloodhope.R;
import com.kantutapp.bloodhope.adapter.CausesAdapter;
import com.kantutapp.bloodhope.models.Cause;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements CausesAdapter.OnItemClickHandler {

    @BindView(R.id.profile_name) TextView textViewProfileName;
    @BindView(R.id.profile_number_donations) TextView textViewProfileDonations;
    @BindView(R.id.profile_thumbnail) CircleImageView circleImageViewThumbnail;
    @BindView(R.id.recycler_causes) RecyclerView recyclerViewCauses;

    Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mContext = view.getContext();
        ButterKnife.bind(this, view);


        FirebaseUser acct = FirebaseAuth.getInstance().getCurrentUser();
        if (acct != null) {
            setupProfileUI(acct);
        }



        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerViewCauses.setLayoutManager(layoutManager);

        List<Cause> causes = new ArrayList<>();
//        causes.add(new Cause("Cause 1","http://www.cheloproject.ca/wp-content/uploads/2017/03/patient-family-member-hospital-bed-1.jpg",""));
//        causes.add(new Cause("Cause 2","https://hmc.pennstatehealth.org/documents/11396232/11425455/Patients+Families+and+Services/28490a49-bc28-4cb9-a3d3-b483f60385a7?t=1479729028222",""));
//        causes.add(new Cause("Cause 3","https://secure.i.telegraph.co.uk/multimedia/archive/02122/patientBed_2122575b.jpg",""));

        CausesAdapter adapter = new CausesAdapter(causes,mContext, this);
        recyclerViewCauses.setAdapter(adapter);


        return view;
    }

    private void setupProfileUI(FirebaseUser acct) {
        String profileName = acct.getDisplayName();
        String profileEmail = acct.getEmail();
        Uri profilePhoto = acct.getPhotoUrl();

        if (!profileName.isEmpty())
            textViewProfileName.setText(profileName);

        if (!profileEmail.isEmpty())
            textViewProfileDonations.setText(profileEmail);

        if (profilePhoto != null)
        Picasso.get()
                .load(profilePhoto)
                .placeholder(R.drawable.profile)
                .into(circleImageViewThumbnail);
    }


    @Override
    public void onCardClickListener(Cause cause) {
//        Toast.makeText(mContext, "This is " + cause.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteListener(Cause cause) {
//        Toast.makeText(mContext, "Delete " + cause.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditListener(Cause cause) {
//        Toast.makeText(mContext, "Edit " + cause.getName(), Toast.LENGTH_SHORT).show();

    }
}
