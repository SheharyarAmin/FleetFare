package com.example.fleetfare.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fleetfare.FirebaseUtils.FireBaseReadandWrite;
import com.example.fleetfare.Interfaces.onMonthDataFetched;
import com.example.fleetfare.Models.Record;
import com.example.fleetfare.R;
import com.example.fleetfare.Utils.ExcelExporter;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class dataExportFragment extends Fragment{


    onMonthDataFetched onMonthDataFetched;
    FireBaseReadandWrite fireBaseReadandWrite;
    TextInputEditText dateTextView;
    TextInputLayout datelayout;
    Button doneBtn;
    NavController navController;
    Context context;
    String date="record";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        fireBaseReadandWrite = new FireBaseReadandWrite();
        onMonthDataFetched = new onMonthDataFetched() {
            @Override
            public void onSuccess(List<Record> records) {
                if(records.size()>0) {
                    String yearMonth = date.replace("/", "-");
                    ExcelExporter.exportToExcel(getContext(), records, yearMonth);
                }else{
                    Toast.makeText(context, "No Data for this Month yet!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String e) {

            }
        };


        datelayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker(view);
            }
        });

        context = getContext();
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker(view);
            }
        });
        datelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker(view);
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dateTextView.getText().length()>0)
                {
                    Bundle dateBundle = new Bundle();
                    dateBundle.putString("month",dateTextView.getText().toString());
                    date = dateTextView.getText().toString();
                    fireBaseReadandWrite.readOnMonth(date,  onMonthDataFetched);
//                    navController.navigate(R.id.action_selectDisplayRange_to_recordOnDate,dateBundle);
                }else{
                    Toast.makeText(getContext(), "Select a Month First!", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void datePicker(View view) {
        MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Month")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MMM");
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                long date = (Long) selection;
                String formattedDate = formatter.format(new Date(date));
                dateTextView.setText(formattedDate);
//                dateTextView.setText(""+datePicker.getHeaderText());
            }
        });

        datePicker.show(getChildFragmentManager(),"TAG");
    }

//    private void showMonthPicker(View view) {
//        MonthPickerDialog
//    }






    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_export, container, false);
        dateTextView = view.findViewById(R.id.date_menu_txt);
        datelayout = view.findViewById(R.id.datelayout);
        doneBtn = view.findViewById(R.id.savebtn);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.status_bar_color_export));
            getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getActivity(), R.color.status_bar_color_display_record));
        }
        // Inflate the layout for this fragment
        return view;
    }
//
//    @Override
//    public void onSuccess(List<Record> records) {
//
//    }
//
//    @Override
//    public void onFailure(String e) {
//
//    }
}