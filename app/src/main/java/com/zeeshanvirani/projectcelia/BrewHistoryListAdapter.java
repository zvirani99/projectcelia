package com.zeeshanvirani.projectcelia;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class BrewHistoryListAdapter extends RecyclerView.Adapter<BrewHistoryListAdapter.MyViewHolder> {

    String[] ids, dates, roasts, ratings;
    Context ctx;
    FragmentManager fm;

    public BrewHistoryListAdapter(Context ctx, FragmentManager fragmentManager, String[] ids, String[] dates, String[] roasts, String[] ratings) {
        this.ctx = ctx;
        this.fm = fragmentManager;
        this.ids = ids;
        this.dates = dates;
        this.roasts = roasts;
        this.ratings = ratings;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate( R.layout.brew_history_card, parent, false );
        return new MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.dateText.setText( dates[position] );
        holder.roastText.setText( roasts[position] );

        if ( ratings[position].equals("null") ) {
            holder.ratingText.setVisibility( View.INVISIBLE );
            holder.ratingButton.setVisibility( View.VISIBLE );
            holder.ratingButton.setOnClickListener(view -> rateBrew( ids[position] ));
        } else {
            holder.ratingText.setText(ratings[position]);
        }
    }

    @Override
    public int getItemCount() {
        return dates.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dateText, roastText, ratingText;
        Button ratingButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById( R.id.history_date );
            roastText = itemView.findViewById( R.id.history_roasttype );
            ratingText = itemView.findViewById( R.id.history_rating );
            ratingButton = itemView.findViewById( R.id.history_rating_button );
        }
    }

    public void rateBrew( String id ) {
        // Pop up box to ask for rating
        // Take rating and update database
        // Update current user screen with new rating
        DialogFragment dialog = new RateDialogFragment();

        dialog.show( fm, "rate" );
    }
}


