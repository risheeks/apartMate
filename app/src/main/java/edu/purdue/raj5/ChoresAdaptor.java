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

public class ChoresAdaptor extends RecyclerView.Adapter<ChoresAdaptor.ViewHolder>  {
    private Context mContext;
    private ArrayList<String> mChoreNames = new ArrayList<>();
    private ArrayList<String> mChoreAssignee = new ArrayList<>();
    private ArrayList<String> mChoreDescription = new ArrayList<>();
    private ArrayList<String> mChoreDate = new ArrayList<>();
    private ArrayList<String> mChoreTime = new ArrayList<>();


    public ChoresAdaptor(Context mContext, ArrayList<String> mGroupNames) {
        this.mContext = mContext;
        this.mChoreNames = mGroupNames;
    }

    @NonNull
    @Override
    public ChoresAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chores,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChoresAdaptor.ViewHolder viewHolder,final int i) {
        viewHolder.chore.setText(mChoreNames.get(i));
        viewHolder.choreAssignee.setText(mChoreAssignee.get(i));
        viewHolder.choreDescription.setText(mChoreDescription.get(i));
        viewHolder.choreDate.setText(mChoreDate.get(i));
        viewHolder.choreTime.setText(mChoreTime.get(i));

        viewHolder.ll_chores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Remove this item: "+mChoreNames.get(i), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mChoreNames.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView chore;
        TextView choreAssignee;
        TextView choreDescription;
        TextView choreDate;
        TextView choreTime;
        LinearLayout ll_chores;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chore = (TextView) itemView.findViewById(R.id.tv_choreName);
            choreAssignee = (TextView) itemView.findViewById(R.id.tv_choreByWho);
            choreDescription = (TextView) itemView.findViewById(R.id.tv_choreDescription);
            choreDate = (TextView) itemView.findViewById(R.id.tv_choreDate);
            choreTime = (TextView) itemView.findViewById(R.id.tv_choreTime);
            ll_chores = (LinearLayout) itemView.findViewById(R.id.ll_chores);
        }
    }
}
