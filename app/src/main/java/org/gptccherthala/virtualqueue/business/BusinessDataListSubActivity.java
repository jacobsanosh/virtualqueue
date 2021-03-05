package org.gptccherthala.virtualqueue.business;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.gptccherthala.virtualqueue.R;

import java.util.ArrayList;

public class BusinessDataListSubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_data_list_sub);
        RecyclerView mBusinessDataListSubRecView = findViewById(R.id.businessDataListSubRecView);

        Intent intent = getIntent();

        String category = intent.getStringExtra("category");

        ArrayList<BusinessDatabase> data = new ArrayList<>();
        BusinessDataListSubRecViewAdapter adapter = new BusinessDataListSubRecViewAdapter(this);
        if (category.equals("Hotel")) {
            String[] types = getResources().getStringArray(R.array.type_hotel);
            for (int i = 0; i < types.length; i++) {
                data.add(new BusinessDatabase(R.drawable.hotel, types[i]));
            }
        }
        //TODO Set data for remaining types
        adapter.setmDatabase(data);
        mBusinessDataListSubRecView.setAdapter(adapter);
        mBusinessDataListSubRecView.setLayoutManager(new GridLayoutManager(this, 3));
    }
}