package com.zeeshanvirani.projectcelia;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

// Handles all modifications of stored local data
public class DataHandler {

    // Update all preferences
    public static void updateSharedPreferences( Context context ) {
        assert FirebaseAuth.getInstance().getCurrentUser() != null;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        DocumentReference docRef;

        docRef = FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        docRef.get().addOnSuccessListener(documentSnapshot -> {
            String name = (String) documentSnapshot.get("name");
            boolean notifyBrewingStatus = (boolean) documentSnapshot.get("notifyBrewingStatus");
            boolean notifyMaintenanceReminders = (boolean) documentSnapshot.get("notifyMaintenanceReminders");
            editor.putString("account_name", name);
            editor.putBoolean( "notifications_brewing_status", notifyBrewingStatus);
            editor.putBoolean( "notifications_maintenance_reminders", notifyMaintenanceReminders);
            editor.apply();
        });
        editor.putString("account_email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        editor.apply();
    }

}
