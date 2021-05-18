package org.gptccherthala.virtualqueue.business;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.gptccherthala.virtualqueue.R;
import org.gptccherthala.virtualqueue.user.USER_QUEUE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JoinedUserQueuAdapter extends RecyclerView.Adapter<JoinedUserQueuAdapter.Userjoined> {

    ArrayList<USER_QUEUE> user_queues=new ArrayList<>();
    Context context;
    DatabaseReference userRef;
    String bid;

    public void setUser_queues(ArrayList<USER_QUEUE> user_queues,Context context,String bid) {
        this.bid=bid;
        this.context=context;
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

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getting the customer number by position
                String ph="tel:"+Long.toString( user_queues.get(position).getPhone());

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(ph));
                context.startActivity(callIntent);

            }
        });




        holder.alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userRef = FirebaseDatabase.getInstance().getReference().child("user").child(user_queues.get(position).getUid()).child(bid);
                userRef.child("alert").setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("alert" , false);
                        userRef.updateChildren(childUpdates);
                    }
                });
                //for resetting





            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return user_queues.size();
    }

    public static class Userjoined extends  RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public TextView name;
        public ImageButton alert;
        public ImageButton call;
        public Button delete;

        public Userjoined(@NonNull View itemView) {
            super(itemView);
            this.imageView=itemView.findViewById(R.id.img);
            this.name=itemView.findViewById(R.id.uName);
            this.call=itemView.findViewById(R.id.call);
            this.alert=itemView.findViewById(R.id.alert);
            this.delete=itemView.findViewById(R.id.delete);
        }
    }





}
