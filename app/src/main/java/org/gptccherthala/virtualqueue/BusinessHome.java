package org.gptccherthala.virtualqueue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class BusinessHome extends AppCompatActivity {
    BottomNavigationView BottomNav;


    //BottomNav code
    private final BottomNavigationView.OnNavigationItemSelectedListener nav = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
           switch (item.getItemId()) {
                case R.id.Details:
                    Toast.makeText(BusinessHome.this, "Details...", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.QrReader:
                    Toast.makeText(BusinessHome.this, "qrreaders is clicked.....", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.Profile:
                    Toast.makeText(BusinessHome.this, "profile is clicked.....", Toast.LENGTH_LONG).show();
                    return true;

            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_home);
        BottomNav = findViewById(R.id.BottomNav);
        BottomNav.setOnNavigationItemSelectedListener(nav);

    }

}