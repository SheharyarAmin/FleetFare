package com.example.fleetfare.FirebaseUtils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fleetfare.Interfaces.onMonthDataFetched;
import com.example.fleetfare.Interfaces.onStatsAvailable;
import com.example.fleetfare.Models.Record;
import com.example.fleetfare.Interfaces.onDataFetched;
import com.example.fleetfare.Models.Stats;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FireBaseReadandWrite {


    FirebaseDatabase firebaseDatabase;
    DatabaseReference firebaseReference;


    public FireBaseReadandWrite() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseReference = firebaseDatabase.getReference();
        firebaseReference.keepSynced(true);
    }


    public void writeToFireBase(Record record1){
        DatabaseReference recordRef = firebaseReference.child(convertDateFormat(record1.getDate())).push();

        recordRef.setValue(record1, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    addStats(record1);
                } else {
                    Log.d("TAG", "Failed to write record to database: " + error.getMessage());
                }
            }
        });

        // Set a disconnection listener to trigger addStats() if the device disconnects before the data is uploaded to Firebase
        recordRef.onDisconnect().cancel();
        recordRef.onDisconnect().setValue(record1, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {
                    // Device disconnected before data was uploaded, trigger addStats()
//                    addStats(record1);
                } else {
                    Log.d("TAG", "Failed to set disconnection listener: " + error.getMessage());
                }
            }
        });
    }

    public void getStats(onStatsAvailable onStatsAvailable){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MMM", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        DatabaseReference statsRef = firebaseReference.child("stats/"+currentDateandTime);
        statsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Stats stats = snapshot.getValue(Stats.class);
                if (stats==null){
                    stats = new Stats(0, 0.0F, 0.0F);
                }
                if (stats.getNumRecords()<=0 || stats.getTotalAmount()<0 || stats.getTotalWeight()<0){
                    stats = new Stats(0, 0.0F, 0.0F);
                }
                onStatsAvailable.onSuccess(stats);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "Failed to read statistics from database: " + error.getMessage());
            }
        });
    }

    private void addStats(Record record) {
        String[] dateComponents = record.getDate().split("/");
        String year = dateComponents[2];
        String month = dateComponents[1];

        DatabaseReference statsRef = firebaseReference.child("stats").child(year).child(month);
        statsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Stats stats = snapshot.getValue(Stats.class);
                if (stats==null){
                    stats = new Stats(0, 0.0F, 0.0F);
                }
                if (stats.getNumRecords()<=0 || stats.getTotalAmount()<0 || stats.getTotalWeight()<0){
                    stats = new Stats(0, 0.0F, 0.0F);
                }
                stats.addnumRecords(1);
                stats.addtotalAmount(record.getTotalPayement());
                stats.addtotalWeight(record.getWeightAfterDeduction());
                statsRef.setValue(stats);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "Failed to read statistics from database: " + error.getMessage());
            }
        });
    }
    private void subtractStats(Record record) {
        String[] dateComponents = record.getDate().split("/");
        String year = dateComponents[2];
        String month = dateComponents[1];

        DatabaseReference statsRef = firebaseReference.child("stats").child(year).child(month);
        statsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Stats stats = snapshot.getValue(Stats.class);
                if (stats==null){
                    stats = new Stats(0, 0.0F, 0.0F);
                }
                if (stats.getNumRecords()<=0 || stats.getTotalAmount()<0 || stats.getTotalWeight()<0){
                    stats = new Stats(0, 0.0F, 0.0F);
                }else {
                    stats.subtractnumRecords(1);
                    stats.subtracttotalAmount(record.getTotalPayement());
                    stats.subtracttotalWeight(record.getWeightAfterDeduction());
                }
                statsRef.setValue(stats);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "Failed to read statistics from database: " + error.getMessage());
            }
        });
    }


    public List<String> readTabsFromFireBase() {
        List<String> tabsList = new ArrayList<>();
        firebaseReference.child("2022/Months/Oct/Days/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot key :snapshot.getChildren()) {
                    key.getKey();
                    int count = (int) key.getChildrenCount();
                    Log.d("TAG",count+"");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return tabsList;
    }

    public String convertDateFormat(String dateString) {
        String formattedDate = "";
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
            Date date = inputFormat.parse(dateString);

            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MMM/dd", Locale.getDefault());
            formattedDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }


    public void readOnDate(String date,final onDataFetched onDataFetchedInterface){

        DatabaseReference reference = firebaseReference.child(convertDateFormat(date));
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Record> records = new ArrayList<>();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Record record = childSnapshot.getValue(Record.class);
                    record.id = childSnapshot.getKey();
                    records.add(record);

                }
                onDataFetchedInterface.onSuccess(records);
                // use the records list as required
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // handle error
            }
        });
    }


    public void readOnMonth(String date, final onMonthDataFetched onMonthDataFetchedInterface) {
        DatabaseReference reference = firebaseReference.child(date);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Record> records = new ArrayList<>();
                for (DataSnapshot daySnapshot : snapshot.getChildren()) {
                    for (DataSnapshot recordSnapshot : daySnapshot.getChildren()) {
                        Record record = recordSnapshot.getValue(Record.class);
                        records.add(record);
                    }
                }
                onMonthDataFetchedInterface.onSuccess(records);
                // use the records list as required
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // handle error
            }
        });
    }


    public void deleteEntry(Record record) {
        DatabaseReference databaseReference = firebaseReference;
        DatabaseReference recordRef = databaseReference.child(convertDateFormat(record.getDate())+"/"+record.id);
        databaseReference.onDisconnect().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("FirebaseUtils","Value Removed Successfully!");
                subtractStats(record);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("FirebaseUtils","Error While Removing Value: "+e.toString());
            }
        });
        recordRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("FirebaseUtils","Value Removed Successfully!");
                subtractStats(record);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("FirebaseUtils","Error While Removing Value: "+e.toString());
            }
        });

    }
}
