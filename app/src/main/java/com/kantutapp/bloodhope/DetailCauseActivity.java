package com.kantutapp.bloodhope;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kantutapp.bloodhope.adapter.CollaboratorAdapter;
import com.kantutapp.bloodhope.models.Cause;
import com.kantutapp.bloodhope.models.Collaborator;
import com.kantutapp.bloodhope.models.User;
import com.kantutapp.bloodhope.models.UserCollaborator;
import com.kantutapp.bloodhope.utils.Constants;
import com.kantutapp.bloodhope.utils.TextViewMontserratBold;
import com.kantutapp.bloodhope.utils.TextViewMontserratRegular;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.pawelkleczkowski.customgauge.CustomGauge;

public class DetailCauseActivity extends AppCompatActivity {

    public static final String EXTRA_CAUSE_KEY = "cause_key";

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
    @BindView(R.id.content_edit)
    LinearLayout contentEdit;
    @BindView(R.id.content_ver)
    LinearLayout contentVer;
    @BindView(R.id.recycler_collaborators)
    RecyclerView recyclerCollaborators;

    public Cause cause;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cause_detail);
        ButterKnife.bind(this);


        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(detailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Bundle extras = getIntent().getExtras();
        if (extras.containsKey(Constants.CAUSE)) {
            cause = getIntent().getParcelableExtra(Constants.CAUSE);
            if (extras.containsKey(Constants.MODE_EDIT))
                editCause();
            if (extras.containsKey(Constants.MODE_VIEW))
                viewCause();
        }
    }


    private void editCause() {
        contentEdit.setVisibility(View.VISIBLE);
        contentVer.setVisibility(View.GONE);
        setupCauseUI();
        setUpCollaboratorsUI();
    }


    private void viewCause() {
        contentEdit.setVisibility(View.GONE);
        contentVer.setVisibility(View.VISIBLE);
        setupCauseUI();
    }


    private void setupCauseUI() {
        if (cause != null) {
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
            if (cause.getTotal_donations() > 0) {
                valueOfDonations = cause.getNumber_donations() * (360 / cause.getTotal_donations());
            }
            detailCauseGauge.setPointSize(valueOfDonations);
            detailCauseNumberOfDonations.setText(String.valueOf(cause.getNumber_donations()));
        }

    }


    private void setUpCollaboratorsUI() {
        final List<UserCollaborator> userCollaborators = new ArrayList<>();
        final CollaboratorAdapter adapter = new CollaboratorAdapter(userCollaborators,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerCollaborators.setLayoutManager(layoutManager);
        recyclerCollaborators.setAdapter(adapter);

        if (cause != null) {
            mDatabase.child(Constants.USERS_COLLABORATORS)
                    .orderByChild("id_cause")
                    .equalTo(cause.getKey())
                    .addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            final Collaborator collaborator = dataSnapshot.getValue(Collaborator.class);

                            // Get the User Info from Collaborator
                            mDatabase.child(Constants.USERS).child(collaborator.getId_user()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    User user = dataSnapshot.getValue(User.class);

                                    if (user != null) {
                                        Log.e("Gool", user.getName());
                                        UserCollaborator userCollaborator =
                                                new UserCollaborator(
                                                        user.getPhoto(),
                                                        user.getName(),
                                                        user.getPhone_number(),
                                                        collaborator.getStatus());

                                        userCollaborators.add(userCollaborator);
                                        adapter.notifyDataSetChanged();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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

    }


    @OnClick(R.id.btn_donate)
    public void donateCause() {
        if (cause != null) {

        }
    }


    @OnClick(R.id.btn_edit_info)
    public void editInfoOfCause() {
        if (cause != null) {
            Intent intentToEditActivity = new Intent(this, EditCauseActivity.class);
            intentToEditActivity.putExtra(Constants.CAUSE, cause);
            startActivity(intentToEditActivity);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
