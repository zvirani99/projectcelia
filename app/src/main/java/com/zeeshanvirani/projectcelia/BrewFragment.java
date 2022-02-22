package com.zeeshanvirani.projectcelia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class BrewFragment extends Fragment {

    // Define component variables
    private Button roast_light_btn;
    private Button roast_medium_btn;
    private Button roast_mediumdark_btn;
    private Button roast_dark_btn;

    private Button cupsize_small_btn;
    private Button cupsize_med_btn;
    private Button cupsize_large_btn;

    public BrewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate layout and set variable
        View view = inflater.inflate(R.layout.fragment_brew, container, false);

        // Initialize components within view
        roast_light_btn = (Button) view.findViewById(R.id.button_roast_light);
        roast_medium_btn = (Button) view.findViewById(R.id.button_roast_medium);
        roast_mediumdark_btn = (Button) view.findViewById(R.id.button_roast_mediumdark);
        roast_dark_btn = (Button) view.findViewById(R.id.button_roast_dark);

        cupsize_small_btn = (Button) view.findViewById(R.id.button_cupsize_8oz);
        cupsize_med_btn = (Button) view.findViewById(R.id.button_cupsize_16oz);
        cupsize_large_btn = (Button) view.findViewById(R.id.button_cupsize_20oz);

        // On Click Listener for Roast Type Buttons and Cup Size Buttons
        // Checks if already selected and unselects it
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