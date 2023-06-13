package com.example.fleetfare.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fleetfare.FirebaseUtils.FireBaseReadandWrite;
import com.example.fleetfare.Models.Record;
import com.example.fleetfare.Interfaces.onDataFetched;
import com.example.fleetfare.R;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class recordOnDate extends Fragment implements onDataFetched {

        NavController navController;
        DayItemAdapter dayItemAdapter;
        List<Record> dayItemList = new ArrayList<>();
        FireBaseReadandWrite fireBaseReadandWrite;
        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            navController = Navigation.findNavController(view);
            date =  getArguments().getString("date");
            fireBaseReadandWrite = new FireBaseReadandWrite();
            fireBaseReadandWrite.readOnDate(date,this);
            dayItemAdapter = new DayItemAdapter(getContext(),dayItemList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(dayItemAdapter);
            dateDisplayTxt.setText(formatDate(date));
        }

        RecyclerView recyclerView;
        TextView dateDisplayTxt;
        String date;
    //    LinearLayout


        @SuppressLint("MissingInflatedId")
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_record_on_date, container, false);
            recyclerView = view.findViewById(R.id.record_in_day);
            dateDisplayTxt = view.findViewById(R.id.datedisplaytxt);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getContext(), R.color.status_bar_color));
                getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getActivity(), R.color.white));
            }

            // Inflate the layout for this fragment
            return view;
        }

    public static String formatDate(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);
            Date date1 = inputFormat.parse(dateString);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd' 'MMM',' yyyy", Locale.ENGLISH);
            String formattedDate = outputFormat.format(date1);

            int day = Integer.parseInt(dateString.substring(0, 2));
            String daySuffix;
            switch (day % 10) {
                case 1:
                    daySuffix = "st";
                    break;
                case 2:
                    daySuffix = "nd";
                    break;
                case 3:
                    daySuffix = "rd";
                    break;
                default:
                    daySuffix = "th";
                    break;
            }

            formattedDate = formattedDate.substring(0, 2) + daySuffix + formattedDate.substring(2);

            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }



    @Override
        public void onSuccess(List<Record> records) {
            dayItemList.clear();
            for (Record record:records) {
                dayItemList.add(record);
            }
            dayItemAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(String e) {

        }

        public class DayItemAdapter extends RecyclerView.Adapter<DayItemAdapter.DayItemViewHolder> {
            List<Record> items;
            Context context;
            public DayItemAdapter(Context context, List<Record> itemList) {
                this.context = context;
                this.items = itemList;
            }

            @NonNull
            @Override
            public DayItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.record_item,parent,false);
                DayItemViewHolder dayItemViewHolder = new DayItemViewHolder(view);
                return dayItemViewHolder;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onBindViewHolder(@NonNull DayItemViewHolder holder, @SuppressLint("RecyclerView") int position) {

                holder.amountTxt.setText("Rs. "+items.get(position).getTotalPayementInString());
                holder.vehicleNumberTxt.setText(items.get(position).getVehicleNO());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("record", items.get(position));
                        navController.navigate(R.id.action_recordOnDate_to_displayRecordInfoFragment,bundle);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        new AlertDialog.Builder(view.getContext())
                                .setTitle("Are you sure you want to delete this entry?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(context, "Deleting the Entry!!!", Toast.LENGTH_SHORT).show();
                                        fireBaseReadandWrite.deleteEntry(items.get(holder.getAdapterPosition()));
                                        items.remove(holder.getAdapterPosition());
                                        dayItemAdapter.notifyItemRemoved(holder.getAdapterPosition());
                                    }
                                }).setNegativeButton("No",null)
                                .show();
                        return true;
                    }
                });
            }


            @Override
            public int getItemCount() {
                return items.size();
            }

            public class DayItemViewHolder extends  RecyclerView.ViewHolder{

                public TextView vehicleNumberTxt, amountTxt;

                public DayItemViewHolder(@NonNull View itemView) {
                    super(itemView);
                    vehicleNumberTxt = itemView.findViewById(R.id.vehicle_no_txt);
                    amountTxt = itemView.findViewById(R.id.amount_txt);
                }
            }
        }
}