package edu.purdue.raj5.apartmate;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mMatches = new ArrayList<>();

    public MatchesAdapter(Context mContext, ArrayList<String> mNames) {
        this.mContext = mContext;
        this.mMatches = mNames;
    }

    @NonNull
    @Override
    public MatchesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_matches,viewGroup,false);
        MatchesAdapter.ViewHolder holder = new MatchesAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.match.setText(mMatches.get(i));
    }

    @Override
    public int getItemCount() {
        return mMatches.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView match;
        LinearLayout ll_matches;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            match = (TextView) itemView.findViewById(R.id.tv_matchesName);
            ll_matches = (LinearLayout) itemView.findViewById(R.id.ll_matches);
            String s = preferences.getString("theme", "");
            if (s.equals("dark")) {
                ll_matches.setBackgroundColor(Color.DKGRAY);
                match.setTextColor(Color.WHITE);
            } else {
                ll_matches.setBackgroundColor(Color.WHITE);
                match.setTextColor(Color.BLACK);
            }
        }
    }
}