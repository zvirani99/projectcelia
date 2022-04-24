package com.zeeshanvirani.projectcelia;

import androidx.appcompat.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

public class ChangePasswordDialogFragment extends DialogFragment {

    private static final String TAG = "ProjectCelia:ChangePasswordDialogFragment";
    View view;

    // Necessary constructor
    public ChangePasswordDialogFragment() {}

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Get layout for dialog
        view = getLayoutInflater().inflate( R.layout.dialog_change_password, null );

        // Setup dialog builder
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireActivity(), R.style.Theme_ProjectCelia_MaterialAlertDialog);
        builder.setMessage("Enter your new password.")
                .setPositiveButton("Submit", (dialog, id) -> {} )
                .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss() )
                .setView( view );

        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog dialog = (AlertDialog) getDialog();

        // Check if dialog is null
        if ( dialog != null ) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                // Submit button clicked
                EditText password = view.findViewById(R.id.chpw_password);
                EditText confirmpassword = view.findViewById(R.id.chpw_confpassword);
                // Check if fields are empty
                if ( password.getText().toString().equals("") ) {
                    Log.d(TAG, "Password field cannot be empty.");
                    password.setError( "Password cannot be empty" );
                    return;
                }
                if ( confirmpassword.getText().toString().equals("") ) {
                    Log.d(TAG, "Confirm Password field cannot be empty.");
                    confirmpassword.setError( "Confirm Password cannot be empty" );
                    return;
                }
                // Check if both password fields match
                if (password.getText().equals(confirmpassword.getText())) {
                    assert FirebaseAuth.getInstance().getCurrentUser() != null;
                    FirebaseAuth.getInstance().getCurrentUser().updatePassword( password.getText().toString() )
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User password has been updated.");
                                } else {
                                    Log.d(TAG, "Error occurred. Password not updated.");
                                }
                            });
                    dialog.dismiss();
                } else {
                    Log.d(TAG, "Entered passwords do not match.");
                    password.setError( "Passwords do not match" );
                    confirmpassword.setError( "Passwords do not match" );
                }
            });
        }
    }
}