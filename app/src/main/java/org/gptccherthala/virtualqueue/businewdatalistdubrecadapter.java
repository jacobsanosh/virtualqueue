package org.gptccherthala.virtualqueue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class businewdatalistdubrecadapter extends RecyclerView.Adapter<businewdatalistdubrecadapter.viewHolder> {
    private Context i;

    public businewdatalistdubrecadapter(Context i) {
        this.i = i;
    }

    private ArrayList<BusinessDatabase> databases = new ArrayList<>();

    public void setDatabases(ArrayList<BusinessDatabase> databases) {
        this.databases = databases;
        notifyDataSetChanged();
    }


    public businewdatalistdubrecadapter() {
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_list_sub_item,parent,false);
        viewHolder holder =new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.img.setImageResource(databases.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return databases.size();
    }

    public class  viewHolder extends RecyclerView.ViewHolder{
        ImageButton img;
    public viewHolder(@NonNull View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.image);


    }
}
}
