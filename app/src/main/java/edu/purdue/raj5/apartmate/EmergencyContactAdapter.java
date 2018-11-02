package edu.purdue.raj5.apartmate;


import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EmergencyContactAdapter extends RecyclerView.Adapter<EmergencyContactAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mEmergencyContacts = new ArrayList<>();
    ArrayList<String> mTempEmergency = new ArrayList<>();
    String groupName;
    String email;
    public EmergencyContactAdapter(Context mContext, ArrayList<String> mGroupNames, ArrayList<String> mEmergencyContacts,ArrayList<String> mTempEmergency, String groupName, String email) {
        this.mContext = mContext;
        this.mNames = mGroupNames;
        this.mEmergencyContacts = mEmergencyContacts;
        this.mTempEmergency = mTempEmergency;
        this.groupName = groupName;
        this.email = email;
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
                        final FirebaseDatabase storage = FirebaseDatabase.getInstance();
                        final DatabaseReference storageRef = storage.getReference("Groups/" + groupName + "/EmergencyContacts");
                        Log.e("GN",groupName);
                        storageRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String message;
                                String remainingEmer = "";
                                if (dataSnapshot.getValue() == null)
                                    message = "";
                                else
                                    message = dataSnapshot.getValue().toString();
                                String emergency[] = message.split(";");


                                for(int j = 0; j < emergency.length;j++) {
                                    String[] emer = emergency[j].split(":");
                                    Log.e("Iny","For");
                                    Log.e("In",String.valueOf(i));
                                    Log.e("In",String.valueOf(j));

                                    if (!emer[1].equals(mTempEmergency.get(i)))
                                    {
                                        remainingEmer += emergency[j]+";";
                                    }
                                }
                                Log.e("Remainig",remainingEmer);
                                storageRef.setValue(remainingEmer);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("The read failed: " + databaseError.getCode());
                            }
                        });


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