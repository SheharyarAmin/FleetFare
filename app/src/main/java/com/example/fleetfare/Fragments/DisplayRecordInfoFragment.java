package com.example.fleetfare.Fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.fleetfare.Models.Record;
import com.example.fleetfare.R;


public class DisplayRecordInfoFragment extends Fragment {


    TextView dateTxt, vehicleNumberTxt, weightTxt, finalWeightTxt,deductionTxt, afterDedTxt, rateTxt, amountTxt;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_record_info, container, false);
        dateTxt =  view.findViewById(R.id.datedisplaytxt);
        vehicleNumberTxt = view.findViewById(R.id.vehicledisplaytxt);
        finalWeightTxt = view.findViewById(R.id.finalweightdisplaytxt);
        weightTxt = view.findViewById(R.id.weightdisplaytxt);
        afterDedTxt = view.findViewById(R.id.weightafterdeductiondisplaytxt);
        deductionTxt = view.findViewById(R.id.deductiondisplaytxt);
        rateTxt = view.findViewById(R.id.pricedisplaytxt);
        amountTxt = view.findViewById(R.id.paymentdisplaytxt);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.status_bar_color_display_record));
            getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getActivity(), R.color.white));
        }
        // Inflate the layout for this fragment
        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert getArguments() != null;
        Record record = (Record) getArguments().getSerializable("record");
        dateTxt.setText(record.getDate());
        vehicleNumberTxt.setText(record.getVehicleNO());
        weightTxt.setText(record.getWeight() +" kg");
        afterDedTxt.setText(record.getWeightAfterDeduction() +" kg");
        finalWeightTxt.setText(record.getWeightInMann() +" Mann");
        amountTxt.setText("Rs. "+ record.getTotalPayement());
        rateTxt.setText("Rs. "+ record.getPricePerMann());
        deductionTxt.setText(record.getWeightDeductionPercent() +"%");

    }
}