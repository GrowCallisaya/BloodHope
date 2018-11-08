package com.kantutapp.bloodhope.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kantutapp.bloodhope.EditCauseActivity;
import com.kantutapp.bloodhope.R;
import com.kantutapp.bloodhope.adapter.CausesAdapter;
import com.kantutapp.bloodhope.models.Cause;
import com.kantutapp.bloodhope.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import mehdi.sakout.fancybuttons.FancyButton;

public class ProfileFragment extends Fragment implements CausesAdapter.OnItemClickHandler {

    @BindView(R.id.profile_name) TextView textViewProfileName;
    @BindView(R.id.profile_number_donations) TextView textViewProfileDonations;
    @BindView(R.id.profile_thumbnail) CircleImageView circleImageViewThumbnail;
    @BindView(R.id.recycler_causes) RecyclerView recyclerViewCauses;
    @BindView(R.id.buttonCreate)    FancyButton btnCreate;
    @BindView(R.id.list_causes_user) RelativeLayout listCuasesUses;
    Context mContext;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference ref = firebaseDatabase.getReference(); // cause
    List<Cause> causes = new ArrayList<>();
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
            setupListOfCauses(acct);
        }


        return view;
    }

    private void setupListOfCauses(FirebaseUser acct) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerViewCauses.setLayoutManager(layoutManager);

        final CausesAdapter adapter = new CausesAdapter(causes, mContext, this);
        recyclerViewCauses.setAdapter(adapter);


        updateUI();


        ref.child(Constants.CAUSES)
                .orderByChild(Constants.CAUSES_USERID)
                .equalTo(acct.getUid())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Cause cause = dataSnapshot.getValue(Cause.class);
                        causes.add(cause);
                        adapter.notifyDataSetChanged();

                        updateUI();

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        adapter.notifyDataSetChanged();
                        updateUI();

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
                });





    }

    private void updateUI() {
        if (causes == null || causes.isEmpty()){
            btnCreate.setVisibility(View.VISIBLE);
            listCuasesUses.setVisibility(View.GONE);
        }
        else{
            btnCreate.setVisibility(View.GONE);
            listCuasesUses.setVisibility(View.VISIBLE);
        }
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
    @OnClick(R.id.buttonCreate)  void createCause() {
        Toast.makeText(mContext, "Create "  , Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), EditCauseActivity.class);
        startActivity(intent);
    }

}
