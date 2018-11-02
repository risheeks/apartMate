package com.example.dell.apartmate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mInterests = new ArrayList<>();


    public InterestAdapter(Context mContext, ArrayList<String> mGroupNames, ArrayList<String> mInterests) {
        this.mContext = mContext;
        this.mNames = mGroupNames;
        this.mInterests = mInterests;
    }

    @NonNull
    @Override
    public InterestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_interest,viewGroup,false);
        InterestAdapter.ViewHolder holder = new InterestAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(mNames.get(i));
        viewHolder.interests.setText(mInterests.get(i));
        /*viewHolder.ll_chores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Editing is yet to be added", Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView interests;
        //LinearLayout ll_chores;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_interestPersonName);
            interests = (TextView) itemView.findViewById(R.id.tv_interestDescription);
            //ll_chores = (LinearLayout) itemView.findViewById(R.id.ll_groceries);


        }
    }
}