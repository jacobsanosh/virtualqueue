package org.gptccherthala.virtualqueue.business;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.gptccherthala.virtualqueue.QueueDetails;
import org.gptccherthala.virtualqueue.R;

import java.util.ArrayList;


public class BusinessDataListRecViewAdapter extends RecyclerView.Adapter<BusinessDataListRecViewAdapter.ViewHolder> {

    private ArrayList<BusinessDatabase> businessDatabase = new ArrayList<>();
    private Context mContext;
    private long qlLength;
    private String UID;
    private String bid;
    private String name;
    //firestore
    FirebaseFirestore db;
    //real time object
    DatabaseReference mydatabase;
    public BusinessDataListRecViewAdapter(Context mContext) {
        this.mContext = mContext;
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
        // for fb database connection


        mydatabase = FirebaseDatabase.getInstance().getReference();


        // to get the current user UID from fb


        // to get businnessid through BusinessDatabase object it have a constructor on that it will get that data
        // finds which field is clicked

        //we can set onclick listener by specifying an holder before it

        holder.JButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                bid = businessDatabase.get(position).getBid();
                // to get an length of firebase

                mydatabase.child("Business").child(bid).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        System.out.println("on adding the queue"+qlLength+"    "+bid);
                        qlLength = dataSnapshot.getChildrenCount()+1;
                        QueueDetails(position);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        qlLength=1;
                        System.out.println("on initializing the queue");
                        QueueDetails(position);

                    }
                });


            }
        });


        holder.txtName.setText(businessDatabase.get(position).getName());

        Glide.with(mContext)
                .asBitmap()
                .load(businessDatabase.get(position).getImageUrl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return businessDatabase.size();
    }

    public void setBusinessDatabase(ArrayList<BusinessDatabase> businessDatabase) {
        this.businessDatabase = businessDatabase;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtName;
        private final ImageView image;
        private final Button JButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.text_name);
            image = itemView.findViewById(R.id.image);
            JButton =itemView.findViewById(R.id.JButton);
        }
    }
    public void QueueDetails(int position)
    {
        //DocumentSnapshot tname;
        db = FirebaseFirestore.getInstance();

        db.collection("users").document(UID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot tname = task.getResult();
                name = tname.get("Name").toString();
                QueueDetails qq = new QueueDetails(name,qlLength);
                //   mydatabase.child("Bsuiness").child(bid).child("Q length").setValue(qlength);
                //it will create an table with name Businee and it hav an child with Businessid and again it have an child UID
                // then it store all the data in the firebase using mydatabase reference
                mydatabase.child("Business").child(bid).child(UID).setValue(qq).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {

                            Toast.makeText(mContext,"successfully joined",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(mContext,"failed joined",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

      /*  db.collection("users").document(UID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                name = documentSnapshot.get("Name").toString();
            }
        });*/


    }
}
