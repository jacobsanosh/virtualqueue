package org.gptccherthala.virtualqueue.business;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.gptccherthala.virtualqueue.R;
import org.gptccherthala.virtualqueue.user.UserRegistrationActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BusinessRegistrationActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 71;
    EditText etCompanyName;
    EditText etAddress;
    EditText etPinCode;
    EditText etPhone;
    EditText etDescription;
    Spinner spCategory;
    Spinner spType;
    Button btnChoose;
    Button btnRegister;
    String userId;
    private Uri filePath;
    private DatabaseReference mDataBase;
    private StorageReference mStorageReference;
    private String imageUrl;

    // for firestore

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_registration);

        etCompanyName = findViewById(R.id.text_company_name);
        etAddress = findViewById(R.id.text_address);
        etPhone = findViewById(R.id.text_phone);
        etPinCode = findViewById(R.id.text_pincode);
        etDescription = findViewById(R.id.text_description);
        spCategory = findViewById(R.id.spinner);
        spType = findViewById(R.id.spinner_type);
        btnChoose = findViewById(R.id.image_choose);
        btnRegister = findViewById(R.id.button_register);
        mDataBase = FirebaseDatabase.getInstance().getReference();
        userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        mStorageReference = FirebaseStorage.getInstance().getReference();

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = spCategory.getSelectedItem().toString();

                switch (type) {
                    case "Hotel": {
                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(BusinessRegistrationActivity.this,
                                R.array.type_hotel, android.R.layout.simple_spinner_item);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spType.setAdapter(adapter);
                        break;
                    }
                    case "Office": {
                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(BusinessRegistrationActivity.this,
                                R.array.type_office, android.R.layout.simple_spinner_item);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spType.setAdapter(adapter);
                        break;
                    }
                    case "Shop": {
                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(BusinessRegistrationActivity.this,
                                R.array.type_shop, android.R.layout.simple_spinner_item);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spType.setAdapter(adapter);
                        break;
                    }
                    case "Bank": {
                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(BusinessRegistrationActivity.this,
                                R.array.type_bank, android.R.layout.simple_spinner_item);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spType.setAdapter(adapter);
                        break;
                    }
                    case "Hospital": {
                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(BusinessRegistrationActivity.this,
                                R.array.type_hospital, android.R.layout.simple_spinner_item);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spType.setAdapter(adapter);
                        break;
                    }
                    case "Other": {
                        //TODO Implement methods for type other
                        break;
                    }
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String name = etCompanyName.getText().toString();
                    String address = etAddress.getText().toString();
                    String phoneString = etPhone.getText().toString();
                    long phone = 0;
                    if (!phoneString.equals("")) {
                        phone = Long.parseLong(phoneString);
                    }

                    int pincode = 0;
                    String pincodeString = etPinCode.getText().toString();
                    if (!pincodeString.equals("")) {
                        pincode = Integer.parseInt(pincodeString);
                    }

                    String description = etDescription.getText().toString();
                    String category = spCategory.getSelectedItem().toString();
                    String type = spType.getSelectedItem().toString();

                    uploadImage();

                    if (checkFieldData(name, address, pincode, phoneString, description, imageUrl)) {
                        try {
                            BusinessDatabase data = new BusinessDatabase(name, address, phone, pincode, description, category, imageUrl);

                           // business an variable with key value pair used to store in fire store using key value pair

                            Map<String , Object> Business = new HashMap<>();

                            // name is an key value pair and store the value in db

                            Business.put("Name", name);
                            Business.put("Address", address);
                            Business.put("Phone", phone);
                            Business.put("Pincode", pincode);
                            Business.put("Description", description);
                            Business.put("Category", category);
                            Business.put("ImageUrl", imageUrl);

                            // it this collection path Business will be the document name userId

                            db.collection("Business")
                                    .document(userId).set(Business).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) // if the task is successful
                                    {
                                        Toast.makeText(getApplicationContext(), "Business registration Success", Toast.LENGTH_SHORT).show();
                                        Intent business = new Intent(getApplicationContext(),BusinessHomeActivity.class);
                                        startActivity(business);
                                        BusinessRegistrationActivity.this.finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(), "Failes to register", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });



                           /* mDataBase.child("business").child(category).child(type).child(userId).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(BusinessRegistrationActivity.this, "Success", Toast.LENGTH_SHORT);
                                    } else {
                                        Toast.makeText(BusinessRegistrationActivity.this, "Failed", Toast.LENGTH_SHORT);
                                    }
                                }
                            });*/
                        }
                        catch (DatabaseException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public boolean checkFieldData(String companyName, String address, int pincode
            , String phone, String description, String imageUrl) {
        if (companyName.trim().length() == 0) {
            etCompanyName.setError("Name required");
            return false;
        }

        if (address.trim().length() == 0) {
            etAddress.setError("Address required");
            return false;
        }

        if (pincode == 0) {
            etPinCode.setError("Pincode required");
            return false;
        }

        if (phone.trim().length() == 0) {
            etPhone.setError("Phone Number required");
            return false;
        } else if (phone.trim().length() != 10) {
            etPhone.setError("Invalid Phone Number");
            return false;
        }

        if (description.trim().length() == 0) {
            etDescription.setError("Description required");
            return false;
        }

        if (imageUrl == null || imageUrl.isEmpty()) {
            Toast.makeText(BusinessRegistrationActivity.this,"An ERROR OCCURED ",Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
        }
    }

    private void uploadImage() {
        if (filePath != null) {
            StorageReference ref = mStorageReference.child("images/" + userId);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            btnChoose.setText("Image Uploaded");
                            ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    imageUrl = task.getResult().toString();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            btnChoose.setError("Image upload failed");
                            Toast.makeText(BusinessRegistrationActivity.this, "Failed ", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}