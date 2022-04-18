package com.zeeshanvirani.projectcelia;

import androidx.appcompat.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class RateDialogFragment extends DialogFragment {

    private static final String TAG = "ProjectCelia:RateDialogFragment";
    View view;

    private Button[] strengthTypeButtons;

    BrewHistoryListAdapter adapter;
    String dataID;

    public RateDialogFragment( BrewHistoryListAdapter adapter, String dataID ) {
        this.adapter = adapter;
        this.dataID = dataID;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view = getLayoutInflater().inflate( R.layout.dialog_rate_brew, null );
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireActivity(), R.style.Theme_ProjectCelia_MaterialAlertDialog);
        builder.setMessage("Rate this brew:")
                .setPositiveButton("Submit", (dialog, id) -> {} )
                .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss() )
                .setView( view );
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog dialog = (AlertDialog) getDialog();
        if ( dialog != null ) {

            NumberPicker rating = view.findViewById(R.id.rate_rating_np);
            rating.setMinValue(0);
            rating.setMaxValue(10);

            Button strength_weak = view.findViewById(R.id.button_strength_weak);
            Button strength_perfect = view.findViewById(R.id.button_strength_perfect);
            Button strength_strong = view.findViewById(R.id.button_strength_strong);

            strengthTypeButtons = new Button[]{ strength_weak, strength_perfect, strength_strong };

            strength_weak.setOnClickListener( view1 -> adjustStrengthButtons(0) );
            strength_perfect.setOnClickListener( view1 -> adjustStrengthButtons(1) );
            strength_strong.setOnClickListener( view1 -> adjustStrengthButtons(2) );

            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {

                if ( getSelectedStrength().equals("") ) {
                    Log.d(TAG, "No Strength Selected.");
                    // Display error to user
                    return;
                }

                // Update rating data in database
                TempDatabaseClass.brewHistory_ratings[ Integer.parseInt( dataID ) ] = rating.getValue() + "/10";
                // Let page know to refresh
                adapter.notifyItemChanged( Integer.parseInt( dataID ) );

                // Update user's next strength in users collection
                TempDatabaseClass.brewHistory_strength[ Integer.parseInt( dataID ) ] = getSelectedStrength();

                dialog.dismiss();
            });
        }
    }

    public void adjustStrengthButtons( int selButton ) {
        for ( int i = 0; i < strengthTypeButtons.length; i++ ) {
            strengthTypeButtons[i].setSelected( i == selButton & !strengthTypeButtons[i].isSelected() );
        }
    }

    // 0 = weak, 1 = perfect, 2 = strong
    public String getSelectedStrength() {
        for ( int i = 0; i < strengthTypeButtons.length; i++ ) {
            if ( strengthTypeButtons[i].isSelected() ) return String.valueOf(i+1);
        }
        return "";
    }
}