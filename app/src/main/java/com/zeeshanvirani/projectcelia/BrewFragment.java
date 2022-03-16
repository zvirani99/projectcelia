package com.zeeshanvirani.projectcelia;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class BrewFragment extends Fragment {

    // Define view variables
    private Button roast_light_btn;
    private Button roast_medium_btn;
    private Button roast_mediumdark_btn;
    private Button roast_dark_btn;

    private Button cupsize_small_btn;
    private Button cupsize_med_btn;
    private Button cupsize_large_btn;

    private Button start_brew_btn;

    private TextView heading_name;

    // Required empty public constructor
    public BrewFragment() {}

    // Called when view is put onto the screen
    // Takes in instance of LayoutInflater, the ViewGroup that the fragment will live in,
    // and a Bundle
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate layout and set variable
        View view = inflater.inflate(R.layout.fragment_brew, container, false);

        // Initialize views within ViewGroup
        roast_light_btn = (Button) view.findViewById(R.id.button_roast_light);
        roast_medium_btn = (Button) view.findViewById(R.id.button_roast_medium);
        roast_mediumdark_btn = (Button) view.findViewById(R.id.button_roast_mediumdark);
        roast_dark_btn = (Button) view.findViewById(R.id.button_roast_dark);

        cupsize_small_btn = (Button) view.findViewById(R.id.button_cupsize_8oz);
        cupsize_med_btn = (Button) view.findViewById(R.id.button_cupsize_16oz);
        cupsize_large_btn = (Button) view.findViewById(R.id.button_cupsize_20oz);

        start_brew_btn = (Button) view.findViewById(R.id.button_startbrew);

        heading_name = (TextView) view.findViewById(R.id.firstname);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String fullName = sharedPreferences.getString("account_name", "");
        heading_name.setText( fullName.split(" ")[0] );

        // On Click Listener for Roast Type Buttons and Cup Size Buttons
        // Checks if button already selected and unselects it
        // Otherwise selects it and unselects other options
        roast_light_btn.setOnClickListener(view1 -> {
            if ( roast_light_btn.isSelected() ) {
                roast_light_btn.setSelected(false);
            } else {
                roast_light_btn.setSelected(true);
                roast_medium_btn.setSelected(false);
                roast_mediumdark_btn.setSelected(false);
                roast_dark_btn.setSelected(false);
            }
        });

        roast_medium_btn.setOnClickListener(view1 -> {
            if ( roast_medium_btn.isSelected() ) {
                roast_medium_btn.setSelected(false);
            } else {
                roast_light_btn.setSelected(false);
                roast_medium_btn.setSelected(true);
                roast_mediumdark_btn.setSelected(false);
                roast_dark_btn.setSelected(false);
            }
        });

        roast_mediumdark_btn.setOnClickListener(view1 -> {
            if ( roast_mediumdark_btn.isSelected() ) {
                roast_mediumdark_btn.setSelected(false);
            } else {
                roast_light_btn.setSelected(false);
                roast_medium_btn.setSelected(false);
                roast_mediumdark_btn.setSelected(true);
                roast_dark_btn.setSelected(false);
            }
        });

        roast_dark_btn.setOnClickListener(view1 -> {
            if ( roast_dark_btn.isSelected() ) {
                roast_dark_btn.setSelected(false);
            } else {
                roast_light_btn.setSelected(false);
                roast_medium_btn.setSelected(false);
                roast_mediumdark_btn.setSelected(false);
                roast_dark_btn.setSelected(true);
            }
        });

        cupsize_small_btn.setOnClickListener(view1 -> {
            if ( cupsize_small_btn.isSelected() ) {
                cupsize_small_btn.setSelected(false);
            } else {
                cupsize_small_btn.setSelected(true);
                cupsize_med_btn.setSelected(false);
                cupsize_large_btn.setSelected(false);
            }
        });

        cupsize_med_btn.setOnClickListener(view1 -> {
            if ( cupsize_med_btn.isSelected() ) {
                cupsize_med_btn.setSelected(false);
            } else {
                cupsize_small_btn.setSelected(false);
                cupsize_med_btn.setSelected(true);
                cupsize_large_btn.setSelected(false);
            }
        });

        cupsize_large_btn.setOnClickListener(view1 -> {
            if ( cupsize_large_btn.isSelected() ) {
                cupsize_large_btn.setSelected(false);
            } else {
                cupsize_small_btn.setSelected(false);
                cupsize_med_btn.setSelected(false);
                cupsize_large_btn.setSelected(true);
            }
        });

        return view;
    }
}