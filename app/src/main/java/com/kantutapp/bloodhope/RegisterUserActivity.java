package com.kantutapp.bloodhope;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = RegisterUserActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);


        // Views
        Button mBtnLogout = findViewById(R.id.btn_logout);
        TextView mUserTextView = findViewById(R.id.tv_name);

        // Firebase User
        FirebaseUser acct = FirebaseAuth.getInstance().getCurrentUser();
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();
            mUserTextView.setText(personName);
        }

        // Button Listeners
        mBtnLogout.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logout:
                logout();
                break;
        }
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(RegisterUserActivity.this, LoginActivity.class));
        finish();

        // LoginManager.getInstance().logOut();

    }
}
