package org.gptccherthala.virtualqueue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BusinessDataListRecViewAdapter extends RecyclerView.Adapter<BusinessDataListRecViewAdapter.ViewHolder>{

    private ArrayList<BusinessDatabase> businessDatabase = new ArrayList<>();

    public BusinessDataListRecViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_data_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(businessDatabase.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return businessDatabase.size();
    }

    public void setBusinessDatabase(ArrayList<BusinessDatabase> businessDatabase){
        this.businessDatabase = businessDatabase;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.text_name);
        }
    }
}
