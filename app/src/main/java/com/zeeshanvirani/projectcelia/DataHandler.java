package com.zeeshanvirani.projectcelia;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

// Handles all modifications of stored local data
public class DataHandler {

    // Update all preferences
    public static void updateSharedPreferences( Context context ) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String name = (String) documentSnapshot.get("firstName") + " " + documentSnapshot.get("lastName");
                boolean notifyBrewingStatus = (boolean) documentSnapshot.get("notifyBrewingStatus");
                boolean notifyMaintenanceReminders = (boolean) documentSnapshot.get("notifyMaintenanceReminders");
                editor.putString("account_name", name);
                editor.putBoolean( "notifications_brewing_status", notifyBrewingStatus);
                editor.putBoolean( "notifications_maintenance_reminders", notifyMaintenanceReminders);
                editor.apply();
            }
        });
        editor.putString("account_email", FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
        editor.apply();
    }

    // Update only specified preference
    // 0 = name
    // 1 = email
    // 2 = brewing status notification
    // 3 = maintenance reminder notification
    // 4 = device information
    public static void updateSharedPreferences( Context context, int type ) {

    }

}
