package org.gptccherthala.virtualqueue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class BusinessDataListSub extends AppCompatActivity {
    public RecyclerView rec ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_data_list_sub);
        rec = findViewById(R.id.recycler);

        ArrayList<BusinessDatabase> data = new ArrayList<>();
        businewdatalistdubrecadapter bus = new businewdatalistdubrecadapter();
        data.add(new BusinessDatabase(R.drawable.bank));
        data.add(new BusinessDatabase(R.drawable.bank));
        data.add(new BusinessDatabase(R.drawable.bank));
        data.add(new BusinessDatabase(R.drawable.bank));
        data.add(new BusinessDatabase(R.drawable.bank));
        data.add(new BusinessDatabase(R.drawable.bank));
        bus.setDatabases(data);
        rec.setAdapter(bus);
        rec.setLayoutManager(new GridLayoutManager(this,3));
    }

}