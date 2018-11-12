package com.kantutapp.bloodhope;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;

public class EditCauseActivity extends AppCompatActivity implements View.OnClickListener, BloodAdapter.OnBloodClickHandler {
    private static final String TAG = EditCauseActivity.class.getSimpleName();
    private static final int NUMBER_COLS = 6;
    private static final String USER = "User";
    EditText etBirthday;
    Calendar calendario = Calendar.getInstance();



    //initializing firebase authentication object
    private User currentUser = new User();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference ref = firebaseDatabase.getReference(); //city
    DatabaseReference ref2 = firebaseDatabase.getReference();//hospital
    FirebaseUser firebaseUser;
    FirebaseUser acct = FirebaseAuth.getInstance().getCurrentUser();



    @BindView(R.id.recycler_blood_types)
    RecyclerView recyclerBloodTypes;
    @BindView(R.id.et_name) EditText etName;
    @BindView(R.id.et_mobile) EditText etMobile;
    @BindView(R.id.et_donations) EditText etDonations;
    @BindView(R.id.et_deadline) EditText etDeadline;
    @BindView(R.id.et_title) EditText etTitle;
    @BindView(R.id.et_story) EditText etStory;
    @BindView(R.id.spinner) Spinner sCity;
    @BindView(R.id.spinner2) Spinner sHospital;

    @BindView(R.id.btn_submit)    FancyButton btnSubmit;
    @BindView(R.id.btn_photo) FancyButton btnPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cause);
        ButterKnife.bind(this);
        List<String> bloodTypes = new ArrayList<>();
        bloodTypes.add("A+");
        bloodTypes.add("A-");
        bloodTypes.add("B-");
        bloodTypes.add("B+");
        bloodTypes.add("O+");
        bloodTypes.add("O-");
        bloodTypes.add("AB-");
        bloodTypes.add("AB+");

        BloodAdapter adapter = new BloodAdapter(bloodTypes, this, this);
        recyclerBloodTypes.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NUMBER_COLS);
        recyclerBloodTypes.setLayoutManager(gridLayoutManager);


        ref.child(Constants.CITY).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> areas = new ArrayList<String>();

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("name").getValue(String.class);
                    areas.add(areaName);
                }

                Spinner areaSpinner = findViewById(R.id.spinner);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(EditCauseActivity.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ref2.child(Constants.HOSPITAL).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> areas = new ArrayList<String>();

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String areaName = areaSnapshot.child("name").getValue(String.class);
                    areas.add(areaName);
                }

                Spinner areaSpinner = findViewById(R.id.spinner2);
                ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(EditCauseActivity.this, android.R.layout.simple_spinner_item, areas);
                areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnSubmit.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_submit:
                saveCause();
                break;
            case R.id.btn_photo:
                break;
        }
    }
    @Override
    public void onBloodTypeClickListener(String bloodType) {
        currentUser.setBlood_type(bloodType);


    }
    private void saveCause() {
        final EditText[] misCampos = {etStory,etDeadline,etDonations,etMobile,etName,etTitle};
        // Validate 6 EditText
        if (validarCampoVacio(misCampos)) {
            Toast.makeText( this, "Faltan campos por llenar", Toast.LENGTH_SHORT).show();
        }
        else{
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            Log.w(TAG, "create " +   " . " +
                    firebaseUser.getUid()+   " . " +
                    etName.getText().toString()+   " . " +
                    etMobile.getText().toString()+   " . " +
                    etDonations.getText().toString()+   " . " +
                    etDeadline.getText().toString()+   " . " +
                    sCity.getSelectedItem().toString()+   " . " +
                    sHospital.getSelectedItem().toString()+   " . " +
                    currentUser.getBlood_type()+   " . " +
                    etStory.getText().toString());
            Cause currentCause = new Cause();
            currentCause.setTitle(etTitle.getText().toString());
            currentCause.setDeadline(etDeadline.getText().toString());
            currentCause.setDescription(etStory.getText().toString());

            int a1= Integer.parseInt(etDonations.getText().toString());
            currentCause.setTotal_donations(a1);
            currentCause.setBlood_type( currentUser.getBlood_type());
            currentCause.setCity( sCity.getSelectedItem().toString());
            currentCause.setHospital(sHospital.getSelectedItem().toString());
            String c=""+acct.getUid();
            currentCause.setUser_id(c);
            Calendar b= Calendar.getInstance();
            int y = b.get(Calendar.YEAR);
            int m = b.get(Calendar.MONTH);
            int d = b.get(Calendar.DAY_OF_MONTH);
            currentCause.setStartdate(d+"/"+m+"/"+y);

            //Realtime Firebase

            DatabaseReference newData = ref.child("causes").child(firebaseUser.getUid()+"_"+d+"@"+m+"@"+y);
            newData.setValue(currentCause);
            Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show();
    }

    }
    public boolean validarCampoVacio(EditText[] campos){

        for(int i=0; i<campos.length; i++){
            String cadena = campos[i].getText().toString();
            if(cadena.trim().isEmpty()){
                return true;
            }

        }
        return false;
    }

}
