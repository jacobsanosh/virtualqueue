package org.gptccherthala.virtualqueue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    EditText etEmailId;
    EditText etPassword;
    EditText etCPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseAuth = FirebaseAuth.getInstance();
        etEmailId = findViewById(R.id.userEmailId);
        etPassword = findViewById(R.id.password);
        etCPassword = findViewById(R.id.password2);

        continueButton = findViewById(R.id.continueButton);
        typeOfUserRadioGroup = findViewById(R.id.typeOfUserRadioGroup);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String EmailId = etEmailId.getText().toString().trim();
                    String Password = etPassword.getText().toString().trim();
                    String CPassword = etCPassword.getText().toString().trim();

                    if(EmailId.length()==0)
                    {
                        etEmailId.setError("Email Id requires");
                    }

                    if(CPassword == Password)
                    {
                        etCPassword.setError("password must be matched");
                    }

                }
                catch(Exception e)
                {
                    Toast.makeText(RegistrationActivity.this, "something is missing...", Toast.LENGTH_LONG).show();
                }

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