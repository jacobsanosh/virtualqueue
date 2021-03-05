package org.gptccherthala.virtualqueue.business;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.gptccherthala.virtualqueue.R;

public class BusinessHomeActivity extends AppCompatActivity {

    BottomNavigationView BottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_home);
        BottomNav = findViewById(R.id.BottomNav);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        BottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                        return true;
                    case R.id.Profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
                        return true;
                    case R.id.Details:
                        Toast.makeText(BusinessHomeActivity.this, "Details.", Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.QrReader:
                        Toast.makeText(BusinessHomeActivity.this, "Scanner", Toast.LENGTH_LONG).show();
                        return true;
                }
                return false;
            }
        });
    }

}