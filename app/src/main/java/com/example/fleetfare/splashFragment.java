package com.example.fleetfare;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;



public class splashFragment extends Fragment {

    NavController navController;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);



        // Use a Handler to delay the transition to the next activity for a few seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the next activity and finish the current activity
                navController.navigate(R.id.action_splashFragment_to_dashboard);
            }
        }, 3000); // 3000 milliseconds delay for 3 seconds
    }
}
