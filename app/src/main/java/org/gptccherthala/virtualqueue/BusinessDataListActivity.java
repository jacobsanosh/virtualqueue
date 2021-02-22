package org.gptccherthala.virtualqueue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BusinessDataListActivity extends AppCompatActivity {

    private RecyclerView mBusinessDataListRecView;
    private DatabaseReference mDataBase;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_data_list);

        Intent intent = getIntent();

        category = intent.getStringExtra("category");

        mBusinessDataListRecView = findViewById(R.id.businessDataListRecView);
        mDataBase = FirebaseDatabase.getInstance().getReference("/business/" + category);

        ArrayList<BusinessDatabase> businessDatabase = new ArrayList<>();

        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        businessDatabase.add(dataSnapshot.getValue(BusinessDatabase.class));
                    }

                    BusinessDataListRecViewAdapter adapter = new BusinessDataListRecViewAdapter();
                    adapter.setBusinessDatabase(businessDatabase);

                    mBusinessDataListRecView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mBusinessDataListRecView.setLayoutManager(new LinearLayoutManager(this));
    }
}