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
import android.widget.Button;
import android.widget.Toast;


import com.example.fleetfare.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

public class selectDisplayRange extends Fragment {


    TextInputEditText dateTextView;
    TextInputLayout datelayout;
    Button doneBtn;
    NavController navController;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker(view);
            }
        });


        datelayout.setEndIconOnClickListener(new View.OnClickListener() {
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
                    dateBundle.putString("date",dateTextView.getText().toString());
                    navController.navigate(R.id.action_selectDisplayRange_to_recordOnDate,dateBundle);
                }else{
                    Toast.makeText(getContext(), "Select a Date First!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void datePicker(View view) {
        MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
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


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_display_range, container, false);
        dateTextView = view.findViewById(R.id.date_menu_txt);
        datelayout = view.findViewById(R.id.datelayout);
        doneBtn = view.findViewById(R.id.savebtn);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.status_bar_color));
            getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getActivity(), R.color.white));
        }

        // Inflate the layout for this fragment
        return view;
    }
}