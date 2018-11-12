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
import android.widget.ToggleButton;

import java.util.ArrayList;

public class IsSmokingOrDrinkingAdapter extends RecyclerView.Adapter<IsSmokingOrDrinkingAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Boolean> mIsSmoking = new ArrayList<>();
    private ArrayList<Boolean> mIsDrinking = new ArrayList<>();

    public IsSmokingOrDrinkingAdapter(Context mContext, ArrayList<String> mNames, ArrayList<Boolean> mIsSmoking, ArrayList<Boolean> mIsDrinking) {
        this.mContext = mContext;
        this.mNames = mNames;
        this.mIsSmoking = mIsSmoking;
        this.mIsDrinking = mIsDrinking;
    }

    @NonNull
    @Override
    public IsSmokingOrDrinkingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_emergency_contact,viewGroup,false);
        IsSmokingOrDrinkingAdapter.ViewHolder holder = new IsSmokingOrDrinkingAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(mNames.get(i));
        viewHolder.isSmoking.setChecked(mIsSmoking.get(i));
        viewHolder.isDrinking.setChecked(mIsDrinking.get(i));
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ToggleButton isSmoking;
        ToggleButton isDrinking;
        //LinearLayout ll_roommateRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_ratingRoommatePersonName);
            isSmoking = (ToggleButton) itemView.findViewById(R.id.tb_isRoommateSmoking);
            isDrinking = (ToggleButton) itemView.findViewById(R.id.tb_isRoommateDrinking);
            //ll_roommateRating = (LinearLayout) itemView.findViewById(R.id.ll_roomateRating);
        }
    }
}