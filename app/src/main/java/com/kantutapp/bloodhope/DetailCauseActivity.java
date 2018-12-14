package com.kantutapp.bloodhope;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
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
import com.kantutapp.bloodhope.utils.SpinnerMontserratAdapter;
import com.kantutapp.bloodhope.utils.TextViewMontserratBold;
import com.kantutapp.bloodhope.utils.TextViewMontserratRegular;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;
import pl.pawelkleczkowski.customgauge.CustomGauge;

import static com.kantutapp.bloodhope.utils.Constants.CAUSE;
import static com.kantutapp.bloodhope.utils.Constants.CAUSES;
import static com.kantutapp.bloodhope.utils.Constants.CITY;
import static com.kantutapp.bloodhope.utils.Constants.MODE_EDIT;
import static com.kantutapp.bloodhope.utils.Constants.MODE_VIEW;
import static com.kantutapp.bloodhope.utils.Constants.USERS;
import static com.kantutapp.bloodhope.utils.Constants.USERS_COLLABORATORS;
import static com.kantutapp.bloodhope.utils.Constants.USERS_COLLABORATORS_IDCAUSE;
import static com.kantutapp.bloodhope.utils.Constants.USERS_COLLABORATORS_STATUS;


public class DetailCauseActivity extends AppCompatActivity implements CollaboratorAdapter.onChechboxHandler {

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

    @BindView(R.id.share_facebook)
    ImageView shareFacebook;


    CallbackManager mCallbackManager;
    ShareDialog shareDialog;

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
        if (extras.containsKey(CAUSE)) {
            cause = getIntent().getParcelableExtra(CAUSE);
            if (extras.containsKey(MODE_EDIT))
                editCause();
            if (extras.containsKey(MODE_VIEW))
                viewCause();
        }


        mCallbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

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
            if (cause.getImage() != null) {
                if (!cause.getImage().isEmpty())
                    Picasso.get()
                            .load(cause.getImage())
                            .placeholder(R.drawable.cause)
                            .into(detailCauseImage);
            }

            detailCauseTitle.setText(cause.getTitle());
            detailCauseDescription.setText(cause.getDescription());
            detailCauseBloodType.setText(cause.getBlood_type());
            detailCauseDeadlineDesc.setText(cause.getDeadline());
            mDatabase.child(Constants.CITY).child(cause.getCity_id()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String cityName = dataSnapshot.child("name").getValue(String.class);
                    detailCauseCityDesc.setText(cityName);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) { }
            });

            mDatabase.child(Constants.HOSPITAL).child(cause.getHospital_id()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                        String areaName = dataSnapshot.child("name").getValue(String.class);
                        detailCauseHospitalDesc.setText(areaName);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });



            // Gauge
            int valueOfDonations = 0;
            if (cause.getTotal_donations() > 0) {
                valueOfDonations = cause.getNumber_donations() * (360 / cause.getTotal_donations());
            }
            detailCauseGauge.setPointSize(valueOfDonations);
            String donations = cause.getNumber_donations() + "/" + cause.getTotal_donations();
            detailCauseNumberOfDonations.setText(donations);
        }

    }


    private void setUpCollaboratorsUI() {
        final List<UserCollaborator> userCollaboratorList = new ArrayList<>();
        final CollaboratorAdapter collaboratorAdapter = new CollaboratorAdapter(userCollaboratorList, this, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerCollaborators.setLayoutManager(layoutManager);
        recyclerCollaborators.setAdapter(collaboratorAdapter);

        if (cause != null) {
            mDatabase.child(USERS_COLLABORATORS)
                    .orderByChild(USERS_COLLABORATORS_IDCAUSE)
                    .equalTo(cause.getKey())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            userCollaboratorList.clear();

                            for (DataSnapshot collaboratorSnapshot : dataSnapshot.getChildren()) {
                                final Collaborator collaborator = collaboratorSnapshot.getValue(Collaborator.class);
                                final String collaboratorKey = collaboratorSnapshot.getKey();

                                // Get the User Info from Collaborator
                                if (collaborator != null) {
                                    mDatabase.child(USERS)
                                            .child(collaborator.getId_user())
                                            .addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                    if (dataSnapshot.exists()) {
                                                        User user = dataSnapshot.getValue(User.class);
                                                        user.setKey(dataSnapshot.getKey());

                                                        if (user != null) {
                                                            UserCollaborator userCollaborator =
                                                                    new UserCollaborator(
                                                                            collaboratorKey,
                                                                            user.getPhoto(),
                                                                            user.getName(),
                                                                            user.getEmail(),
                                                                            collaborator.getStatus());

                                                            userCollaborator.setUser(user);
                                                            userCollaborator.setCause(cause);


                                                            userCollaboratorList.add(userCollaborator);
                                                            collaboratorAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                }
                                            });
                                }
                            }
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
            Intent intentToDonateActivity = new Intent(this, DonateActivity.class);
            intentToDonateActivity.putExtra(CAUSE, cause);
            startActivity(intentToDonateActivity);
        }
    }


    @OnClick(R.id.btn_edit_info)
    public void editInfoOfCause() {
        if (cause != null) {
            Intent intentToEditActivity = new Intent(this, EditCauseActivity.class);
            intentToEditActivity.putExtra(CAUSE, cause);
            intentToEditActivity.putExtra(MODE_EDIT, true);
            startActivity(intentToEditActivity);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onCheckBoxSelectedListener(UserCollaborator userCollaborator) {
        // Change status of collaborator
        mDatabase.child(USERS_COLLABORATORS)
                .child(userCollaborator.getKey())
                .child(USERS_COLLABORATORS_STATUS)
                .setValue(true);

        // TODO Send Notification to user

    }

    @Override
    public void addDonationToUser(User user) {
        int donations = user.getNumber_donations();
        donations++;
        mDatabase.child(USERS)
                .child(user.getKey())
                .child("number_donations")
                .setValue(donations);
    }

    @Override
    public void incrementNumberOfDonations(Cause cause) {
        int donations = cause.getNumber_donations();
        donations++;
        mDatabase.child(CAUSES)
                .child(cause.getKey())
                .child("number_donations")
                .setValue(donations);
    }




    @OnClick(R.id.share_facebook)
    public void shareInFacebook(){

//        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
//                .setQuote("Help us Sharing this cause: " + cause.getTitle())
//                .setContentUrl("")
//                .setShareHashtag(new ShareHashtag.Builder()
//                        .setHashtag("#BloodHope")
//                        .build())
//                .build();
//        if(ShareDialog.canShow(ShareLinkContent.class)){
//            shareDialog.show(shareLinkContent);
//        }

//        SharePhoto sharePhoto = null;
//        if (cause.getImage() != null){
//            String imageURL = cause.getImage();
//            sharePhoto = new SharePhoto.Builder()
//                    .setImageUrl(Uri.parse(imageURL))
//                    .build();
//        }else {
//            sharePhoto = new SharePhoto.Builder()
//                    .setImageUrl(Uri.parse("https://s3-sa-east-1.amazonaws.com/iclinic-mkt/blog/2014/08/confianca_medico_paciente.jpg"))
//                    .build();
//        }
//
//
//        if(ShareDialog.canShow(SharePhotoContent.class)){
//            SharePhotoContent content = new SharePhotoContent.Builder()
//                    .addPhoto(sharePhoto)
//                    .build();
//            shareDialog.show(content);
//
//        }
//

        shareDialog.registerCallback(mCallbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(DetailCauseActivity.this, "Shared complete!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Log.e("","A");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("","A");
            }
        });



    }
}
