package org.gptccherthala.virtualqueue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class LoginActivity extends AppCompatActivity {

    Button registerButton;
    EditText etUsername;
    EditText etPassword;
    Button loginBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseUser currentUser = mAuth.getCurrentUser();

        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginButton);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrationActivityIntent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(registrationActivityIntent);
            }
        });
    }

    public void verifyUser(View view) {
        Toast.makeText(this, "Verifying.....", Toast.LENGTH_SHORT).show();
    }
}