package com.kantutapp.bloodhope;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kantutapp.bloodhope.fragments.DonateFragment;
import com.kantutapp.bloodhope.fragments.PricesFragment;
import com.kantutapp.bloodhope.fragments.ProfileFragment;
import com.kantutapp.bloodhope.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GeneralActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    public static final int TAB_PRICES = 0;
    public static final int TAB_DONATE = 1;
    public static final int TAB_PROFILE = 2;


    public static final String USER = "User";

    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent.getParcelableExtra(USER) != null){
            User user = intent.getParcelableExtra(USER);
            Toast.makeText(this, user.getTypeOfBlood(), Toast.LENGTH_SHORT).show();
        }
        // Firebase User
        FirebaseUser acct = FirebaseAuth.getInstance().getCurrentUser();
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();
        }

        setFragment(new ProfileFragment());
        bottomNavigation.getMenu().getItem(TAB_PROFILE).setChecked(true);
        bottomNavigation.getMenu().getItem(TAB_DONATE).setIcon(R.drawable.ic_blood);

        bottomNavigation.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.category_prices:
                bottomNavigation.getMenu().getItem(TAB_PRICES).setChecked(true);
                setFragment(new PricesFragment());
                break;

            case R.id.category_donate:
                bottomNavigation.getMenu().getItem(TAB_DONATE).setChecked(true);
                setFragment(new DonateFragment());
                break;

            case R.id.category_profile:
                bottomNavigation.getMenu().getItem(TAB_PROFILE).setChecked(true);
                setFragment(new ProfileFragment());
                break;
        }
        return false;
    }

    public void setFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.general_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.general_logout:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void logout() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        startActivity(new Intent(GeneralActivity.this, LoginActivity.class));
        finish();

    }
}
