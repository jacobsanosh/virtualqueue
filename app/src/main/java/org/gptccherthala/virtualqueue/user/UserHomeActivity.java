package org.gptccherthala.virtualqueue.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.gptccherthala.virtualqueue.R;
import org.gptccherthala.virtualqueue.business.BusinessDataListSubActivity;

public class UserHomeActivity extends AppCompatActivity {
    //BottomNavigator code
    private final BottomNavigationView.OnNavigationItemSelectedListener nav = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.Home:
                    Toast.makeText(UserHomeActivity.this, "home....", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.Profile:
                    FirebaseAuth.getInstance().signOut();
                    Intent LoginActivity = new Intent(getApplicationContext(), org.gptccherthala.virtualqueue.LoginActivity.class);
                    startActivity(LoginActivity);
                    finish();
                    return true;
                case R.id.Qr:
                    Toast.makeText(UserHomeActivity.this, "qr is clicked.....", Toast.LENGTH_LONG).show();
                    return true;

            }
            return false;
        }
    };
    ImageButton BtnHotel, BtnOffice, BtnShop, BtnBank;
    BottomNavigationView BottomNav;
    String test = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        //for retriving button object id

        BtnHotel = findViewById(R.id.BtnHotel);
        BtnOffice = findViewById(R.id.BtnOffice);
        BtnShop = findViewById(R.id.BtnShop);
        BtnBank = findViewById(R.id.BtnBank);
        BottomNav = findViewById(R.id.BottomNav);


        BottomNav.setOnNavigationItemSelectedListener(nav);


        // checking whether BtnHotel is clicked

        BtnHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent businessDataListSub = new Intent(getApplicationContext(), BusinessDataListSubActivity.class);
                businessDataListSub.putExtra("category", "Hotel");
                startActivity(businessDataListSub);
            }
        });

        // checking whether BtnOffice is clicked
        BtnOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent businessDataListSub = new Intent(getApplicationContext(), BusinessDataListSubActivity.class);
                businessDataListSub.putExtra("category", "Office");
                startActivity(businessDataListSub);
            }
        });

        // checking whether BtnShop is clicked
        BtnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent businessDataListSub = new Intent(getApplicationContext(), BusinessDataListSubActivity.class);
                businessDataListSub.putExtra("category", "Shop");
                startActivity(businessDataListSub);
            }
        });

        // checking whether BtnBank is clicked
        BtnBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent businessDataListSub = new Intent(getApplicationContext(), BusinessDataListSubActivity.class);
                businessDataListSub.putExtra("category", "Bank");
                startActivity(businessDataListSub);
            }
        });
    }
}