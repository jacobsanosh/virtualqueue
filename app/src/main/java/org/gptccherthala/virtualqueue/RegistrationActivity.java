package org.gptccherthala.virtualqueue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    Button continueButton;
    RadioGroup typeOfUserRadioGroup;
    RadioButton selectedRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        continueButton = findViewById(R.id.continueButton);
        typeOfUserRadioGroup = findViewById(R.id.typeOfUserRadioGroup);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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