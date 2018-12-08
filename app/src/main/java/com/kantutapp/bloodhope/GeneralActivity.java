package com.kantutapp.bloodhope;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Transaction;
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

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            ProfileFragment profileFragment = new ProfileFragment();

            profileFragment.setArguments(getIntent().getExtras());

            bottomNavigation.getMenu().getItem(TAB_PROFILE).setChecked(true);
            bottomNavigation.getMenu().getItem(TAB_PROFILE).setIcon(R.drawable.ic_profile);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, profileFragment).commit();

        }
        bottomNavigation.setOnNavigationItemSelectedListener(this);



    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.category_prices:
                bottomNavigation.getMenu().getItem(TAB_PRICES).setChecked(true);
                setFragment(new PricesFragment());

//                PricesFragment pricesFragment = new PricesFragment();
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, pricesFragment)
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                        .addToBackStack(null).commit();
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
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(null);
        transaction.commit();
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
