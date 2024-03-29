package org.gptccherthala.virtualqueue;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.gptccherthala.virtualqueue.business.BusinessHomeActivity;
import org.gptccherthala.virtualqueue.user.UserHomeActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    Button registerButton;
    EditText etEmail;
    EditText etPassword;
    Button loginBtn;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    SharedPreferences preferences;
    SharedPreferences.Editor udata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        udata =preferences.edit();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            checkUser();
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
                            checkUser();
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
            }
        });
    }

    public void checkUser() {
        String TAG = "Testing";

        // used dto get uid
        String Uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        // to check the user from database
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        boolean isUser = false;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                if (document.getId().equals(Uid)) {
                                    isUser = true;
                                    break;
                                }
                                Log.d(TAG, document.getId());
                            }

                            if (isUser)
                            {
                                DocumentReference documentReference=db.collection("users").document(Uid);
                                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful())
                                        {
                                            DocumentSnapshot data =task.getResult();
                                            if(data.exists())
                                            {
                                                Integer picode = Integer.parseInt(data.get("PinCode").toString());
                                                Long phone = Long.parseLong(data.get("Phone").toString());
                                                String name = data.get("Name").toString();
                                                udata.putString("Uname",name);
                                                udata.putLong("Phone",phone);
                                                udata.putInt("Pincode",picode);
                                                udata.apply();
                                                System.out.println(udata.toString());
                                            }
                                        }
                                    }
                                });
                                Intent UserHomeActivity = new Intent(getApplicationContext(), UserHomeActivity.class);
                                startActivity(UserHomeActivity);
                                LoginActivity.this.finish();
                            } else
                                {
                                Intent BusinessHomeActivity = new Intent(getApplicationContext(), BusinessHomeActivity.class);
                                startActivity(BusinessHomeActivity);
                                LoginActivity.this.finish();
                                }
                        }
                        else
                            {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}