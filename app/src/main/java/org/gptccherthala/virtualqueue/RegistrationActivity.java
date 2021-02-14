package org.gptccherthala.virtualqueue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    Button continueButton;
    RadioGroup typeOfUserRadioGroup;
    RadioButton selectedRadioButton;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseAuth = FirebaseAuth.getInstance();

        continueButton = findViewById(R.id.continueButton);
        typeOfUserRadioGroup = findViewById(R.id.typeOfUserRadioGroup);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.createUserWithEmailAndPassword("deepu@gmail.com","123456")
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegistrationActivity.this, "something went wrong....", Toast.LENGTH_LONG).show();
                                } else {
                                  RegistrationActivity.this.startActivity(new Intent(RegistrationActivity.this, UserRegistrationActivity.class));
                                   RegistrationActivity.this.finish();
                                }
                            }
                        });

                int selectedId = typeOfUserRadioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(RegistrationActivity.this, "Please select type", Toast.LENGTH_LONG).show();
                } else {
                    selectedRadioButton = findViewById(selectedId);
                    String selectedButtonString = selectedRadioButton.getText().toString();
                    if (selectedButtonString.equals("User")) {
                        Intent UserRegistrationActivity = new Intent(getApplicationContext(), UserRegistrationActivity.class);
                        startActivity(UserRegistrationActivity);
                    } else {
                        Intent BusinessRegistrationActivity = new Intent(getApplicationContext(), BusinessRegistrationActivity.class);
                        startActivity(BusinessRegistrationActivity);
                    }

                }
            }
        });
    }
};