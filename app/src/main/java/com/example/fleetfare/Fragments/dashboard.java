package com.example.fleetfare.Fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fleetfare.FirebaseUtils.FireBaseReadandWrite;
import com.example.fleetfare.Interfaces.onStatsAvailable;
import com.example.fleetfare.Models.Stats;
import com.example.fleetfare.R;

public class dashboard extends Fragment implements onStatsAvailable {

    LinearLayout addRecordLinLayout, getRecordLinLayout, exportRecordLinLayout;
    RelativeLayout addRecordRelLayout, getRecordRelLayout, exportRecordRelLayout;
    TextView addRecordTxt, getRecordTxt, exportRecordTxt, amountTxt, countTxt, weightTxt;
    ImageView addRecordImg, getRecordImg, exportRecordImg;
    NavController navController;
    FireBaseReadandWrite fireBaseReadandWrite;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initializeViews(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.status_bar_color_dashboard));
            getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getActivity(), R.color.status_bar_color_display_record));
        }
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        onClickListener();

        fireBaseReadandWrite = new FireBaseReadandWrite();

        fireBaseReadandWrite.getStats(this);
    }

    private void onClickListener() {
        addRecordLinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewRecordFragment();
            }
        });
        addRecordRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewRecordFragment();
            }
        });
        addRecordTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewRecordFragment();
            }
        });
        addRecordImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewRecordFragment();
            }
        });


        getRecordLinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGetRecordFragment();
            }
        });
        getRecordRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGetRecordFragment();
            }
        });
        getRecordTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGetRecordFragment();
            }
        });
        getRecordImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGetRecordFragment();
            }
        });

        exportRecordLinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToExportRecordFragment();
            }
        });
        exportRecordRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToExportRecordFragment();
            }
        });
        exportRecordTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToExportRecordFragment();
            }
        });
        exportRecordImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToExportRecordFragment();
            }
        });
    }

    private void goToExportRecordFragment() {
        navController.navigate(R.id.action_dashboard_to_dataExportFragment);
    }

    private void goToGetRecordFragment() {
        navController.navigate(R.id.action_dashboard_to_selectDisplayRange);
    }
    private void initializeViews(View view) {
        addRecordLinLayout = view.findViewById(R.id.gotoaddrecordlinearlay);
        getRecordLinLayout = view.findViewById(R.id.gotogetrecordlinearlay);
        exportRecordLinLayout = view.findViewById(R.id.gotoexportrecordlinearlay);

        addRecordRelLayout = view.findViewById(R.id.addrecrellayout);
        getRecordRelLayout = view.findViewById(R.id.getrecrellayout);
        exportRecordRelLayout = view.findViewById(R.id.exportrecrellayout);

        addRecordTxt = view.findViewById(R.id.newrectxt);
        getRecordTxt = view.findViewById(R.id.oldrectxt);
        exportRecordTxt = view.findViewById(R.id.exportrectxt);

        addRecordImg = view.findViewById(R.id.addicon);
        getRecordImg = view.findViewById(R.id.geticon);
        exportRecordImg = view.findViewById(R.id.exporticon);

        amountTxt = view.findViewById(R.id.amount_txt);
        weightTxt = view.findViewById(R.id.weight_txt);
        countTxt = view.findViewById(R.id.shipment_count_txt);

    }
    public void goToNewRecordFragment() {
        navController.navigate(R.id.action_dashboard_to_addRecordFragment);
    }


    @Override
    public void onSuccess(Stats stats) {
        amountTxt.setText("Rs. "+stats.getTotalAmount());
        weightTxt.setText(stats.getTotalWeight()+" Kg");
        countTxt.setText(""+stats.getNumRecords());
    }

    @Override
    public void onFailure(String s) {

    }
}