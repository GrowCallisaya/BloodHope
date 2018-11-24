package com.kantutapp.bloodhope;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kantutapp.bloodhope.adapter.BloodAdapter;
import com.kantutapp.bloodhope.models.Cause;
import com.kantutapp.bloodhope.models.User;
import com.kantutapp.bloodhope.utils.Constants;
import com.kantutapp.bloodhope.utils.EditTextMontserratRegular;
import com.kantutapp.bloodhope.utils.SpinnerMontserratAdapter;
import com.kantutapp.bloodhope.utils.TextViewMontserratBold;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static com.kantutapp.bloodhope.utils.Constants.CAUSE;
import static com.kantutapp.bloodhope.utils.Constants.MODE_CREATE;
import static com.kantutapp.bloodhope.utils.Constants.MODE_EDIT;

public class EditCauseActivity extends AppCompatActivity implements View.OnClickListener,
        BloodAdapter.OnBloodClickHandler {


    private static final String TAG = EditCauseActivity.class.getSimpleName();
    private static final int NUMBER_COLS = 6;
    private static final String USER = "User";
    private static final int YEAR_LENGTH = 4, YEAR_MONTH_LENGTH = 2;



    //Valider Date
    private Pattern pattern;
    private Matcher matcher;
    private static final String DATE_PATTERN = "(0?[1-9]|1[012]) [/.-] (0?[1-9]|[12][0-9]|3[01]) [/.-] ((19|20)\\d\\d)";

    //initializing firebase authentication object
    private User currentUser = new User();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference(); //city_id
    FirebaseUser firebaseUser;



    @BindView(R.id.create_toolbar)
    Toolbar createToolbar;
    @BindView(R.id.recycler_blood_types)
    RecyclerView recyclerBloodTypes;
    @BindView(R.id.et_name)
    EditTextMontserratRegular etName;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_donations)
    EditText etDonations;
    @BindView(R.id.et_deadline)
    EditText etDeadline;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_story)
    EditText etStory;
    @BindView(R.id.spinner)
    Spinner sCity;
    @BindView(R.id.spinner2)
    Spinner sHospital;
    @BindView(R.id.btn_submit)
    FancyButton btnSubmit;
    @BindView(R.id.btn_photo)
    FancyButton btnPhoto;
    @BindView(R.id.iv_cause_photo)
    ImageView ivCausePhoto;
    @BindView(R.id.toolbar_title)
    TextViewMontserratBold toolbarTitle;


    public Cause cause;
    final List<String> cities = new ArrayList();
    final List<String> hospitals = new ArrayList();

    final HashMap<String,String> citiesMap = new HashMap();
    final HashMap<String,String> hospitalsMap = new HashMap();


    private Uri filePath;
    private String currentBloodtypeSelected = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cause);
        ButterKnife.bind(this);

        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.colorBlack), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(createToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Bundle extras = getIntent().getExtras();
        if (extras.containsKey(MODE_CREATE)) {
            createCauseInfo();
        } else if (extras.containsKey(MODE_EDIT)) {
            if (extras.containsKey(CAUSE)) {
                cause = getIntent().getParcelableExtra(CAUSE);
                editCauseInfo(cause);
            }
        }

        btnSubmit.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);
        ivCausePhoto.setOnClickListener(this);
    }


    private void editCauseInfo(Cause cause) {
        toolbarTitle.setText("Edit Cause");

        // Deadline (Cause)
        if (!cause.getDeadline().isEmpty())
            etDeadline.setText(cause.getDeadline());

        // City (Cause)

        databaseReference.child(Constants.CITY).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> areas = new ArrayList<String>();
                int position = 0;
                int positionSelected = 0;
                for (DataSnapshot citySnapshot : dataSnapshot.getChildren()) {
                    String cityName = citySnapshot.child("name").getValue(String.class);
                    String cityKey = citySnapshot.getKey();
                    if (cityKey.equals(cause.city_id)){
                        positionSelected = position;
                    }
                    position++;
                    areas.add(cityName);
                    citiesMap.put(cityName, cityKey);
                }

                Spinner areaSpinner = findViewById(R.id.spinner);
                SpinnerMontserratAdapter areasAdapter = new SpinnerMontserratAdapter(EditCauseActivity.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
                areaSpinner.setSelection(positionSelected);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Hospital (Cause)

        databaseReference.child(Constants.HOSPITAL).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> areas = new ArrayList<String>();
                int position = 0;
                int positionSelected = 0;
                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("name").getValue(String.class);
                    String areaKey = areaSnapshot.getKey();

                    if (areaKey.equals(cause.hospital_id)){
                        positionSelected = position;
                    }

                    position++;
                    areas.add(areaName);
                    hospitalsMap.put(areaName,areaKey);
                }

                Spinner areaSpinner = findViewById(R.id.spinner2);
                SpinnerMontserratAdapter areasAdapter = new SpinnerMontserratAdapter(EditCauseActivity.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
                areaSpinner.setSelection(positionSelected);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Blood Group (Cause)

        List<String> bloodTypes = new ArrayList<>();
        bloodTypes.add("A+");
        bloodTypes.add("A-");
        bloodTypes.add("B-");
        bloodTypes.add("B+");
        bloodTypes.add("O+");
        bloodTypes.add("O-");
        bloodTypes.add("AB-");
        bloodTypes.add("AB+");


        // Setting the position
        BloodAdapter adapter = new BloodAdapter(bloodTypes, this, this);

        HashMap<String,Integer> map = new HashMap();
        for (int i= 0; i < bloodTypes.size(); i ++)
                map.put(bloodTypes.get(i) , i);

        if (map.containsKey(cause.getBlood_type()))
            adapter.positionBloodSelected = map.get(cause.getBlood_type());

        recyclerBloodTypes.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NUMBER_COLS);
        recyclerBloodTypes.setLayoutManager(gridLayoutManager);


        // Name (User)
        // Mobile (User)
        databaseReference.child(Constants.USERS)
                .child(cause.getUser_id())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            if (!user.getName().isEmpty())
                                etName.setText(user.getName());

                            if (!user.getPhone_number().isEmpty())
                                etMobile.setText(user.getPhone_number());

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        // Total of Donations (Cause)
        if (cause.getTotal_donations() > 0)
            etDonations.setText(String.valueOf(cause.getTotal_donations()));

        // Title (Cause)
        if (!cause.getTitle().isEmpty())
            etTitle.setText(cause.getTitle());

        // Story (Cause)
        if (!cause.getDescription().isEmpty())
            etStory.setText(cause.getDescription());


        // Photo
        if (cause.getImage() !=null){
            if (cause.getImage().length() > 0){
                btnPhoto.setVisibility(View.GONE);
                ivCausePhoto.setVisibility(View.VISIBLE);
                Picasso.get()
                      .load(cause.getImage())
                      .placeholder(R.drawable.cause)
                      .into(ivCausePhoto);
            }
        }


    }

    private void createCauseInfo() {
        toolbarTitle.setText("Create Cause");

        // City (Cause)

        databaseReference.child(Constants.CITY).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot citySnapshot : dataSnapshot.getChildren()) {
                    String cityName = citySnapshot.child("name").getValue(String.class);
                    String cityKey= citySnapshot.getKey();
                    cities.add(cityName);
                    citiesMap.put(cityName, cityKey);
                }

                Spinner areaSpinner = findViewById(R.id.spinner);
                SpinnerMontserratAdapter areasAdapter = new SpinnerMontserratAdapter(EditCauseActivity.this, android.R.layout.simple_spinner_item, cities);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Hospital (Cause)

        databaseReference.child(Constants.HOSPITAL).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot hospitalSnapshot : dataSnapshot.getChildren()) {
                    String hospitalName = hospitalSnapshot.child("name").getValue(String.class);
                    String hospitalKey = hospitalSnapshot.getKey();
                    hospitals.add(hospitalName);
                    hospitalsMap.put(hospitalName,hospitalKey);
                }

                Spinner hospitalSpinner = findViewById(R.id.spinner2);
                SpinnerMontserratAdapter hospitalAdapter = new SpinnerMontserratAdapter(EditCauseActivity.this, android.R.layout.simple_spinner_item, hospitals);
                hospitalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                hospitalSpinner.setAdapter(hospitalAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Blood Group (Cause)

        List<String> bloodTypes = new ArrayList<>();
        bloodTypes.add("A+");
        bloodTypes.add("A-");
        bloodTypes.add("B-");
        bloodTypes.add("B+");
        bloodTypes.add("O+");
        bloodTypes.add("O-");
        bloodTypes.add("AB-");
        bloodTypes.add("AB+");


        // Setting the position
        BloodAdapter adapter = new BloodAdapter(bloodTypes, this, this);
        recyclerBloodTypes.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NUMBER_COLS);
        recyclerBloodTypes.setLayoutManager(gridLayoutManager);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                saveCause();
                break;
            case R.id.btn_photo:
                selectPicture();
                break;
            case R.id.iv_cause_photo:
                selectPicture();
                break;
        }
    }

    private void selectPicture() {
        EasyImage.openGallery(this, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                e.printStackTrace();
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                btnPhoto.setVisibility(View.GONE);
                ivCausePhoto.setVisibility(View.VISIBLE);

                filePath = Uri.fromFile(imageFile);

                Picasso.get()
                        .load(imageFile)
                        .placeholder(R.drawable.cause)
                        .into(ivCausePhoto);

            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {

            }
        });
    }


    @Override
    public void onBloodTypeClickListener(String bloodType) {
        currentBloodtypeSelected = bloodType;


    }

    private void saveCause() {
        final EditText[] misCampos = {etStory, etDeadline, etDonations, etMobile, etName, etTitle};

        // Validate 6 EditText
        if (validateFields(misCampos)) {
            Toast.makeText(this, "Faltan campos por llenar", Toast.LENGTH_SHORT).show();
        } else {

            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            Cause createadCause = new Cause();

            // Personal Data
            String userID = "" + firebaseUser.getUid();
            createadCause.setUser_id(userID);

            // Name
            String nameUser = etName.getText().toString();
            firebaseDatabase.getReference(Constants.USERS).child(userID).child("name").setValue(nameUser);


            // Mobile Number
            String mobileNumber = etMobile.getText().toString();
            firebaseDatabase.getReference(Constants.USERS).child(userID).child("phone_number").setValue(mobileNumber);

            // Number Of Donations
            int numberDonations = Integer.parseInt(etDonations.getText().toString());
            createadCause.setTotal_donations(numberDonations);

            // Deadline
            createadCause.setDeadline(etDeadline.getText().toString());

            // City
            String citySelected = sCity.getSelectedItem().toString();
            if(citiesMap.containsKey(citySelected)){
                createadCause.setCity_id(citiesMap.get(citySelected));
            }

            // Hospital
            String hospitalSelected = sHospital.getSelectedItem().toString();
            if(hospitalsMap.containsKey(hospitalSelected)){
                createadCause.setHospital_id(hospitalsMap.get(hospitalSelected));
            }

            // Select Blood Group
            String currentBloodType = currentBloodtypeSelected;
            createadCause.setBlood_type(currentBloodType);


            // Cause title
            createadCause.setTitle(etTitle.getText().toString());

            // Cause Description
            createadCause.setDescription(etStory.getText().toString());

            // TODO Cause Picture




            Calendar b = Calendar.getInstance();
            int y = b.get(Calendar.YEAR);
            int m = b.get(Calendar.MONTH);
            int d = b.get(Calendar.DAY_OF_MONTH);
            createadCause.setStartdate(d + "-" + m + "-" + y);



            // CREATE
            Bundle extras = getIntent().getExtras();
            if (extras.containsKey(MODE_CREATE)) {

                DatabaseReference newData = databaseReference.child(Constants.CAUSES).child(firebaseUser.getUid() + "_" + d + "@" + m + "@" + y);
                newData.setValue(createadCause);

                Toast.makeText(this, "Saving by Creating", Toast.LENGTH_SHORT).show();
                finish();

            }

            // EDIT
            if (extras.containsKey(MODE_EDIT)) {
                if (extras.containsKey(CAUSE)) {
                    Cause cause = getIntent().getParcelableExtra(CAUSE);
                    String causeKey = cause.getKey();
                    DatabaseReference newData = databaseReference.child(Constants.CAUSES).child(causeKey);
                    newData.setValue(createadCause);
                    Toast.makeText(this, "Saving by Editing", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

        }


    }

    public boolean validateFields(EditText[] campos) {

        for (int i = 0; i < campos.length; i++) {
            String cadena = campos[i].getText().toString();
            if (cadena.trim().isEmpty()) {
                return true;
            }

        }
        return false;
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
