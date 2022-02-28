package com.zeeshanvirani.projectcelia;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class HistoryFragment extends Fragment {

    Context context;
    RecyclerView historyLayout;

    String[] brewHistory_dates, brewHistory_roasttypes, brewHistory_ratings;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        context = getActivity().getApplicationContext();

        historyLayout = (RecyclerView) view.findViewById(R.id.brew_history_list);

        // For testing purposes : START
        brewHistory_dates = new String[]{"Dec 28, 2018", "Dec 29, 2018", "Dec 30, 2018", "Dec 28, 2018", "Dec 29, 2018", "Dec 30, 2018", "Dec 28, 2018", "Dec 29, 2018", "Dec 30, 2018"};
        brewHistory_roasttypes = new String[]{"Light Roast", "Medium Roast", "Dark Roast", "Light Roast", "Medium Roast", "Dark Roast", "Light Roast", "Medium Roast", "Dark Roast"};
        brewHistory_ratings = new String[]{"null", "7/10", "9/10", "6/10", "7/10", "9/10", "6/10", "7/10", "9/10"};
        // END

        historyLayout.setAdapter( new BrewHistoryListAdapter(context, brewHistory_dates, brewHistory_roasttypes, brewHistory_ratings) );
        historyLayout.setLayoutManager( new LinearLayoutManager( context ) );

        return view;
    }

}