package com.zeeshanvirani.projectcelia;

import androidx.appcompat.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

public class ChangePasswordDialogFragment extends DialogFragment {

    View view;

    // Necessary constructor
    public ChangePasswordDialogFragment() {}

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        assert getActivity() != null;
        // Use the Builder class for convenient dialog construction
        view = getLayoutInflater().inflate( R.layout.dialog_change_password, null );
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity(), R.style.Theme_ProjectCelia_MaterialAlertDialog);
        builder.setMessage("Enter your new password.")
                .setPositiveButton("Submit", (dialog, id) -> {} )
                .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss() )
                .setView( view );
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog dialog = (AlertDialog) getDialog();
        if ( dialog != null ) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                EditText password = view.findViewById(R.id.chpw_password);
                EditText confirmpassword = view.findViewById(R.id.chpw_confpassword);
                if (password.getText().equals(confirmpassword.getText())) {
                    assert FirebaseAuth.getInstance().getCurrentUser() != null;
                    FirebaseAuth.getInstance().getCurrentUser().updatePassword( password.getText().toString() )
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    System.out.println("Password updated");
                                } else {
                                    System.out.println("Error password not updated");
                                }
                            });
                    dialog.dismiss();
                } else {
                    System.out.println("Password does not match.");
                }
            });
        }
    }
}