package com.zeeshanvirani.projectcelia;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class RateDialogFragment extends DialogFragment {

    BrewHistoryListAdapter adapter;
    String dataID;
    public RateDialogFragment( BrewHistoryListAdapter adapter, String dataID ) {
        this.adapter = adapter;
        this.dataID = dataID;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final NumberPicker numberPicker = new NumberPicker(getActivity());
        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(0);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity(), R.style.RateBrewTheme);
        builder.setMessage("What would you rate this cup of coffee?")
                .setPositiveButton("Rate", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Update rating data in database
                        TempDatabaseClass.brewHistory_ratings[ Integer.parseInt( dataID ) ] = String.valueOf(numberPicker.getValue()) + "/10";

                        // Let page know to refresh
                        adapter.notifyItemChanged( Integer.parseInt( dataID ) );
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setView( numberPicker );
        // Create the AlertDialog object and return it
        return builder.create();
    }
}