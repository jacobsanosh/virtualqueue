package org.gptccherthala.virtualqueue.business;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.gptccherthala.virtualqueue.R;

import java.util.ArrayList;

public class BusinessDataListActivity extends AppCompatActivity {

    private RecyclerView mBusinessDataListRecView;
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_data_list);

        Bundle data = getIntent().getExtras();

        String type = data.getString("type");
        String category = data.getString("category");

        mBusinessDataListRecView = findViewById(R.id.businessDataListRecView);
        mDataBase = FirebaseDatabase.getInstance().getReference("/business/" + category + "/" + type);

        ArrayList<BusinessDatabase> businessDatabase = new ArrayList<>();

        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    BusinessDataListRecViewAdapter adapter = new BusinessDataListRecViewAdapter(BusinessDataListActivity.this);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        businessDatabase.add(dataSnapshot.getValue(BusinessDatabase.class));
                        adapter.setBusinessDatabase(businessDatabase);
                    }
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