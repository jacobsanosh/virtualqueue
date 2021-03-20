package org.gptccherthala.virtualqueue.business;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.gptccherthala.virtualqueue.R;

import java.util.ArrayList;

public class BusinessDataListActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_data_list);

        Bundle data = getIntent().getExtras();

        String type = data.getString("type");
        String category = data.getString("category");

        RecyclerView mBusinessDataListRecView = findViewById(R.id.businessDataListRecView);

        ArrayList<BusinessDatabase> businessDatabase = new ArrayList<>();
        BusinessDataListRecViewAdapter adapter = new BusinessDataListRecViewAdapter(BusinessDataListActivity.this);

        db.collection("business").document(category).collection(type)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        BusinessDatabase dd = new BusinessDatabase(document.get("Name").toString(), document.get("ImageUrl").toString());
                        System.out.println(dd.name + "" + dd.imageUrl);
                        businessDatabase.add(dd);
                        adapter.setBusinessDatabase(businessDatabase);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBusinessDataListRecView.setAdapter(adapter);
        mBusinessDataListRecView.setLayoutManager(new LinearLayoutManager(this));
    }
}