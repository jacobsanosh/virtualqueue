package org.gptccherthala.virtualqueue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class BusinessRegistrationActivity extends AppCompatActivity {

    private final int PICK_IMAGE_REQUEST = 71;
    EditText etCompanyName;
    EditText etAddress;
    EditText etPinCode;
    EditText etPhone;
    EditText etDescription;
    Spinner spCategory;
    Button btnChoose;
    Button btnRegister;
    String userId;
    private Uri filePath;
    private DatabaseReference mDataBase;
    private StorageReference mStorageReference;

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
        btnChoose = findViewById(R.id.image_choose);
        btnRegister = findViewById(R.id.button_register);
        mDataBase = FirebaseDatabase.getInstance().getReference();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mStorageReference = FirebaseStorage.getInstance().getReference();

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
                uploadImage();
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
                    if(!phoneString.equals("")){
                        phone = Long.parseLong(phoneString);
                    }

                    int pincode = 0;
                    String pincodeString = etPinCode.getText().toString();
                    if(!pincodeString.equals("")) {
                        pincode = Integer.parseInt(pincodeString);
                    }

                    String description = etDescription.getText().toString();
                    String category = spCategory.getSelectedItem().toString();

                    if (checkFieldData(name, address, pincode, phoneString, description)) {
                        try {
                            BusinessDatabase data = new BusinessDatabase(name, address, phone, pincode, description, category);

                            mDataBase.child("business").child(category).child(userId).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(BusinessRegistrationActivity.this, "Success", Toast.LENGTH_SHORT);
                                    } else {
                                        Toast.makeText(BusinessRegistrationActivity.this, "Failed", Toast.LENGTH_SHORT);
                                    }
                                }
                            });
                        } catch (DatabaseException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    public boolean checkFieldData(String companyName, String address, int pincode
            , String phone, String description) {
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
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = mStorageReference.child("images/" + userId);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(BusinessRegistrationActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(BusinessRegistrationActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }
}