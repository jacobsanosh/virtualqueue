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

        String type[];

        switch (category) {
            case "Hotel":
                type = getResources().getStringArray(R.array.type_hotel);
                for (int i = 0; i < type.length; i++) {
                    data.add(new BusinessDatabase(R.drawable.hotel, type[i], category));
                }
                break;
            case "Shop":
                type = getResources().getStringArray(R.array.type_shop);
                for (int i = 0; i < type.length; i++) {
                    data.add(new BusinessDatabase(R.drawable.shop, type[i], category));
                }
                break;
            case "Office":
                type = getResources().getStringArray(R.array.type_office);
                for (int i = 0; i < type.length; i++) {
                    data.add(new BusinessDatabase(R.drawable.office, type[i], category));
                }
                break;
            case "Bank":
                type = getResources().getStringArray(R.array.type_bank);
                for (int i = 0; i < type.length; i++) {
                    data.add(new BusinessDatabase(R.drawable.bank, type[i], category));
                }
                break;
            case "Hospital":
                type = getResources().getStringArray(R.array.type_hospital);
                for (int i = 0; i < type.length; i++) {
                    data.add(new BusinessDatabase(R.drawable.hospital, type[i], category));
                }
                break;
            case "Other":
                //TODO Implement methods for type other
                break;
            default:
        }
                adapter.setDatabase(data);
                mBusinessDataListSubRecView.setAdapter(adapter);
                mBusinessDataListSubRecView.setLayoutManager(new GridLayoutManager(this, 3));
        }
    }