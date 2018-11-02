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

public class GroceryAdaptor extends RecyclerView.Adapter<GroceryAdaptor.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mGroceryNames = new ArrayList<>();
    private ArrayList<String> mNumItems = new ArrayList<>();


    public GroceryAdaptor(Context mContext, ArrayList<String> mGroupNames, ArrayList<String> mNumItems) {
        this.mContext = mContext;
        this.mGroceryNames = mGroupNames;
        this.mNumItems = mNumItems;
    }

    @NonNull
    @Override
    public GroceryAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grocery,viewGroup,false);
        GroceryAdaptor.ViewHolder holder = new GroceryAdaptor.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.groceryName.setText(mGroceryNames.get(i));
        viewHolder.groceryNumItems.setText(mNumItems.get(i));
        viewHolder.ll_chores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        Toast.makeText(mContext, "Remove this item: "+mGroceryNames.get(i), Toast.LENGTH_SHORT).show();

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
        return mGroceryNames.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView groceryName;
        TextView groceryNumItems;
        LinearLayout ll_chores;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            groceryName = (TextView) itemView.findViewById(R.id.tv_groceryName);
            groceryNumItems = (TextView) itemView.findViewById(R.id.tv_groceryNumItems);
            ll_chores = (LinearLayout) itemView.findViewById(R.id.ll_groceries);


        }
    }
}