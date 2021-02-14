package org.gptccherthala.virtualqueue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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