package org.gptccherthala.virtualqueue.user;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.gptccherthala.virtualqueue.R;

import java.util.HashMap;
import java.util.Map;

public class UserRegistrationActivity extends AppCompatActivity {

    EditText etName;
    EditText etPhone;
    EditText etPinCode;
    Button btRegister;
    String userId;
    private DatabaseReference mDataBase;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        etName = findViewById(R.id.text_name);
        etPhone = findViewById(R.id.text_phone);
        etPinCode = findViewById(R.id.text_pincode);
        btRegister = findViewById(R.id.button_register);
        //mDataBase = FirebaseDatabase.getInstance().getReference();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String TAG = "Testing";

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString();
                String phoneString = etPhone.getText().toString();
                long phone = Long.parseLong(phoneString);
                int pincode = Integer.parseInt(etPinCode.getText().toString());

                if (checkFieldData(name, phoneString, pincode)) {
                    try {
                        Map<String, Object> user = new HashMap<>();
                        //user.put("Uid", userId);
                        user.put("Name", name);
                        user.put("Phone", phone);
                        user.put("PinCode", pincode);

                        db.collection("users")
                                .document(userId)
                                .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(UserRegistrationActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    Intent UserHomeActivity = new Intent(getApplicationContext(), org.gptccherthala.virtualqueue.user.UserHomeActivity.class);
                                    startActivity(UserHomeActivity);
                                    UserRegistrationActivity.this.finish();
                                } else {
                                    Toast.makeText(UserRegistrationActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (DatabaseException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public boolean checkFieldData(String name, String phone, int pincode) {
        if (name.trim().length() == 0) {
            etName.setError("Name required");
            return false;
        }

        if (phone.trim().length() == 0) {
            etPhone.setError("Phone Number required");
            return false;
        } else if (phone.trim().length() != 10) {
            etPhone.setError("Invalid Phone Number");
            return false;
        }

        if (pincode == 0) {
            etPinCode.setError("Pincode required");
            return false;
        }
        return true;
    }
}