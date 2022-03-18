package com.zeeshanvirani.projectcelia;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class RateDialogFragment extends DialogFragment {

    BrewHistoryListAdapter adapter;
    String dataID;
    public RateDialogFragment( BrewHistoryListAdapter adapter, String dataID ) {
        this.adapter = adapter;
        this.dataID = dataID;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        assert getActivity() != null;
        // Use the Builder class for convenient dialog construction
        final NumberPicker numberPicker = new NumberPicker(getActivity());
        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(0);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity(), R.style.Theme_ProjectCelia_MaterialAlertDialog);
        builder.setMessage("What would you rate this cup of coffee?")
                .setPositiveButton("Rate", (dialog, id) -> {
                    // Update rating data in database
                    TempDatabaseClass.brewHistory_ratings[ Integer.parseInt( dataID ) ] = numberPicker.getValue() + "/10";

                    // Let page know to refresh
                    adapter.notifyItemChanged( Integer.parseInt( dataID ) );
                })
                .setNegativeButton("Cancel", (dialog, id) -> {})
                .setView( numberPicker );
        // Create the AlertDialog object and return it
        return builder.create();
    }
}