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

public class EmergencyContactAdapter extends RecyclerView.Adapter<EmergencyContactAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mEmergencyContacts = new ArrayList<>();


    public EmergencyContactAdapter(Context mContext, ArrayList<String> mGroupNames, ArrayList<String> mEmergencyContacts) {
        this.mContext = mContext;
        this.mNames = mGroupNames;
        this.mEmergencyContacts = mEmergencyContacts;
    }

    @NonNull
    @Override
    public EmergencyContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_emergency_contact,viewGroup,false);
        EmergencyContactAdapter.ViewHolder holder = new EmergencyContactAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(mNames.get(i));
        viewHolder.emergencyContacts.setText(mEmergencyContacts.get(i));
        viewHolder.ll_emergencyContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        Toast.makeText(mContext, "Remove this item: "+mEmergencyContacts.get(i), Toast.LENGTH_SHORT).show();

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
        TextView emergencyContacts;
        LinearLayout ll_emergencyContacts;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_emergencyContactPersonName);
            emergencyContacts = (TextView) itemView.findViewById(R.id.tv_emergencyContact);
            ll_emergencyContacts = (LinearLayout) itemView.findViewById(R.id.ll_emergencyContact);
        }
    }
}