package com.example.fleetfare.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.fleetfare.FirebaseUtils.FireBaseReadandWrite;
import com.example.fleetfare.Models.Record;
import com.example.fleetfare.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddRecordFragment extends Fragment {

    public AddRecordFragment() {
        // Required empty public constructor
    }
    ScrollView scrollView;


    Button Save;
    Record record;
    TextInputLayout datelayout,vehiclelayout,weightlayout, deductionlayout,pricelayout,commentslayout;
    TextInputEditText datetxt,vehicletxt,weighttxt,deductiontxt,pricetxt,commentstxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        record = new Record();

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        datelayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker(view);
            }
        });



        datetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker(view);
            }
        });

//        datelayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                datePicker(view);
//            }
//        });


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Data Validation
                if(DataValidation())
                {
                    return;
                }
                //Processing Data before uploading
                record.setDate(datetxt.getText().toString());
                record.setComments(commentstxt.getText().toString());
                record.setVehicleNO(vehicletxt.getText().toString());

                record.setWeight(Float.parseFloat(weighttxt.getText().toString()));
                record.setPricePerMann(Float.parseFloat(pricetxt.getText().toString()));
                if (deductiontxt.getText().toString().length() != 0)
                    record.setWeightDeductionPercent(Float.parseFloat(deductiontxt.getText().toString()));

                Float a = (float) (record.getWeight()*(record.getWeightDeductionPercent()/100.0));
                Float b = (float) record.getWeight();
                record.setWeightAfterDeduction(b-a);
                record.setWeightInMann((float) (record.getWeightAfterDeduction()/40.0));
                record.setTotalPayement(record.getPricePerMann()*record.getWeightInMann());

                if (record.getTotalPayement()<0){
                    Toast.makeText(requireContext(), "Total Amount is Negative", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Writing Data in DB
                FireBaseReadandWrite fireBaseReadandWrite = new FireBaseReadandWrite();
                fireBaseReadandWrite.writeToFireBase(record);

                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                scrollView.smoothScrollTo(0,0);

                //Data Written Successfully
                datetxt.setText("");
                datetxt.clearFocus();
                if(datelayout.isErrorEnabled())
                {
                    datelayout.setErrorEnabled(false);
                }
                commentstxt.setText("");
                commentstxt.clearFocus();
                vehicletxt.setText("");
                vehicletxt.clearFocus();
                if(vehiclelayout.isErrorEnabled())
                {
                    vehiclelayout.setErrorEnabled(false);
                }
                weighttxt.setText("");
                weighttxt.clearFocus();
                if(weightlayout.isErrorEnabled())
                {
                    weightlayout.setErrorEnabled(false);
                }
                pricetxt.setText("");
                pricetxt.clearFocus();
                if(pricelayout.isErrorEnabled())
                {
                    pricelayout.setErrorEnabled(false);
                }
                deductiontxt.setText("");
                deductiontxt.clearFocus();

                Toast.makeText(getContext(), "Data Saved Successfully!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean DataValidation() {
        boolean a = validateData(datetxt, datelayout, "Date is Required");
        boolean b = validateData(vehicletxt, vehiclelayout, "Vehicle Number is Required");
        boolean c = validateData(weighttxt, weightlayout, "Weight is Required");
        boolean d = validateData(pricetxt, pricelayout, "Price is Required");
        if(a || b || c ||d) {
            return true;
        }
        return false;
    }

    private boolean validateData(TextInputEditText datetxt, TextInputLayout datelayout, String s) {
        if (datetxt.getText().length() == 0) {
            datelayout.setErrorEnabled(true);
            datelayout.setError(s);
            return true;
        }
        return false;
    }

    private void InitalizingViews(View view) {

        Save = view.findViewById(R.id.savebtn);


        vehicletxt = view.findViewById(R.id.vehiclenotxt);
        weighttxt = view.findViewById(R.id.weighttxt);
        deductiontxt = view.findViewById(R.id.deductiontxt);
        pricetxt = view.findViewById(R.id.pricetxt);
        commentstxt = view.findViewById(R.id.commentstxt);


        vehiclelayout = view.findViewById(R.id.vehiclelayout);
        weightlayout = view.findViewById(R.id.weightlayout);
        deductionlayout = view.findViewById(R.id.deductionlayout);
        pricelayout = view.findViewById(R.id.pricelayout);
        commentslayout = view.findViewById(R.id.commentslayout);
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
                datetxt.setText(formattedDate);

//                datetxt.setText(""+datePicker.getHeaderText());
            }
        });

        datePicker.show(getChildFragmentManager(),"TAG");
    }



    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_record, container, false);
        datetxt = view.findViewById(R.id.datetxt);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.status_bar_color_dashboard));
            getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getActivity(), R.color.status_bar_color_display_record));
        }

        datelayout = view.findViewById(R.id.datelayout);
        InitalizingViews(view);

        scrollView = view.findViewById(R.id.addentryscrollview);
        return view;
    }
}