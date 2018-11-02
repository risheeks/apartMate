package com.example.dell.apartmate;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UnshareablePossessionsAdapter extends RecyclerView.Adapter<UnshareablePossessionsAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mUnshareablePossessions = new ArrayList<>();


    public UnshareablePossessionsAdapter(Context mContext, ArrayList<String> mGroupNames, ArrayList<String> mUnshareablePossessions) {
        this.mContext = mContext;
        this.mNames = mGroupNames;
        this.mUnshareablePossessions = mUnshareablePossessions;
    }

    @NonNull
    @Override
    public UnshareablePossessionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_unshareable_possessions,viewGroup,false);
        UnshareablePossessionsAdapter.ViewHolder holder = new UnshareablePossessionsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(mNames.get(i));
        viewHolder.unshareablePossessions.setText(mUnshareablePossessions.get(i));
        viewHolder.ll_unshareablePossession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        Toast.makeText(mContext, "Remove this item: "+mUnshareablePossessions.get(i), Toast.LENGTH_SHORT).show();

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView unshareablePossessions;
        LinearLayout ll_unshareablePossession;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_unshareablePersonName);
            unshareablePossessions = (TextView) itemView.findViewById(R.id.tv_unshareablePossessions);
            ll_unshareablePossession= (LinearLayout) itemView.findViewById(R.id.ll_unshareablePossession);


        }
    }
}