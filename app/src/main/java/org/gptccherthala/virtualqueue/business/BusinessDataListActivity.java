package org.gptccherthala.virtualqueue.business;

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

    String category;
    private RecyclerView mBusinessDataListRecView;
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_data_list);

        //Intent intent = getIntent();

        //category = intent.getStringExtra("category");

        //startActivity(new Intent(this, BusinessDataListSubActivity.class));

        mBusinessDataListRecView = findViewById(R.id.businessDataListRecView);
        mDataBase = FirebaseDatabase.getInstance().getReference("/business/Shop/Textile");

        ArrayList<BusinessDatabase> businessDatabase = new ArrayList<>();

        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    //Toast.makeText(getApplicationContext(), "If ss", Toast.LENGTH_SHORT).show();
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