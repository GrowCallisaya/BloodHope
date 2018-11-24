package com.kantutapp.bloodhope;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kantutapp.bloodhope.models.Cause;
import com.kantutapp.bloodhope.models.Collaborator;
import com.kantutapp.bloodhope.models.Hospital;
import com.kantutapp.bloodhope.models.User;
import com.kantutapp.bloodhope.utils.Constants;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.cketti.mailto.EmailIntentBuilder;

public class DonateActivity extends AppCompatActivity {

    @BindView(R.id.radiogroup_contacts)
    RadioGroup radiogroupContacts;
    @BindView(R.id.radio_mail)
    RadioButton radioMail;
    @BindView(R.id.radio_whatsapp)
    RadioButton radioWhatsapp;


    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public Cause cause;
    public String CAUSE_CONTACT_TYPE = "";
    public String CAUSE_CONTACT_SRC = "";
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public static final String WHATSAPP = "whatsapp";
    public static final String MAIL = "mail";
    @BindView(R.id.mapView)
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_access_token));

        setContentView(R.layout.activity_donate);
        ButterKnife.bind(this);

        checkPermissions();

        mapView.onCreate(savedInstanceState);


        Bundle extras = getIntent().getExtras();
        if (extras.containsKey(Constants.CAUSE)) {
            cause = getIntent().getParcelableExtra(Constants.CAUSE);
            setupMap(cause);
            setupRadioButtons(cause);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    private void setupMap(Cause cause) {
        mDatabase.child(Constants.HOSPITAL)
                .child(cause.getHospital_id())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final Hospital hospital = dataSnapshot.getValue(Hospital.class);

                        if (hospital != null){
                            double latitute = hospital.getLatitutde();
                            double longitude= hospital.getLongitude();
                            String name= hospital.getName();
                            mapView.getMapAsync( mapboxMap -> {

                                Toast.makeText(DonateActivity.this, "lat" + latitute, Toast.LENGTH_SHORT).show();
                                Toast.makeText(DonateActivity.this, "lon" + longitude, Toast.LENGTH_SHORT).show();
                                LatLng coords = new LatLng(latitute, longitude);
                                mapboxMap.addMarker(new MarkerOptions()
                                        .position(coords)
                                        .title(name)
                                        .snippet("Donate here!"));

                                CameraPosition position = new CameraPosition.Builder()
                                        .target(coords)
                                        .zoom(11)
                                        .build();

                                mapboxMap.animateCamera(CameraUpdateFactory
                                        .newCameraPosition(position), 7000);
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void setupRadioButtons(final Cause cause) {
        radioMail.setVisibility(View.GONE);
        radioWhatsapp.setVisibility(View.GONE);
        mDatabase.child(Constants.USERS)
                .child(cause.getUser_id())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            if (user.getPhone_number() != null) {
                                if (user.getPhone_number().length() > 0) {
                                    radioWhatsapp.setVisibility(View.VISIBLE);
                                    radioWhatsapp.setOnClickListener(v -> {
                                        CAUSE_CONTACT_TYPE = WHATSAPP;
                                        CAUSE_CONTACT_SRC = user.getPhone_number();
                                    });
                                }

                            }
                            if (user.getEmail() != null) {
                                if (user.getEmail().length() > 0) {
                                    radioMail.setVisibility(View.VISIBLE);
                                    radioMail.setOnClickListener(v -> {
                                        CAUSE_CONTACT_TYPE = MAIL;
                                        CAUSE_CONTACT_SRC = user.getEmail();
                                    });
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }


    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS, grantResults);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        Toast.makeText(this, "Required permission '" + permissions[index]
                                + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                break;
        }
    }


    @OnClick(R.id.btn_im_in)
    public void contactWithDonor() {
        addCollaborator();
        Toast.makeText(this, "Sending....", Toast.LENGTH_SHORT).show();
        if (CAUSE_CONTACT_TYPE.length() > 0 && CAUSE_CONTACT_SRC.length() > 0) {
            switch (CAUSE_CONTACT_TYPE) {
                case WHATSAPP:
                    sendWhatsapp(CAUSE_CONTACT_SRC);
                    break;
                case MAIL:
                    sendMail(CAUSE_CONTACT_SRC);
                    break;
            }
        }
    }

    /**
     * Method for add a new collaborator in /users_collaborators
     * */
    private void addCollaborator() {
        if (cause != null){
            Collaborator collaborator = new Collaborator();
            collaborator.setId_cause(cause.key);
            collaborator.setId_user(cause.user_id);
            collaborator.setStatus(false);
            Calendar b = Calendar.getInstance();
            int y = b.get(Calendar.YEAR);
            int m = b.get(Calendar.MONTH);
            int d = b.get(Calendar.DAY_OF_MONTH);
            String collaboratorKey = mDatabase.child(Constants.USERS_COLLABORATORS).push().getKey()  + "_" + d + "@" + m + "@" + y;
            mDatabase.child(Constants.USERS_COLLABORATORS).child(collaboratorKey).setValue(collaborator);
        }

    }

    private void sendWhatsapp(String number) {
        // TODO (5) Cambiar el mensaje de whastapp
        try {
            String text = "OUR DEMO TEXT";
            String toNumber = number;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber + "&text=" + text));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendMail(String email) {
        // TODO (4) Cambiar el Subject
        try {
            Intent emailIntent = EmailIntentBuilder.from(this)
                    .to(email)
                    .subject("FOUR DEMO SUBJECT")
                    .body(buildHTML())
                    .build();
            startActivity(emailIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String buildHTML() {
        // TODO (3) Cambiar los datos de respuesta
        return "Hey felicidades ya estas en la app";
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }



}
