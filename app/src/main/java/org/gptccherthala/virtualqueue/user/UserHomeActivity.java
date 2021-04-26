package org.gptccherthala.virtualqueue.user;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.gptccherthala.virtualqueue.R;

public class UserHomeActivity extends AppCompatActivity {

    BottomNavigationView BottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        BottomNav = findViewById(R.id.BottomNav);

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
                    case R.id.Qr:
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        loadingqr qr =new loadingqr(UserHomeActivity.this);
                        qr.displayingqr(userId);
                        return true;
                }
                return false;
            }
        });
    }
}