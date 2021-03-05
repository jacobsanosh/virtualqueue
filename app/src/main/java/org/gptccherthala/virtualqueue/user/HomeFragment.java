package org.gptccherthala.virtualqueue.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.gptccherthala.virtualqueue.R;
import org.gptccherthala.virtualqueue.business.BusinessDataListSubActivity;

public class HomeFragment extends Fragment {

    ImageButton BtnHotel, BtnOffice, BtnShop, BtnBank,BtnHospital,BtnOthers;
    View view;

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
        view = inflater.inflate(R.layout.fragment_home, container, false);

        BtnHotel = view.findViewById(R.id.BtnHotel);
        BtnOffice = view.findViewById(R.id.BtnOffice);
        BtnShop = view.findViewById(R.id.BtnShop);
        BtnBank = view.findViewById(R.id.BtnBank);
        BtnHospital = view.findViewById(R.id.BtnHospital);
        BtnOthers = view.findViewById(R.id.BtnOthers);

        // checking whether BtnHotel is clicked
        BtnHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent businessDataListSub = new Intent(getActivity(), BusinessDataListSubActivity.class);
                businessDataListSub.putExtra("category", "Hotel");
                startActivity(businessDataListSub);
            }
        });

        // checking whether BtnOffice is clicked
        BtnOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent businessDataListSub = new Intent(getActivity(), BusinessDataListSubActivity.class);
                businessDataListSub.putExtra("category", "Office");
                startActivity(businessDataListSub);
            }
        });

        // checking whether BtnShop is clicked
        BtnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent businessDataListSub = new Intent(getActivity(), BusinessDataListSubActivity.class);
                businessDataListSub.putExtra("category", "Shop");
                startActivity(businessDataListSub);
            }
        });

        // checking whether BtnBank is clicked
        BtnBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent businessDataListSub = new Intent(getActivity(), BusinessDataListSubActivity.class);
                businessDataListSub.putExtra("category", "Bank");
                startActivity(businessDataListSub);
            }
        });

        // for onclick of btnHospital

        BtnHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent businessDataListSub = new Intent(getActivity(), BusinessDataListSubActivity.class);
                businessDataListSub.putExtra("category","Hospital");
                startActivity(businessDataListSub);
            }
        });

        // for other types

        BtnOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent businessDataListSub = new Intent(getActivity(),BusinessDataListSubActivity.class);
                businessDataListSub.putExtra("category","Others");
                startActivity(businessDataListSub);
            }
        });

        return view;
    }
}