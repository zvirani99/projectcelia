package com.zeeshanvirani.projectcelia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Calendar;


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

    private FirebaseFirestore fsInstance = FirebaseFirestore.getInstance();

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

//        assert getActivity() != null;
//        if ( PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext())
//                .getBoolean( "notifications_darklight_mode", true) ) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }

        // Initialize views within ViewGroup
        roast_light_btn = view.findViewById(R.id.button_roast_light);
        roast_medium_btn = view.findViewById(R.id.button_roast_medium);
        roast_mediumdark_btn = view.findViewById(R.id.button_roast_mediumdark);
        roast_dark_btn = view.findViewById(R.id.button_roast_dark);

        cupsize_small_btn = view.findViewById(R.id.button_cupsize_8oz);
        cupsize_med_btn = view.findViewById(R.id.button_cupsize_16oz);
        cupsize_large_btn = view.findViewById(R.id.button_cupsize_20oz);

        start_brew_btn = view.findViewById(R.id.button_startbrew);

        heading_name = view.findViewById(R.id.firstname);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String fullName = sharedPreferences.getString("account_name", "");
        heading_name.setText( fullName.split(" ")[0] );

        // On Click Listener for Roast Type Buttons and Cup Size Buttons
        // Checks if button already selected and unselects it
        // Otherwise selects it and unselects other options
        roast_light_btn.setOnClickListener(view1 -> AdjustRoastButtons( 0 ));

        roast_medium_btn.setOnClickListener(view1 -> AdjustRoastButtons( 1 ));

        roast_mediumdark_btn.setOnClickListener(view1 -> AdjustRoastButtons( 2 ));

        roast_dark_btn.setOnClickListener(view1 -> AdjustRoastButtons( 3 ));

        cupsize_small_btn.setOnClickListener(view1 -> AdjustSizeButtons(0));

        cupsize_med_btn.setOnClickListener(view1 -> AdjustSizeButtons(1));

        cupsize_large_btn.setOnClickListener(view1 -> AdjustSizeButtons(2));

        start_brew_btn.setOnClickListener(view1 -> {
            // Get Data from Database and pass onto new intent
            Map<String, Object> brew = new HashMap<>();

            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy", Locale.US);
            String currentDate = sdf.format(new Date());
            sdf = new SimpleDateFormat("HH:mm:ss", Locale.US);
            String currentTime = sdf.format(new Date());

            brew.put(DataHandler.DB_USER_ID, FirebaseAuth.getInstance().getCurrentUser().getUid());
            brew.put(DataHandler.DB_DATE, currentDate);
            brew.put(DataHandler.DB_TIME, currentTime);
            brew.put(DataHandler.DB_ROAST_TYPE, getSelectedRoast());
            brew.put(DataHandler.DB_CUP_SIZE, getSelectedSize());
            brew.put(DataHandler.DB_RATING, "null");

            DocumentReference docRef = FirebaseFirestore.getInstance().collection("users")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            docRef.get().addOnSuccessListener(documentSnapshot -> {
                String next_temp = (String) documentSnapshot.get("next_target_temperature");
                String next_sat = (String) documentSnapshot.get("next_target_saturation");
                brew.put(DataHandler.DB_TEMPERATURE, next_temp);
                brew.put(DataHandler.DB_TARGET_SATURATION, next_sat);
            });

            // Calculate target temps and sats from cindy's algorithm

            // Store this into database
            fsInstance.collection("brews").add(brew);

            // Create extra for intents
            startActivity(new Intent(getActivity().getApplicationContext(), BrewingProcess.class));
        });

        return view;
    }

    // AdjustRoastButton
    // Inputs: selButton (int); Which button was clicked
    // 0 = light, 1 = medium, 2 = meddark, 3 = dark
    // Selects button if unselected and replaces text with full name and expands width
    // All other buttons are unselected, set to normal width, and short name
    public void AdjustRoastButtons( int selButton ) {

        Button[] brewButtons = { roast_light_btn, roast_medium_btn, roast_mediumdark_btn, roast_dark_btn };
        String[] unselBrewText = {"L", "M", "MD", "D"};
        String[] selBrewText = {"Light", "Medium", "MedDark", "Dark"};

        for ( int i = 0; i < brewButtons.length; i++ ) {

            if ( i == selButton & !brewButtons[i].isSelected() ) {
                brewButtons[i].setSelected( true );

                brewButtons[i].setText( selBrewText[i] );
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(dpToPx(90), dpToPx(60) );
                lparams.setMargins(dpToPx(10), dpToPx(10), dpToPx(10), dpToPx(10));
                brewButtons[i].setLayoutParams( lparams );
            } else {
                brewButtons[i].setSelected( false );

                brewButtons[i].setText( unselBrewText[i] );
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(dpToPx(50), dpToPx(60) );
                lparams.setMargins(dpToPx(10), dpToPx(10), dpToPx(10), dpToPx(10));
                brewButtons[i].setLayoutParams( lparams );
            }

        }

    }

    // AdjustSizeButton
    // Inputs: selButton (int); Which button was clicked
    // 0 = small, 1 = medium, 2 = large
    // Selects button if unselected
    // All other buttons are unselected
    public void AdjustSizeButtons( int selButton ) {
        Button[] sizeButtons = { cupsize_small_btn, cupsize_med_btn, cupsize_large_btn };
        for ( int i = 0; i < sizeButtons.length; i++ ) {
            sizeButtons[i].setSelected(i == selButton & !sizeButtons[i].isSelected());
        }
    }

    // Converts any pixel value to dp and returns it
    public int dpToPx(int dp) {
        assert getActivity() != null;
        float density = getActivity().getApplicationContext().getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    public String getSelectedRoast() {
        if ( roast_light_btn.isSelected() ) return roast_light_btn.getText().toString();
        else if ( roast_medium_btn.isSelected() ) return roast_medium_btn.getText().toString();
        else if ( roast_mediumdark_btn.isSelected() ) return roast_mediumdark_btn.getText().toString();
        else if ( roast_dark_btn.isSelected() ) return roast_dark_btn.getText().toString();
        else return "";
    }

    public String getSelectedSize() {
        if ( cupsize_small_btn.isSelected() ) return cupsize_small_btn.getText().toString();
        else if ( cupsize_med_btn.isSelected() ) return cupsize_med_btn.getText().toString();
        else if ( cupsize_large_btn.isSelected() ) return cupsize_large_btn.getText().toString();
        else return "";
    }
}