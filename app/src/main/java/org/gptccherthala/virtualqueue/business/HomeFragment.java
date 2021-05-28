package org.gptccherthala.virtualqueue.business;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.gptccherthala.virtualqueue.R;
import org.gptccherthala.virtualqueue.user.USER_QUEUE;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    View view;
    RecyclerView joinedUserRecview;
    private DatabaseReference businessRef;
    String bId;
    private FirebaseFirestore db;
    boolean isOpen;
    SwitchMaterial isOpenOrClosedSwitch;
    TextView shop_details;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home_business, container, false);


        //initialization

        db = FirebaseFirestore.getInstance();
        bId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        businessRef = FirebaseDatabase.getInstance().getReference().child("business").child(bId);
        isOpen = false;
        isOpenOrClosedSwitch = view.findViewById(R.id.openOrClosed);
        shop_details = view.findViewById(R.id.shop_detail);

        //adding button click listener for switch

        isOpenOrClosedSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpenOrClosedSwitch.isChecked()) {
                    businessRef.child("isopen").setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            shop_details.setText("Shop Open");
                            isOpenOrClosedSwitch.setChecked(true);
                            RecyclerViewOfUser();
                        }
                    });
                } else {
                    businessRef.child("isopen").setValue(false).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            shop_details.setText("Shop close");
                            isOpenOrClosedSwitch.setChecked(false);
                        }
                    });
                }
            }
        });


        //checking whether is open or closed from db

        businessRef.child("isopen").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.getValue().toString() == "true") {
                        shop_details.setText("Shop Open");
                        isOpenOrClosedSwitch.setChecked(true);
                        RecyclerViewOfUser();
                    } else {

                        shop_details.setText("Shop closed");
                        isOpenOrClosedSwitch.setChecked(false);
                    }
                }
                //setting an default value for isopen in db
                else {
                    businessRef.child("isopen").setValue(false).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            System.out.println("added into the db");
                        }
                    });

                }
            }
        });
        //checking whether nood is exists
        businessRef.child("qlength").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    //adding an default qlength
                    businessRef.child("qlength").setValue(0).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            System.out.println("adding an default q length");
                        }
                    });
                }
            }
        });


        return view;
    }


    public void RecyclerViewOfUser() {
        joinedUserRecview = view.findViewById(R.id.joinedUsersRecView);
        JoinedUserQueuAdapter adapter = new JoinedUserQueuAdapter();
        Query busineessRefQuery = businessRef.orderByChild("qlength");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("yes on datachange");

                ArrayList<USER_QUEUE> userjoined = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d("Home", "1");
                    if(ds.getKey().equals("isopen") || ds.getKey().equals("qlength"))
                    {
                        System.out.println(ds.getKey()+"is openfields is open");
                        //resetting the adapter after delete
                        adapter.setUser_queues(userjoined,getActivity(),bId);
                    }
                    else{
                        System.out.println(ds.getKey()+"hahahah");
                        USER_QUEUE data = dataSnapshot.getValue(USER_QUEUE.class);
                        data.setUid(ds.getKey());
                        userjoined.add(data);
                        adapter.setUser_queues(userjoined,getActivity(),bId);
                        Log.d("Home", "2");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("on cancelling");

            }

        };busineessRefQuery.addValueEventListener(postListener);

        joinedUserRecview.setAdapter(adapter);
        joinedUserRecview.setLayoutManager(new LinearLayoutManager(getContext()));
    }


}
