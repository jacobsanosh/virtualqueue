package org.gptccherthala.virtualqueue;

import android.content.Intent;
import android.os.Bundle;
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

    EditText etUsername;
    EditText etPassword;
    EditText etPassword2;
    Button continueButton;
    RadioGroup typeOfUserRadioGroup;
    RadioButton selectedRadioButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();

        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        etPassword2 = findViewById(R.id.password2);
        continueButton = findViewById(R.id.continueButton);
        typeOfUserRadioGroup = findViewById(R.id.typeOfUserRadioGroup);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = typeOfUserRadioGroup.getCheckedRadioButtonId();
                String emailId = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String password2 = etPassword2.getText().toString();

                boolean isComplete = checkFieldData(emailId, password, password2, selectedId);

                if (isComplete) {
                    mAuth.createUserWithEmailAndPassword(emailId, password)
                            .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        //int selectedId = typeOfUserRadioGroup.getCheckedRadioButtonId();
                                        selectedRadioButton = findViewById(selectedId);
                                        String selectedButtonString = selectedRadioButton.getText().toString();

                                        if (selectedButtonString.equals("User")) {
                                            Intent UserRegistrationActivity = new Intent(getApplicationContext(), org.gptccherthala.virtualqueue.user.UserRegistrationActivity.class);
                                            startActivity(UserRegistrationActivity);
                                            RegistrationActivity.this.finish();
                                        } else {
                                            Intent BusinessRegistrationActivity = new Intent(getApplicationContext(), org.gptccherthala.virtualqueue.business.BusinessRegistrationActivity.class);
                                            startActivity(BusinessRegistrationActivity);
                                            RegistrationActivity.this.finish();
                                        }
                                    } else {
                                        Toast.makeText(RegistrationActivity.this, "Something went wrong.....", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    public boolean checkFieldData(String emailId, String password, String password2, int selectedId) {
        if (emailId.trim().length() == 0) {
            etUsername.setError("Email-ID required");
            return false;
        }

        if (password.trim().length() == 0) {
            etPassword.setError("Password required");
            return false;
        } else if (password.trim().length() < 6) {
            etPassword.setError("Minimum 6 characters");
            return false;
        }

        if (!password.equals(password2)) {
            etPassword2.setError("Password not matching");
            return false;
        }

        if (selectedId == -1) {
            Toast.makeText(RegistrationActivity.this, "Please select type", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}