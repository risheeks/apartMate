package com.example.dell.apartmate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ChoresAdaptor extends RecyclerView.Adapter<ChoresAdaptor.ViewHolder>  {
    private Context mContext;
    private ArrayList<String> mGroupNames = new ArrayList<>();


    public ChoresAdaptor(Context mContext, ArrayList<String> mGroupNames) {
        this.mContext = mContext;
        this.mGroupNames = mGroupNames;
    }

    @NonNull
    @Override
    public ChoresAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chores,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChoresAdaptor.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mGroupNames.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView Chore;
        TextView ChoreAssignee;
        TextView ChoreDescription;
        TextView ChoreDate;
        TextView ChoreTime;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Chore = (TextView) itemView.findViewById(R.id.tv_choreName);
            ChoreAssignee = (TextView) itemView.findViewById(R.id.tv_choreByWho);
            ChoreDescription = (TextView) itemView.findViewById(R.id.tv_choreDescription);
            ChoreDate = (TextView) itemView.findViewById(R.id.tv_choreDate);
            ChoreTime = (TextView) itemView.findViewById(R.id.tv_choreTime);
        }
    }
}
