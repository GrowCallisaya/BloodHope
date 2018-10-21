package com.kantutapp.bloodhope;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kantutapp.bloodhope.fragments.DonateFragment;
import com.kantutapp.bloodhope.fragments.PricesFragment;
import com.kantutapp.bloodhope.fragments.ProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RegisterUserActivity.class.getSimpleName();

    @BindView(R.id.btn_done)
    FancyButton btnDone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        ButterKnife.bind(this);


        btnDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_done:
                goToGeneralActivity();
                break;
        }
    }

    private void goToGeneralActivity() {
        startActivity(new Intent(RegisterUserActivity.this, GeneralActivity.class));
        finish();
    }


}
