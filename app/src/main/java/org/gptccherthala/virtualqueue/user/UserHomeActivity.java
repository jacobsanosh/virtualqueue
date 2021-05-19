package org.gptccherthala.virtualqueue.user;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.gptccherthala.virtualqueue.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserHomeActivity extends AppCompatActivity {
    DatabaseReference userRef,newuserRef;
    BottomNavigationView BottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        String Uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        userRef= FirebaseDatabase.getInstance().getReference().child("user").child(Uid);
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "000")
                .setSmallIcon(R.drawable.alert)
                .setContentTitle("One Queue")
                .setContentText("Be ready you are next..")
                .setPriority(NotificationCompat.PRIORITY_MAX);

        userRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    newuserRef=userRef.child(ds.getKey());
                    ValueEventListener postListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try{
                                if(dataSnapshot.child("alert").getValue().toString().equals("true")) {
                                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                                    notificationManager.notify(000, builder.build());
                                }
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            System.out.println("on cancelling");

                        }

                    };newuserRef.addValueEventListener(postListener);

                }
            }
        });



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
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Test";
            String description = "Test Channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("000", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}