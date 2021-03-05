package org.gptccherthala.virtualqueue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    Button registerButton;
    EditText etEmail;
    EditText etPassword;
    Button loginBtn;
    private FirebaseAuth mAuth;
    String Uid;
    // for database
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            mDatabase = FirebaseDatabase.getInstance().getReference("/users/");
            Uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean flag = false;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        System.out.println("Uid/; " + dataSnapshot.getKey());
                        System.out.println("Uid curr; " + Uid);
                        if(dataSnapshot.getKey().equals(Uid)){
                            flag = true;
                        }
                    }
                    if(flag){
                        Intent UserHomeActivity = new Intent(getApplicationContext(), UserHomeActivity.class);
                        startActivity(UserHomeActivity);
                        LoginActivity.this.finish();
                    } else {
                        Intent BusinessHomeActivity = new Intent(getApplicationContext(), BusinessHome.class);
                        startActivity(BusinessHomeActivity);
                        LoginActivity.this.finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }



        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();


                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mDatabase = FirebaseDatabase.getInstance().getReference("/users/");
                            Uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                            // for getting the current user id
                            mDatabase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        boolean flag = false;
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            System.out.println("Uid/; " + dataSnapshot.getKey());
                                            System.out.println("Uid curr; " + Uid);
                                            if(dataSnapshot.getKey().equals(Uid)){
                                                flag = true;
                                            }
                                        }
                                        if(flag){
                                            Intent UserHomeActivity = new Intent(getApplicationContext(), UserHomeActivity.class);
                                            startActivity(UserHomeActivity);
                                            LoginActivity.this.finish();
                                        } else {
                                            Intent BusinessHomeActivity = new Intent(getApplicationContext(), BusinessHome.class);
                                            startActivity(BusinessHomeActivity);
                                            LoginActivity.this.finish();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        } else {
                            Toast.makeText(LoginActivity.this, "Incorrect credentials", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrationActivityIntent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(registrationActivityIntent);
                finish();
            }
        });
    }
}