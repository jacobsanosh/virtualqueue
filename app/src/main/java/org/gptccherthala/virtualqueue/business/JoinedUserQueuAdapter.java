package org.gptccherthala.virtualqueue.business;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.gptccherthala.virtualqueue.R;
import org.gptccherthala.virtualqueue.user.USER_QUEUE;

import java.util.ArrayList;

public class JoinedUserQueuAdapter extends RecyclerView.Adapter<JoinedUserQueuAdapter.Userjoined> {

    ArrayList<USER_QUEUE> user_queues=new ArrayList<>();
    Context context;

    public void setUser_queues(ArrayList<USER_QUEUE> user_queues) {
        this.user_queues = user_queues;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Userjoined onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.joined_users_list,parent,false);
        Userjoined userjoined=new Userjoined(view);
        return userjoined;
    }

    @Override
    public void onBindViewHolder(@NonNull Userjoined holder, int position) {


    }

    @Override
    public int getItemCount() {
        return user_queues.size();
    }

    public static class Userjoined extends  RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public TextView name;

        public Userjoined(@NonNull View itemView) {
            super(itemView);
            this.imageView=itemView.findViewById(R.id.img);
            this.name=itemView.findViewById(R.id.uName);
        }
    }

}
