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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    Button continueButton;
    RadioGroup typeOfUserRadioGroup;
    RadioButton selectedRadioButton;
    EditText etEmailId;
    EditText etPassword;
    EditText etCPassword;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mydatabase;// for userr real time database


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseAuth = FirebaseAuth.getInstance();
        etEmailId = findViewById(R.id.useremail);
        etPassword = findViewById(R.id.password);
        etCPassword = findViewById(R.id.password2);

        continueButton = findViewById(R.id.continueButton);
        typeOfUserRadioGroup = findViewById(R.id.typeOfUserRadioGroup);

        mydatabase = FirebaseDatabase.getInstance().getReference();// for users db

        continueButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String EmailId = etEmailId.getText().toString().trim();
                String Password = etPassword.getText().toString().trim();
                String CPassword = etCPassword.getText().toString().trim();
                int selectedId = typeOfUserRadioGroup.getCheckedRadioButtonId();
                boolean isComplete = checkFieldData(EmailId, Password, CPassword, selectedId);


                if (isComplete) {
                    firebaseAuth.createUserWithEmailAndPassword(EmailId, Password)
                            .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful())
                                    {
                                        Toast.makeText(RegistrationActivity.this, "something went wrong....", Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        Users user=new Users("sanosh",813882001,688542);
                                        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful())
                                                {

                                                }
                                            }
                                        });


                                        selectedRadioButton = findViewById(selectedId);
                                        String selectedButtonString = selectedRadioButton.getText().toString();
                                        if (selectedButtonString.equals("User"))
                                        {
                                            Intent UserRegistrationActivity = new Intent(getApplicationContext(), UserRegistrationActivity.class);
                                            startActivity(UserRegistrationActivity);
                                        }
                                        else
                                        {
                                            Intent BusinessRegistrationActivity = new Intent(getApplicationContext(), BusinessRegistrationActivity.class);
                                            startActivity(BusinessRegistrationActivity);
                                        }
                                    }
                                }
                            });


                        }
            }


        });
    }

    public boolean checkFieldData(String emailId, String password, String password2, int selectedId) {
        if (emailId.trim().length() == 0) {
            etEmailId.setError("Email-ID required");
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
            etCPassword.setError("Password not matching");
            return false;
        }

        if (selectedId == -1) {
            Toast.makeText(RegistrationActivity.this, "Please select type", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}