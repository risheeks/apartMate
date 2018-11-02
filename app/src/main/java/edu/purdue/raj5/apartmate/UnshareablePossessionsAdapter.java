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

import java.util.ArrayList;

public class UnshareablePossessionsAdapter extends RecyclerView.Adapter<UnshareablePossessionsAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mUnshareablePossessions = new ArrayList<>();
    private ArrayList<String> mTempNames = new ArrayList<String>();
    String groupName;
    String email;


    public UnshareablePossessionsAdapter(Context mContext, ArrayList<String> mGroupNames, ArrayList<String> mUnshareablePossessions, ArrayList<String> mTempNames, String groupName, String email) {
        this.mContext = mContext;
        this.mNames = mGroupNames;
        this.mUnshareablePossessions = mUnshareablePossessions;
        this.mTempNames = mTempNames;
        this.groupName = groupName;
        this.email = email;
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
                        final FirebaseDatabase storage = FirebaseDatabase.getInstance();
                        final DatabaseReference storageRef = storage.getReference("Groups/" + groupName + "/UnshareablePossessions");
                        Log.e("GN",groupName);
                        storageRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String message;
                                String remainingPossessions = "";
                                if (dataSnapshot.getValue() == null)
                                    message = "";
                                else
                                    message = dataSnapshot.getValue().toString();
                                String possessions[] = message.split(";");


                                for(int j = 0; j < possessions.length;j++) {
                                    String[] poss = possessions[j].split(":");
                                    Log.e("Iny","For");
                                    Log.e("In",String.valueOf(i));
                                    Log.e("In",String.valueOf(j));

                                    if (!poss[1].equals(mTempNames.get(i)))
                                    {
                                        remainingPossessions += possessions[j]+";";
                                    }
                                }
                                Log.e("Remainig",remainingPossessions);
                                storageRef.setValue(remainingPossessions);
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