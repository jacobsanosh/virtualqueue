
package org.gptccherthala.virtualqueue.business;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.telecom.Call;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.zxing.WriterException;

import org.gptccherthala.virtualqueue.QueueDetails;
import org.gptccherthala.virtualqueue.R;
import org.gptccherthala.virtualqueue.user.BUSINESS_QUEUE;
import org.gptccherthala.virtualqueue.user.USER_QUEUE;
import org.gptccherthala.virtualqueue.user.UserDatabase;
import org.gptccherthala.virtualqueue.user.loadingqr;

import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class BusinessDataListRecViewAdapter extends RecyclerView.Adapter<BusinessDataListRecViewAdapter.ViewHolder> {

    String businessId;
    String userId;
    private ArrayList<BusinessDatabase> businessDatabase = new ArrayList<>();
    private BusinessDatabase bData;
    private Context mContext;
    private Activity activity;
    private DatabaseReference mDataBase;
    private long qLength;

    UserDatabase user = new UserDatabase();
    public BusinessDataListRecViewAdapter(Context mContext) {
        this.mContext = mContext;
        this.activity = (Activity) mContext;
    }

    public BusinessDataListRecViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_data_list_recview_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDataBase = FirebaseDatabase.getInstance().getReference();
        bData = businessDatabase.get(position);
        holder.txtName.setText(bData.getName());
        Glide.with(mContext)
                .asBitmap()
                .load(bData.getImageUrl()).into(holder.image);


//checking da for joining
        mDataBase.child("business").child(businessDatabase.get(position).getbId()).child("isopen").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.getValue().toString() == "true")
                    {
                        //saying whether shop is open or not
                        holder.Details.setText("shop open");

                        //if db shop is open then checking whether the customer is joined or not
                        mDataBase.child("user").child(userId).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot)
                            {
                                if (dataSnapshot.hasChild(businessDatabase.get(position).getbId()))
                                {
                                    //aleady joined user
                                    holder.joinQueue.setText("Alredy joined");
                                    holder.joinQueue.setEnabled(false);
                                }
                                else{
                                    holder.joinQueue.setEnabled(true);
                                    }
                            }
                        });
                    }
                    else
                        {
                        holder.Details.setText("shop closed");

                        holder.joinQueue.setEnabled(false);
                        }
                }

            }
        });




        holder.joinQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bData = businessDatabase.get(position);
                businessId = bData.getbId();
                userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                mDataBase.child("business").child(businessId).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        qLength = dataSnapshot.getChildrenCount() + 1;
                        updateQueueDetails(holder);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        qLength = 1;
                        updateQueueDetails(holder);
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return businessDatabase.size();
    }

    public void setBusinessDatabase(ArrayList<BusinessDatabase> businessDatabase) {
        this.businessDatabase = businessDatabase;
        notifyDataSetChanged();
    }

    public void updateQueueDetails(ViewHolder holder) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
       String name= preferences.getString("Uname","");
       long phone =preferences.getLong("Phone",0);
       int pincode = preferences.getInt("Pincode",0);
        USER_QUEUE user_queue=new USER_QUEUE(name,phone,qLength,pincode);

        QueueDetails queueDetails = new QueueDetails(name,phone,pincode,qLength);
        BUSINESS_QUEUE business_queue =new BUSINESS_QUEUE(bData.getName(),qLength,bData.getPhone());
        System.out.println(business_queue.getPhone());
        mDataBase.child("business").child(businessId).child(userId).setValue(user_queue)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            holder.joinQueue.setEnabled(false);
                            mDataBase.child("user").child(userId).child(businessId).setValue(business_queue)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){

                                                //calling qr code which will contain function for displaying qr
                                                loadingqr qr =new loadingqr(mContext);
                                                qr.displayingqr(userId);

                                            }

                                            else
                                                Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else
                            Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtName,Details;
        private final ImageView image;
        private final Button joinQueue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Details = itemView.findViewById(R.id.Details);
            txtName = itemView.findViewById(R.id.text_name);
            image = itemView.findViewById(R.id.image);
            joinQueue = itemView.findViewById(R.id.button_join_queue);
        }
    }
    //queue details into firebase

}