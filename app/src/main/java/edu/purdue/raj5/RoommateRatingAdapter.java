package com.example.dell.apartmate;

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

import java.util.ArrayList;

public class RoommateRatingAdapter extends RecyclerView.Adapter<RoommateRatingAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mRatings = new ArrayList<>();
    private ArrayList<String> mComments = new ArrayList<>();

    public RoommateRatingAdapter(Context mContext, ArrayList<String> mNames, ArrayList<String> mRatings, ArrayList<String> mComments) {
        this.mContext = mContext;
        this.mNames = mNames;
        this.mRatings = mRatings;
        this.mComments = mComments;
    }

    @NonNull
    @Override
    public RoommateRatingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_emergency_contact,viewGroup,false);
        RoommateRatingAdapter.ViewHolder holder = new RoommateRatingAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(mNames.get(i));
        viewHolder.rating.setRating(Float.parseFloat(mRatings.get(i)));
        viewHolder.comment.setText(mComments.get(i));
        viewHolder.ll_roommateRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Update roommate rating",Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View viewDialog = LayoutInflater.from(mContext).inflate(R.layout.add_roommate_rating,null);
                final RatingBar rb_roommateRatingAdd = (RatingBar) viewDialog.findViewById(R.id.rb_roommateRatingAdd);
                final Button bt_roommateRatingUpdate = (Button) viewDialog.findViewById(R.id.bt_roommateRatingAdd);
                builder.setView(viewDialog);
                final AlertDialog dialog = builder.create();

                bt_roommateRatingUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String rating = String.valueOf(rb_roommateRatingAdd.getRating());
                        Toast.makeText(mContext, "The rating of this roommate is "+rating, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

               // dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        RatingBar rating;
        TextView comment;
        LinearLayout ll_roommateRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_ratingRoommatePersonName);
            rating = (RatingBar) itemView.findViewById(R.id.rb_roommateRating);
            comment = (TextView) itemView.findViewById(R.id.tv_ratingRoommateComments);
            ll_roommateRating = (LinearLayout) itemView.findViewById(R.id.ll_roomateRating);
        }
    }
}