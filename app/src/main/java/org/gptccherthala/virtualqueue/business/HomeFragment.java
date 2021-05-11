package org.gptccherthala.virtualqueue.business;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.gptccherthala.virtualqueue.R;
import org.gptccherthala.virtualqueue.user.USER_QUEUE;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    View view;
    RecyclerView joinedUserRecview;
    private DatabaseReference businessRef;
    String name;
    Long phone;
    Long qlength;
    int pincode;
    String bId;
    private FirebaseFirestore db;

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

        ArrayList<USER_QUEUE> userjoined=new ArrayList<>();
        joinedUserRecview=view.findViewById(R.id.joinedUsersRecView);
        JoinedUserQueuAdapter adapter=new JoinedUserQueuAdapter();
        db = FirebaseFirestore.getInstance();
        bId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        businessRef = FirebaseDatabase.getInstance().getReference().child("business").child(bId);


        businessRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d("Home", "1");

                   businessRef.child(ds.getKey()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                       @Override
                       public void onSuccess(DataSnapshot dataSnapshot) {
                           if (dataSnapshot.exists()) {


                               USER_QUEUE data =dataSnapshot.getValue(USER_QUEUE.class);
                               userjoined.add(data);
                               adapter.setUser_queues(userjoined);
                               Log.d("Home", "2");
                           }
                       }
                   });


                Log.d("Home", "3");
            }
            }
        });

        Log.d("Home", "4");
        joinedUserRecview.setAdapter(adapter);
        joinedUserRecview.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}
