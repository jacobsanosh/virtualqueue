package org.gptccherthala.virtualqueue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class UserHomeActivity extends AppCompatActivity {
    //creating objects for each btn
    ImageButton BtnHotel,BtnOffice,BtnShop,BtnBank;
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
               Toast.makeText(UserHomeActivity.this, "hotel is clicked.....", Toast.LENGTH_LONG).show();
           }
       });

       // checking whether BtnOffice is clicked

        BtnOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserHomeActivity.this, "office is clicked.....", Toast.LENGTH_LONG).show();
            }
        });



        // checking whether BtnShop is clicked

        BtnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserHomeActivity.this, "shop is clicked.....", Toast.LENGTH_LONG).show();
            }
        });

        // checking whether BtnBank is clicked

        BtnBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserHomeActivity.this, "Bank is clicked.....", Toast.LENGTH_LONG).show();
            }
        });
        //BottomNavigator code





    }
    private BottomNavigationView.OnNavigationItemSelectedListener nav = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.Home:
                    Toast.makeText(UserHomeActivity.this, "home....", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.Profile:
                    Toast.makeText(UserHomeActivity.this, "profile is clicked.....", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.Qr:
                    Toast.makeText(UserHomeActivity.this, "qr is clicked.....", Toast.LENGTH_LONG).show();
                    return true;

            }
            return false;
        }


    };
}