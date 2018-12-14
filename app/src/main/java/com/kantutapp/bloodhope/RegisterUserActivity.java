package com.kantutapp.bloodhope;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kantutapp.bloodhope.adapter.BloodAdapter;
import com.kantutapp.bloodhope.models.User;
import com.kantutapp.bloodhope.utils.EditTextMontserratRegular;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener, BloodAdapter.OnBloodClickHandler {

    private static final String TAG = RegisterUserActivity.class.getSimpleName();
    private static final int NUMBER_COLS = 6;
    private static final String USER = "User";

    @BindView(R.id.btn_done)
    FancyButton btnDone;
    @BindView(R.id.recycler_blood_types)
    RecyclerView recyclerBloodTypes;
    @BindView(R.id.et_user_number)
    EditTextMontserratRegular etUserNumber;
    @BindView(R.id.et_user_email)
    EditTextMontserratRegular etUserEmail;

    private User currentUser = new User();


    //initializing firebase authentication object
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference ref = firebaseDatabase.getReference();

    FirebaseUser firebaseUser;

    boolean hasSelectATypeOfBlood = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
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

        btnDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_done:
                goToGeneralActivity();
                break;
        }
    }

    private void goToGeneralActivity() {

        if (validateFields() && validateBloodType()){
            Toast.makeText(RegisterUserActivity.this, "Some data are missing, please complete", Toast.LENGTH_SHORT).show();
        }else {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            currentUser.setKey(firebaseUser.getUid());
            currentUser.setName(firebaseUser.getDisplayName());
            currentUser.setPhoto(firebaseUser.getPhotoUrl().toString());
            currentUser.setPhone_number(etUserNumber.getText().toString());
            currentUser.setEmail(etUserEmail.getText().toString());
            currentUser.setBlood_type(etUserEmail.getText().toString());
            currentUser.setNumber_donations(0);


            createUser(currentUser);
            Intent intent = new Intent(RegisterUserActivity.this, GeneralActivity.class);
            intent.putExtra(USER, currentUser);
            startActivity(intent);
            finish();
        }




    }

    private boolean validateBloodType() {
        if (!hasSelectATypeOfBlood)
            return true;
        return false;
    }

    private boolean validateFields() {
        final EditText[] fields = {etUserEmail, etUserNumber};
        for (int i = 0; i < fields.length; i++) {
            String field = fields[i].getText().toString();
            if (field.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onBloodTypeClickListener(String bloodType) {
        hasSelectATypeOfBlood = true;
        currentUser.setBlood_type(bloodType);

    }

    public void createUser(User user) {
        //Realtime Firebase
        DatabaseReference newData = ref.child("users").child(firebaseUser.getUid());
        newData.setValue(user);
        Log.w(TAG, "create " + firebaseUser.getUid() + "." + user.getBlood_type());

    }
}
